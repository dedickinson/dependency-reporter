package name.dickinson.duncan.cephalopod.indexer

/**
 * Created by s2183294 on 17/12/2015.
 */
class Lister {

    def tmp() {
        final IndexSearcher searcher = centralContext.acquireIndexSearcher();
        try {
            final IndexReader ir = searcher.getIndexReader();
            Bits liveDocs = MultiFields.getLiveDocs(ir);
            for (int i = 0; i < ir.maxDoc(); i++) {
                if (liveDocs == null || liveDocs.get(i)) {
                    final Document doc = ir.document(i);
                    final ArtifactInfo ai = IndexUtils.constructArtifactInfo(doc, centralContext);
                    System.out.println(ai.getGroupId() + ":" + ai.getArtifactId() + ":" + ai.getVersion() + ":" + ai.getClassifier()
                            + " (sha1=" + ai.getSha1() + ")");
                }
            }
        } finally {
            centralContext.releaseIndexSearcher(searcher);
        }
    }
}
