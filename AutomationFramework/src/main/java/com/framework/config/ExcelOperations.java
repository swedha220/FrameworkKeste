package com.framework.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelOperations {

	static Workbook book;
	static Sheet sheet;

	public static final String filePath = ConstantValues.TESTDATA_PATH;

	public static String getData(String sheetName) {
		String value = null;
		FileInputStream file = null;
		try {
			file = new FileInputStream(filePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			book = WorkbookFactory.create(file);
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		sheet = book.getSheet(sheetName);
		int rowsCount = sheet.getLastRowNum();
		for (int i = 0; i <= rowsCount; i++) {
			Cell cell = sheet.getRow(i).getCell(i);
			value = cell.getStringCellValue();
		}
		return value;
	}

	public static int getRowNubWithTextInColumn(String sheetName, String text, int columnNumber) {
		FileInputStream file = null;
		int rownumber = 0;
		try {
			file = new FileInputStream(filePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			book = WorkbookFactory.create(file);
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		sheet = book.getSheet(sheetName);
		int rowcount = sheet.getLastRowNum();
		try {

			for (rownumber = 1; rownumber < rowcount; rownumber++) {
				if (getCellData(sheetName, rownumber, columnNumber).equalsIgnoreCase(text)) {
					break;
				}
			}

		} catch (Exception e) {

		}
		return rownumber;
	}

	public static String getCellData(String sheetName, int RowNum, int ColNum) throws Exception {
		String dataValue = null;
		FileInputStream file = null;

		try {
			file = new FileInputStream(filePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			book = WorkbookFactory.create(file);
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		sheet = book.getSheet(sheetName);
		Cell cell = sheet.getRow(RowNum).getCell(ColNum);

		dataValue = cell.getStringCellValue();
		return dataValue;
	}
}
