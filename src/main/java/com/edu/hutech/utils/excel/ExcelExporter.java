package com.edu.hutech.utils.excel;

import com.edu.hutech.entities.Course;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;


public class ExcelExporter<T> {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<T> list;
    private Class<T> type;


    public ExcelExporter(List<T> list, Class<T> type) {
        this.list = list;
        this.type = type;
        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine() {
        sheet = workbook.createSheet("current-sheet");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        int t = type.getDeclaredFields().length;

        for (int i = 0; i < type.getDeclaredFields().length; i++) {

            createCell(row, i, type.getDeclaredFields()[i].getName(), style);
        }

    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Float) {
            cell.setCellValue((Float) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (T t : list) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            for (int i = 0; i < t.getClass().getDeclaredFields().length; i++) {
                try {
                    createCell(row, columnCount++, t.getClass().getDeclaredFields()[i].get(t), style);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

        }
    }


    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.flush();
        outputStream.close();

    }

}
