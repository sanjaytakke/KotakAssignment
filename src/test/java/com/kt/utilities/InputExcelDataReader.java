package com.kt.utilities;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kt.base.TestBaseKotak;



public class InputExcelDataReader extends TestBaseKotak  {

	private static final Logger LOGGER = LoggerFactory.getLogger(InputExcelDataReader.class);

	public Object[][] readData(String Scenario, String fileName) {
		XSSFWorkbook wb = null;
		XSSFSheet sh = null;
		Object[][] data = null;
		try {

			FileInputStream ft = new FileInputStream(
					new File(System.getProperty("user.dir") + "/src/test/resources/DataBase/" + fileName + ""));
			wb = new XSSFWorkbook(ft);
			sh = wb.getSheet(Scenario);

			int totalRows = getRowNum(sh);
			data = new String[totalRows][getColNum(sh, 0)];
			LOGGER.info("Total line of Records : " + totalRows);
			DataFormatter df = new DataFormatter();
			for (int num = 1; num <= totalRows; num++) {
				int colNum = getColNum(sh, num);

				Row row = sh.getRow(num);
				LOGGER.info("Row Number : " + num);
				for (int col = 0; col <= colNum - 1 && row != null; col++) {
					String cellvalue = "";
					try {

						//LOGGER.info("Data:"+ df.formatCellValue(row.getCell(col)));
						cellvalue = df.formatCellValue(row.getCell(col));
						cellvalue = cellvalue == null ? "" : cellvalue;

					} catch (NullPointerException e) {
						cellvalue = "";
					}
					data[num - 1][col] = cellvalue;
				}
			}

		} catch (NullPointerException e) {
			LOGGER.info("Exception - Delete blank rows after last line from Excel file : " + e);
		}

		catch (Exception e) {
			LOGGER.info("Exception : " + e);
		}
		return data;
	}

	public int getRowNum(XSSFSheet sh) {
		return sh.getLastRowNum();
	}

	public int getColNum(XSSFSheet sh, int rownum) {
		return sh.getRow(rownum).getLastCellNum();

	}

	public Object[][] readEnquiryMaxData(String Scenario, String fileName) throws Exception {

		int rowNum = 2;
		if ("enquiryMaxCJ2".equalsIgnoreCase(Scenario)) {
			rowNum = 1;
		} else if ("enquiryMaxCJ7".equalsIgnoreCase(Scenario)) {
			rowNum = 2;
		} else {
			rowNum = 3;
		}
		XSSFWorkbook wb = null;
		XSSFSheet sh = null;
		Object[][] data = null;
		try {

			FileInputStream ft = new FileInputStream(
					new File(System.getProperty("user.dir") + "/src/test/resources/" + fileName + ""));
			wb = new XSSFWorkbook(ft);
			sh = wb.getSheet(getPropertyValue("testData.enquiryMax.excelSheetName"));

			// int totalRows = getRowNum(sh);
			data = new String[1][getColNum(sh, 0)];
			// LOGGER.info("Total line of Records : " + totalRows);
			DataFormatter df = new DataFormatter();
			for (int num = rowNum; num <= rowNum; num++) {
				int colNum = getColNum(sh, num);

				Row row = sh.getRow(num);
				LOGGER.info("Row Number : " + num);
				for (int col = 0; col <= colNum - 1 && row != null; col++) {
					String cellvalue = "";
					try {

						LOGGER.info(df.formatCellValue(row.getCell(col)));
						cellvalue = df.formatCellValue(row.getCell(col));
						cellvalue = cellvalue == null ? "" : cellvalue;

					} catch (NullPointerException e) {
						cellvalue = "";
					}
					data[0][col] = cellvalue;
				}
			}

		} catch (NullPointerException e) {
			LOGGER.info("Exception - Delete blank rows after last line from Excel file : " + e);
		}

		catch (Exception e) {
			LOGGER.info("Exception : " + e);
		}
		return data;
	}
}
