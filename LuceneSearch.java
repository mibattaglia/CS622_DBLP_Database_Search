package XMLHandling;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.ByteBuffersDirectory;
import org.apache.lucene.store.Directory;

import java.io.IOException;
import java.util.ArrayList;

public class LuceneSearch {
    /**
     * This class utilizes a lucene index to search contents from a small portion of the dblp file. The dblp file is parsed using {@link Parser} and relevant data
     * (titles and years of each article) is added to an arraylist which is then passed into {@link #luceneIndexer()} for searching. You can manipulate which
     * keyword is searched using {@link #querystr} and the number of results displayed with {@link #hitsPerPage}
     *
     * The results are then printed to an excel file using {@link ExcelDriver}
     *
     * @author Michael Battaglia
     */

    static final int FLAG = 0; //used in ExcelDriver to determine which sheets get written

    static int hitsPerPage = 5000; //enter any number to manipulate hits returned
    static int intTime;

    //example queries
    //static String querystr = new String("Gradient Descent");
    //static String querystr = new String("Optimization");
    //static String querystr = new String("Genetic Algorithm");
    //static String querystr = new String("Optimizer");
    static String querystr = new String("title");


    public static void main(String[] args){
        //execute search based on querystr and hitsPerPage
        luceneIndexer();

        //write results to the excel file
        ExcelDriver.excelWriter(hitsPerPage, intTime, querystr, FLAG);
    }

    public static void luceneIndexer(){
        try {
            Parser.parser("dblpSmall.xml", "inproceedings"); //creates the arraylist from the xml file
            StandardAnalyzer analyzer = new StandardAnalyzer();

            Directory index = new ByteBuffersDirectory(); //index creation

            IndexWriterConfig config = new IndexWriterConfig(analyzer);

            IndexWriter w = new IndexWriter(index, config);

            //add each element of arraylist to Lucene Index
            for (String temp : Parser.myArrayList) {
                addDoc(w, temp);
            }
            w.close();

            // the QueryParer arg specifies the default field to use
            // when no field is explicitly specified in the query.
            Query q = new QueryParser("data", analyzer).parse(querystr);

            // 3. search
            IndexReader reader = DirectoryReader.open(index);
            IndexSearcher searcher = new IndexSearcher(reader);
            TopDocs docs = searcher.search(q, hitsPerPage);
            ScoreDoc[] hits = docs.scoreDocs;

            // 4. display results
            System.out.println("Found " + hits.length + " hits.");
            long startTime = System.currentTimeMillis();
            for(int i=0; i<hits.length; ++i) {
                int docId = hits[i].doc;
                org.apache.lucene.document.Document d = searcher.doc(docId);
                System.out.println((i + 1) + ". DATA FOUND--- " + d.get("data"));
            }
            reader.close(); //reader closed when document no longer needs to be accessed
            long endTime = System.currentTimeMillis();
            long totalTime = endTime - startTime;
            intTime = (int) totalTime;
            System.out.println("Time taken: " + intTime + " ms");
        } catch(IOException | ParseException ex){
            ex.printStackTrace();
        }
    }

    private static void addDoc(IndexWriter w, String nodeName) throws IOException {
        org.apache.lucene.document.Document doc = new org.apache.lucene.document.Document();
        doc.add(new TextField("data", nodeName, Field.Store.YES));
        w.addDocument(doc);
    }
}
