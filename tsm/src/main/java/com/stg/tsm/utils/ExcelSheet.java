package com.stg.tsm.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import com.stg.tsm.exception.TsmException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.stg.tsm.dto.AllTaskDTO;

public class ExcelSheet {

    public static final String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static final String[] HEADERs = { "userstoryName", "date", "taskName", "efforts" };
    static final String SHEET = "TimeSheet";

    public static ByteArrayInputStream tutorialsToExcel(List<AllTaskDTO> tasks) {

      try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
        Sheet sheet = workbook.createSheet(SHEET);

        // Header
        Row headerRow = sheet.createRow(0);

        for (int col = 0; col < HEADERs.length; col++) {
          Cell cell = headerRow.createCell(col);
          cell.setCellValue(HEADERs[col]);
        }

        int rowIdx = 1;
        for (AllTaskDTO task : tasks) {
          Row row = sheet.createRow(rowIdx++);

          row.createCell(0).setCellValue(task.getUserstoryName());
          row.createCell(1).setCellValue(task.getDate().toString());
          row.createCell(2).setCellValue(task.getTaskName());
          row.createCell(3).setCellValue("a");
        }

        workbook.write(out);
        return new ByteArrayInputStream(out.toByteArray());
      } catch (IOException e) {
        throw new TsmException("fail to import data to Excel file: " + e.getMessage());
      }
    }
}
