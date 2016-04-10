package name.dickinson.duncan.cephalopod.indexer

/**
 * Created by s2183294 on 17/12/2015.
 */
class Searcher {
    /*
    // ====
        // Case:
        // Search for all GAVs with known G and A and having version greater than V

        final GenericVersionScheme versionScheme = new GenericVersionScheme();
        final String versionString = "1.5.0";
        final Version version = versionScheme.parseVersion(versionString);

        // construct the query for known GA
        final Query groupIdQ =
                indexer.constructQuery(MAVEN.GROUP_ID, new SourcedSearchExpression("org.sonatype.nexus"));
        final Query artifactIdQ =
                indexer.constructQuery(MAVEN.ARTIFACT_ID, new SourcedSearchExpression("nexus-api"));
        final BooleanQuery query = new BooleanQuery();
        query.add(groupIdQ, Occur.MUST);
        query.add(artifactIdQ, Occur.MUST);

        // we want "jar" artifacts only
        query.add(indexer.constructQuery(MAVEN.PACKAGING, new SourcedSearchExpression("jar")), Occur.MUST);
        // we want main artifacts only (no classifier)
        // Note: this below is unfinished API, needs fixing
        query.add(indexer.constructQuery(MAVEN.CLASSIFIER, new SourcedSearchExpression(Field.NOT_PRESENT)),
                Occur.MUST_NOT);

        // construct the filter to express "V greater than"
        final ArtifactInfoFilter versionFilter = new ArtifactInfoFilter() {
            public boolean accepts(final IndexingContext ctx, final ArtifactInfo ai) {
                try {
                    final Version aiV = versionScheme.parseVersion(ai.getVersion());
                    // Use ">=" if you are INCLUSIVE
                    return aiV.compareTo(version) > 0;
                } catch (InvalidVersionSpecificationException e) {
                    // do something here? be safe and include?
                    return true;
                }
            }
        };

        System.out.println("Searching for all GAVs with G=org.sonatype.nexus and nexus-api and having V greater than 1.5.0");
        final IteratorSearchRequest request =
                new IteratorSearchRequest(query, Collections.singletonList(centralContext), versionFilter);
        final IteratorSearchResponse response = indexer.searchIterator(request);
        for (ArtifactInfo ai : response) {
            System.out.println(ai.toString());
        }

        // Case:
        // Use index
        // Searching for some artifact
        Query gidQ = indexer.constructQuery(MAVEN.GROUP_ID, new SourcedSearchExpression("org.apache.maven.indexer"));
        Query aidQ = indexer.constructQuery(MAVEN.ARTIFACT_ID, new SourcedSearchExpression("indexer-artifact"));

        BooleanQuery bq = new BooleanQuery();
        bq.add(gidQ, Occur.MUST);
        bq.add(aidQ, Occur.MUST);

        searchAndDump(indexer, "all artifacts under GA org.apache.maven.indexer:indexer-artifact", bq);

        // Searching for some main artifact
        bq = new BooleanQuery();
        bq.add(gidQ, Occur.MUST);
        bq.add(aidQ, Occur.MUST);
        // bq.add( nexusIndexer.constructQuery( MAVEN.CLASSIFIER, new SourcedSearchExpression( "*" ) ), Occur.MUST_NOT
        // );

        searchAndDump(indexer, "main artifacts under GA org.apache.maven.indexer:indexer-artifact", bq);

        // doing sha1 search
        searchAndDump(indexer, "SHA1 7ab67e6b20e5332a7fb4fdf2f019aec4275846c2", indexer.constructQuery(MAVEN.SHA1,
                new SourcedSearchExpression("7ab67e6b20e5332a7fb4fdf2f019aec4275846c2")));

        searchAndDump(indexer, "SHA1 7ab67e6b20 (partial hash)",
                indexer.constructQuery(MAVEN.SHA1, new UserInputSearchExpression("7ab67e6b20")));

        // doing classname search (incomplete classname)
        searchAndDump(indexer, "classname DefaultNexusIndexer (note: Central does not publish classes in the index)",
                indexer.constructQuery(MAVEN.CLASSNAMES, new UserInputSearchExpression("DefaultNexusIndexer")));

        // doing search for all "canonical" maven plugins latest versions
        bq = new BooleanQuery();
        bq.add(indexer.constructQuery(MAVEN.PACKAGING, new SourcedSearchExpression("maven-plugin")), Occur.MUST);
        bq.add(indexer.constructQuery(MAVEN.GROUP_ID, new SourcedSearchExpression("org.apache.maven.plugins")),
                Occur.MUST);
        searchGroupedAndDump(indexer, "all \"canonical\" maven plugins", bq, new GAGrouping());

        // doing search for all archetypes latest versions
        searchGroupedAndDump(indexer, "all maven archetypes (latest versions)",
                indexer.constructQuery(MAVEN.PACKAGING, new SourcedSearchExpression("maven-archetype")), new GAGrouping());

        // close cleanly
        indexer.closeIndexingContext(centralContext, false);
    }

    public void searchAndDump(Indexer nexusIndexer, String descr, Query q)
            throws IOException {
        System.out.println("Searching for " + descr);

        FlatSearchResponse response = nexusIndexer.searchFlat(new FlatSearchRequest(q, centralContext));

        for (ArtifactInfo ai : response.getResults()) {
            System.out.println(ai.toString());
        }

        System.out.println("------");
        System.out.println("Total: " + response.getTotalHitsCount());
        System.out.println();
    }

    public void searchGroupedAndDump(Indexer nexusIndexer, String descr, Query q, Grouping g)
            throws IOException {
        System.out.println("Searching for " + descr);

        GroupedSearchResponse response = nexusIndexer.searchGrouped(new GroupedSearchRequest(q, g, centralContext));

        for (Map.Entry<String, ArtifactInfoGroup> entry : response.getResults().entrySet()) {
            ArtifactInfo ai = entry.getValue().getArtifactInfos().iterator().next();
            System.out.println("* Entry " + ai);
            System.out.println("  Latest version:  " + ai.getVersion());
            System.out.println(StringUtils.isBlank(ai.getDescription()) ? "No description in plugin's POM."
                    : StringUtils.abbreviate(ai.getDescription(), 60));
            System.out.println();
        }

        System.out.println("------");
        System.out.println("Total record hits: " + response.getTotalHitsCount());
        System.out.println();
    }
     */
}
