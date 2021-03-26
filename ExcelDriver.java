package XMLHandling;

import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelDriver {

    /**
     *This is a driver class that writes results from {@link LuceneSearch} and {@link BruteForceSearch} to an excel file.
     *
     * @author Michael Battaglia
     */

    public static void excelWriter(int hitsPerPage, int intTime, String querystr, int FLAG) {
        try {
            String excelFilePath = "SearchMetrics.xlsx";

            //create file input stream and initialize workbook
            FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(ExcelDriver.sheetNum(querystr, FLAG));
            Object[][] search = {{hitsPerPage, intTime}}; //store results (hitsPerPage and intTime)

            int rowCount = sheet.getLastRowNum() + 1; //write in next open cell

            //iterate through search and add values to cells
            for (Object[] searchHits : search) {
                Row row = sheet.createRow(rowCount++);
                int columnCount = 0;
                Cell cell = row.createCell(columnCount);
                cell.setCellValue(rowCount);

                for (Object time : searchHits) {
                    cell = row.createCell(columnCount++);
                    if (time instanceof String) {
                        cell.setCellValue((String) time);
                    } else if (time instanceof Integer) {
                        cell.setCellValue((Integer) time);
                    }
                }
            }
            inputStream.close();

            FileOutputStream outputStream = new FileOutputStream("SearchMetrics.xlsx");
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static int sheetNum(String query, int flag) {
        //logic to determine which sheet gets written
        int sheet = 0;
        if (flag == 1) { //results for BruteForceSearch go in these sheets
            if (query.equalsIgnoreCase("Gradient Descent")) {
                sheet = 1;
            } else if (query.equalsIgnoreCase("Optimization")) {
                sheet = 3;
            } else if (query.equalsIgnoreCase("Genetic Algorithm")) {
                sheet = 5;
            } else if (query.equalsIgnoreCase("Optimizer")) {
                sheet = 7;
            } else {
                sheet = 9;
            }
        } else if (flag == 0){ //results for LuceneSearch go in these sheets
            if (query.equalsIgnoreCase("Gradient Descent")) {
                sheet = 2;
            } else if (query.equalsIgnoreCase("Optimization")) {
                sheet = 4;
            } else if (query.equalsIgnoreCase("Genetic Algorithm")) {
                sheet = 6;
            } else if (query.equalsIgnoreCase("Optimizer")) {
                sheet = 8;
            } else {
                sheet = 10;
            }
        }
        return sheet;
    }
}