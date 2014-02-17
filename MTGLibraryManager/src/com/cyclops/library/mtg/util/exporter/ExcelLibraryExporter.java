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

import com.cyclops.library.mtg.domain.CardBean;
import com.cyclops.library.mtg.domain.LibraryBean;
import com.cyclops.library.mtg.domain.LibraryCardBean;
import com.cyclops.library.mtg.domain.LibrarySetBean;

public class ExcelLibraryExporter {

	public static OutputStream export(LibraryBean libraryBean) throws IOException {
		int currentRowIndex = 0;
		HSSFWorkbook workbook = new HSSFWorkbook();
		
		CreationHelper creationHelper = workbook.getCreationHelper();
		
		CellStyle hlinkStyle = workbook.createCellStyle();
		CellStyle cardNoStyle = workbook.createCellStyle();
		cardNoStyle.setAlignment(CellStyle.ALIGN_RIGHT);
		
	    Font hlinkFont = workbook.createFont();
	    hlinkFont.setUnderline(Font.U_SINGLE);
	    hlinkFont.setColor(IndexedColors.BLUE.getIndex());
	    hlinkStyle.setFont(hlinkFont);
	    
		HSSFSheet sheet = workbook.createSheet(libraryBean.getName());
		
		Row headerRow = sheet.createRow(currentRowIndex++);
		headerRow.createCell(0).setCellValue("Card number");
		headerRow.createCell(1).setCellValue("Card name");
		headerRow.createCell(2).setCellValue("Type");
		headerRow.createCell(3).setCellValue("Mana");
		headerRow.createCell(4).setCellValue("Rarity");
		headerRow.createCell(5).setCellValue("Edition");
		headerRow.createCell(6).setCellValue("Quantity");
		headerRow.createCell(7).setCellValue("Foil Quantity");
		
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
				    link.setAddress(referencedCard.getUrl());
				    cardRow.getCell(1).setHyperlink(link);
				    cardRow.getCell(1).setCellStyle(hlinkStyle);
					
					cardRow.createCell(2).setCellValue(referencedCard.getType());
					cardRow.createCell(3).setCellValue(referencedCard.getMana());
					cardRow.createCell(4).setCellValue(referencedCard.getRarity());
					cardRow.createCell(5).setCellValue(currLibrarySetBean.getReferencedSet().getName());
					cardRow.createCell(6).setCellValue(currLibraryCardBean.getQuantity());
					cardRow.createCell(7).setCellValue(currLibraryCardBean.getFoilQuantity());
				}
			}
		}
		
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
}
