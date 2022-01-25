package com.example.demo.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

import com.example.demo.service.ImageService;
import com.spire.doc.Document;
import com.spire.doc.documents.ImageType;
import com.spire.pdf.PdfDocument;
import com.spire.pdf.graphics.PdfImageType;
import com.spire.presentation.Presentation;
import com.spire.xls.Workbook;
import com.spire.xls.Worksheet;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

	@Override
	public File imageWord(File fileWord) {
		log.info("ImageServiceImpl.imageWord");
		try {
			Document documentWord = new Document();
			documentWord.loadFromFile(fileWord.getAbsolutePath());
			BufferedImage image = documentWord.saveToImages(0, ImageType.Bitmap);
			File filePng = new File("word-image.png");
			ImageIO.write(image, "PNG", filePng);
			documentWord.dispose();
			fileWord.delete();
			return filePng;
		} catch (IOException e) {
			log.error("Error {}", e);
			return null;
		}
	}

	@Override
	public File imagePpt(File filePpt) {
		log.info("ImageServiceImpl.imagePpt");
		try {
			Presentation presentation = new Presentation();
			presentation.loadFromFile(filePpt.getAbsolutePath());
			int cantDiapositivas = presentation.getSlides().getCount();
			File filePng = new File("power-point-image.png");
			for (int i = 0; i < cantDiapositivas; i++) {
				if (i == 0) {
					BufferedImage image = presentation.getSlides().get(i).saveAsImage();
					ImageIO.write(image, "PNG", filePng);
				}
			}
			presentation.dispose();
			filePpt.delete();
			return filePng;
		} catch (Exception e) {
			log.error("Error {}", e);
			return null;
		}
	}

	@Override
	public File imageExcel(File fileExcel) {
		log.info("ImageServiceImpl.imageExcel");
		try {
			Workbook workbook = new Workbook();
			workbook.loadFromFile(fileExcel.getAbsolutePath());
			Worksheet sheet = workbook.getWorksheets().get(0);
			BufferedImage bufferedImage = sheet.toImage(sheet.getFirstRow(), sheet.getFirstColumn(), sheet.getLastRow(),
					sheet.getLastColumn());
			File filePng = new File("excel-image.png");
			ImageIO.write(bufferedImage, "PNG", filePng);
			workbook.dispose();
			fileExcel.delete();
			return filePng;
		} catch (IOException e) {
			log.error("Error {}", e);
			return null;
		}
	}

	@Override
	public File imagePdf(File filePdf) {
		log.info("ImageServiceImpl.imagePdf");
		try {
			PdfDocument pdfDocument = new PdfDocument();
			pdfDocument.loadFromFile(filePdf.getAbsolutePath());
			BufferedImage bufferedImage = pdfDocument.saveAsImage(0, PdfImageType.Bitmap);
			File filePng = new File("pdf-image.png");
			ImageIO.write(bufferedImage, "PNG", filePng);
			pdfDocument.dispose();
			filePdf.delete();
			return filePng;
		} catch (IOException e) {
			log.error("Error {}", e);
			return null;
		}
	}

}
