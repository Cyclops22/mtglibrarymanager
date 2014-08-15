package com.cyclops.library.mtg.util.exporter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;

import com.cyclops.library.mtg.domain.CardBean;
import com.cyclops.library.mtg.domain.LibraryBean;
import com.cyclops.library.mtg.domain.LibraryCardBean;
import com.cyclops.library.mtg.domain.LibrarySetBean;

public class ExcelLibraryExporter {

	public static OutputStream export(LibraryBean libraryBean) throws IOException {
		int currentRowIndex = 0;
		HSSFWorkbook workbook = new HSSFWorkbook();
		
		CreationHelper creationHelper = workbook.getCreationHelper();
		
		CellStyle hlinkStyle = createHyperlinkStyle(workbook);
		CellStyle cardNoStyle = createCardNoStyle(workbook);
		
		HSSFSheet sheet = workbook.createSheet(libraryBean.getName());
		
		currentRowIndex = setupHeader(workbook, sheet);
		
		for (LibrarySetBean currLibrarySetBean : libraryBean.getSets()) {
			for (LibraryCardBean currLibraryCardBean : currLibrarySetBean.getCards()) {
				if (currLibraryCardBean.getQuantity() > 0 || currLibraryCardBean.getFoilQuantity() > 0) {
					CardBean referencedCard = currLibraryCardBean.getReferencedCard();
					
					Row cardRow = sheet.createRow(currentRowIndex++);
					
					if (NumberUtils.isNumber(referencedCard.getNumber())) {
						cardRow.createCell(0).setCellValue(Integer.parseInt(referencedCard.getNumber()));
						
					} else {
						cardRow.createCell(0).setCellValue(referencedCard.getNumber());
						
					}
					cardRow.getCell(0).setCellStyle(cardNoStyle);
					
					cardRow.createCell(1).setCellValue(referencedCard.getName());
					
					Hyperlink link = creationHelper.createHyperlink(Hyperlink.LINK_URL);
//				    link.setAddress(referencedCard.getUrl());
//				    cardRow.getCell(1).setHyperlink(link);
//				    cardRow.getCell(1).setCellStyle(hlinkStyle);
//					
//					cardRow.createCell(2).setCellValue(referencedCard.getType());
//					cardRow.createCell(3).setCellValue(referencedCard.getMana());
//					cardRow.createCell(4).setCellValue(referencedCard.getRarity());
//					cardRow.createCell(5).setCellValue(currLibrarySetBean.getReferencedSet().getName());
//					cardRow.createCell(6).setCellValue(currLibraryCardBean.getQuantity());
					
					if (currLibraryCardBean.getFoilQuantity() > 0) {
						cardRow.createCell(7).setCellValue(currLibraryCardBean.getFoilQuantity());
					}
				}
			}
		}
		
		sheet.autoSizeColumn(0);
		sheet.autoSizeColumn(1);
		sheet.autoSizeColumn(2);
		sheet.autoSizeColumn(3);
		sheet.autoSizeColumn(4);
		sheet.autoSizeColumn(5);
		sheet.autoSizeColumn(6);
		sheet.autoSizeColumn(7);
		
		OutputStream baos = null;
		try {
			baos = new ByteArrayOutputStream();
			workbook.write(baos);
			
		} finally {
			if (baos != null) {
				baos.close();
			}
		}
		
		return baos;
	}
	
	private static int setupHeader(HSSFWorkbook workbook, HSSFSheet sheet) {
		int currentRowIndex = 0;
		
		Font headerFont = workbook.createFont();
	    headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
	    
	    CellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setAlignment(CellStyle.ALIGN_CENTER);
		headerStyle.setVerticalAlignment(CellStyle.ALIGN_CENTER);
		headerStyle.setFont(headerFont);
		
		Row headerRow = sheet.createRow(currentRowIndex++);
		headerRow.createCell(0).setCellValue("Card number");
		headerRow.getCell(0).setCellStyle(headerStyle);
		
		headerRow.createCell(1).setCellValue("Card name");
		headerRow.getCell(1).setCellStyle(headerStyle);
		
		headerRow.createCell(2).setCellValue("Type");
		headerRow.getCell(2).setCellStyle(headerStyle);
		
		headerRow.createCell(3).setCellValue("Mana");
		headerRow.getCell(3).setCellStyle(headerStyle);
		
		headerRow.createCell(4).setCellValue("Rarity");
		headerRow.getCell(4).setCellStyle(headerStyle);
		
		headerRow.createCell(5).setCellValue("Edition");
		headerRow.getCell(5).setCellStyle(headerStyle);
		
		headerRow.createCell(6).setCellValue("Quantities");
		headerRow.getCell(6).setCellStyle(headerStyle);
		
		Row subHeaderRow = sheet.createRow(currentRowIndex++);
		subHeaderRow.createCell(0).setCellStyle(headerStyle);
		subHeaderRow.createCell(1).setCellStyle(headerStyle);
		subHeaderRow.createCell(2).setCellStyle(headerStyle);
		subHeaderRow.createCell(3).setCellStyle(headerStyle);
		subHeaderRow.createCell(4).setCellStyle(headerStyle);
		subHeaderRow.createCell(5).setCellStyle(headerStyle);
		
		subHeaderRow.createCell(6).setCellValue("Normal");
		subHeaderRow.getCell(6).setCellStyle(headerStyle);
		
		subHeaderRow.createCell(7).setCellValue("Foil");
		subHeaderRow.getCell(7).setCellStyle(headerStyle);
		
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 0));
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 1, 1));
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 2, 2));
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 3, 3));
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 4, 4));
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 5, 5));
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 6, 7));
		
		return currentRowIndex;
	}
	
	private static CellStyle createHyperlinkStyle(HSSFWorkbook workbook) {
		Font hlinkFont = workbook.createFont();
	    hlinkFont.setUnderline(Font.U_SINGLE);
	    hlinkFont.setColor(IndexedColors.BLUE.getIndex());
	    
		CellStyle hlinkStyle = workbook.createCellStyle();
		hlinkStyle.setFont(hlinkFont);
		
		return hlinkStyle;
	}
	
	private static CellStyle createCardNoStyle(HSSFWorkbook workbook) {
		CellStyle cardNoStyle = workbook.createCellStyle();
		cardNoStyle.setAlignment(CellStyle.ALIGN_RIGHT);
		
		return cardNoStyle;
	}
}
