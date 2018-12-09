//package AI.Tests;
//
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.io.FileNotFoundException;
//import java.util.Iterator;
//
//import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//
//public class ExcelWriter {
//
//    private static final String FILE_NAME = "MinimaxTest1.xlsx";
//
//    public static void main(String[] args){//throws IOException{ //veranderen naar method
//        XSSFWorkbook workbook = new XSSFWorkbook();
//        XSSFSheet sheet = workbook.createSheet("Test");
//
//        Object[][] bookData = {
//                {"Head First Java", "Kathy Serria", 79},
//                {"Effective Java", "Joshua Bloch", 36},
//                {"Clean Code", "Robert martin", 42},
//                {"Thinking in Java", "Bruce Eckel", 35},
//        };
//
//        int rowCount = 0;
//
//        for (Object[] aBook : bookData) {
//            Row row = sheet.createRow(++rowCount);
//
//            int columnCount = 0;
//
//            for (Object field : aBook) {
//                Cell cell = row.createCell(++columnCount);
//                if (field instanceof String) {
//                    cell.setCellValue((String) field);
//                } else if (field instanceof Integer) {
//                    cell.setCellValue((Integer) field);
//                }
//            }
//
//        }
//
//        try {
//            FileOutputStream fileOut = new FileOutputStream(FILE_NAME);
//            workbook.write(fileOut);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        //fileOut.close();
//        //workbook.close();
//
//
//    }
//
//}
