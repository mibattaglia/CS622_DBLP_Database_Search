package XMLHandling;

import java.util.ArrayList;
import java.util.List;

public class BruteForceSearch {
    /**
     * This class performs a brute force search on a small portion of the DBLP data file. The dblp file is parsed using {@link Parser} and relevant data
     * (titles and years of each article) is added to an arraylist. The search is done by iterating through the entire arraylist and appending
     * results that match {@link #querystr} to another arraylist. The result is then printed.
     *
     * The results are then printed to an excel file using {@link ExcelDriver}
     *
     * @author Michael Battaglia
     */

    static final int FLAG = 1; //used in ExcelDriver to determine which sheets get written

    static int hitsPerPage = 5000; //enter any number to manipulate hits returned

    //example queries
    //static String querystr = new String("Gradient Descent");
    //static String querystr = new String("Optimization");
    //static String querystr = new String("Genetic Algorithm");
    //static String querystr = new String("Optimizer");
    static String querystr = new String("title");


    public static void main(String[] args) {
        String nodeName = "inproceedings";

        long startTime = System.currentTimeMillis();
        Parser.parser("dblpSmall.xml", nodeName);

        List<String> listClone = new ArrayList<String>();
        for (String string : Parser.myArrayList) {

            //if arraylist.get(i) matches querystr, add it to listClone
            if (string.matches("(?i).*(" + querystr + ").*")) {
                listClone.add(string);
            }
        }
        int i = hitsPerPage;
        int listNum = 1;
        try {
            //print each element of listClone
            while (i > 0) {
                if (i < listClone.size()) {
                    System.out.println(listNum + ". DATA FOUND--- " + listClone.get(i));
                }
                i--;
                listNum++;
            }
        } catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        int intTime = (int) totalTime;
        System.out.println("Time taken: " + intTime + " ms");

        ExcelDriver.excelWriter(hitsPerPage, intTime, querystr, FLAG);
    }
}
