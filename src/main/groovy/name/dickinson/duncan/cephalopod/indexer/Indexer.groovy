package name.dickinson.duncan.cephalopod.indexer

import org.apache.maven.index.context.IndexCreator
import org.apache.maven.index.context.IndexingContext
import org.apache.maven.index.updater.*
import org.apache.maven.wagon.Wagon
import org.apache.maven.wagon.events.TransferEvent
import org.apache.maven.wagon.events.TransferListener
import org.apache.maven.wagon.observers.AbstractTransferListener
import org.codehaus.plexus.*
import org.codehaus.plexus.component.repository.exception.ComponentLookupException
import org.eclipse.aether.version.InvalidVersionSpecificationException

class Indexer {
    private final PlexusContainer plexusContainer

    private final Indexer indexer

    private final IndexUpdater indexUpdater

    private final Wagon httpWagon

    private IndexingContext centralContext

    Indexer()
            throws PlexusContainerException, ComponentLookupException {
        final DefaultContainerConfiguration config = new DefaultContainerConfiguration();
        config.setClassPathScanning(PlexusConstants.SCANNING_INDEX);
        this.plexusContainer = new DefaultPlexusContainer(config);

        indexer = plexusContainer.lookup(Indexer.class);
        indexUpdater = plexusContainer.lookup(IndexUpdater.class);
        httpWagon = plexusContainer.lookup(Wagon.class, "http");

    }

    public void perform()
            throws IOException, ComponentLookupException, InvalidVersionSpecificationException {
        // Files where local cache is (if any) and Lucene Index should be located
        File centralLocalCache = new File("target/central-cache");
        File centralIndexDir = new File("target/central-index");

        // Creators we want to use (search for fields it defines)
        List<IndexCreator> indexers = new ArrayList<IndexCreator>();
        indexers.add(plexusContainer.lookup(IndexCreator.class, "min"));
        indexers.add(plexusContainer.lookup(IndexCreator.class, "jarContent"));
        indexers.add(plexusContainer.lookup(IndexCreator.class, "maven-plugin"));

        // Create context for central repository index
        centralContext =
                indexer.createIndexingContext("central-context", "central", centralLocalCache, centralIndexDir,
                        "http://repo1.maven.org/maven2", null, true, true, indexers);

        // Update the index (incremental update will happen if this is not 1st run and files are not deleted)
        // This whole block below should not be executed on every app start, but rather controlled by some configuration
        // since this block will always emit at least one HTTP GET. Central indexes are updated once a week, but
        // other index sources might have different index publishing frequency.
        // Preferred frequency is once a week.

        System.out.println("Updating Index...");
        System.out.println("This might take a while on first run, so please be patient!");
        // Create ResourceFetcher implementation to be used with IndexUpdateRequest
        // Here, we use Wagon based one as shorthand, but all we need is a ResourceFetcher implementation
        TransferListener listener = new AbstractTransferListener() {
            public void transferStarted(TransferEvent transferEvent) {
                System.out.print("  Downloading " + transferEvent.getResource().getName());
            }

            public void transferProgress(TransferEvent transferEvent, byte[] buffer, int length) {
            }

            public void transferCompleted(TransferEvent transferEvent) {
                System.out.println(" - Done");
            }
        };
        ResourceFetcher resourceFetcher = new WagonHelper.WagonFetcher(httpWagon, listener, null, null);

        Date centralContextCurrentTimestamp = centralContext.getTimestamp();
        IndexUpdateRequest updateRequest = new IndexUpdateRequest(centralContext, resourceFetcher);
        IndexUpdateResult updateResult = indexUpdater.fetchAndUpdateIndex(updateRequest);
        if (updateResult.isFullUpdate()) {
            System.out.println("Full update happened!");
        } else if (updateResult.getTimestamp().equals(centralContextCurrentTimestamp)) {
            System.out.println("No update needed, index is up to date!");
        } else {
            System.out.println("Incremental update happened, change covered " + centralContextCurrentTimestamp
                    + " - " + updateResult.getTimestamp() + " period.");
        }

        System.out.println();


        System.out.println();
        System.out.println("Using index");
        System.out.println("===========");
        System.out.println();


    }
