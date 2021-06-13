package com.edu.hutech.utils.excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelImporter<T>{

    /**
     * {@link java.lang.String} data type name.
     */
    private static final String DATATYPE_STRING = "java.lang.String";
    /**
     * {@link java.util.Date} data type name.
     */
    private static final String DATATYPE_DATE = "java.util.Date";
    /**
     * {@link java.lang.Integer} data type name.
     */
    private static final String DATATYPE_INTEGER = "java.lang.Integer";
    /**
     * {@link java.lang.Long} data type name.
     */
    private static final String DATATYPE_LONG = "java.lang.Long";
    /**
     * {@link java.lang.Float} data type name.
     */
    private static final String DATATYPE_FLOAT = "java.lang.Float";
    /**
     * {@link java.lang.Double} data type name.
     */
    private static final String DATATYPE_DOUBLE = "java.lang.Double";

    private Class<T> type;

    public ExcelImporter(Class<T> type) {
        this.type = type;
    }

//    T createContents(Class<T> clazz) {
//        try {
//            return clazz.getDeclaredConstructor().newInstance();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
//    }

//    public List parseExcelFile(InputStream is) {
//        try {
//            Workbook workbook = new XSSFWorkbook(is);
//            Sheet sheet = workbook.getSheet("sheet");
//            Iterator rows = sheet.iterator();
//            List<T> list = new ArrayList();
//            int rowNumber = 0;
//            while (rows.hasNext()) {
//                Row currentRow = (Row) rows.next();
//
//                // skip header
//                if (rowNumber == 0) {
//                    rowNumber++;
//                    continue;
//                }
//                Iterator cellsInRow = currentRow.iterator();
//
//                T t = createContents(type);
//
//                int cellIndex = 0;
//                while (cellsInRow.hasNext()) {
//                    Cell currentCell = (Cell) cellsInRow.next();
//                    if (type.getDeclaredFields()[cellIndex].getType().getName().equals(DATATYPE_INTEGER)) {
//
//                    }
//
//                    if (cellIndex == 0) { // ID
//                        cust.setId((long) currentCell.getNumericCellValue());
//                    } else if (cellIndex == 1) { // Name
//                        cust.setName(currentCell.getStringCellValue());
//                    } else if (cellIndex == 2) { // Address
//                        cust.setAddress(currentCell.getStringCellValue());
//                    } else if (cellIndex == 3) { // Age
//                        cust.setAge((int) currentCell.getNumericCellValue());
//                    }
//
//                    cellIndex++;
//                }
//                list.add(t);
//
//
//            }
//            // Close WorkBook
//            workbook.close();
//            return list;
//        } catch (IOException | IllegalAccessException e) {
//            throw new RuntimeException("FAIL! -> message = " + e.getMessage());
//        }
//    }
}
