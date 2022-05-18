package com.example.demo.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.example.demo.model.jasper.ContratoRequestJasper;
import com.example.demo.service.JasperService;

import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;

@Service
@Slf4j
public class JasperServiceImpl implements JasperService {

	@Override
	public byte[] pdfContrato1(List<ContratoRequestJasper> list) {
		log.info("JasperServiceImpl.pdfContrato1");
		JRBeanCollectionDataSource beanCollectionDataSource1 = new JRBeanCollectionDataSource(list, false);
		JRBeanCollectionDataSource beanCollectionDataSource2 = new JRBeanCollectionDataSource(list, false);
		JRBeanCollectionDataSource beanCollectionDataSource3 = new JRBeanCollectionDataSource(list, false);
		JRBeanCollectionDataSource beanCollectionDataSource4 = new JRBeanCollectionDataSource(list, false);
		JRBeanCollectionDataSource beanCollectionDataSource5 = new JRBeanCollectionDataSource(list, false);
		JRBeanCollectionDataSource beanCollectionDataSource6 = new JRBeanCollectionDataSource(list, false);
		try {

			List<JasperPrint> jasperPrintList = new ArrayList<>();
			JasperReport compileReport1 = JasperCompileManager
					.compileReport(new ClassPathResource("Hoja1.jrxml").getInputStream());

			JasperPrint jasperPrint1 = JasperFillManager.fillReport(compileReport1, new HashMap<>(),
					beanCollectionDataSource1);

			jasperPrintList.add(jasperPrint1);

			JasperReport compileReport2 = JasperCompileManager
					.compileReport(new ClassPathResource("Hoja2.jrxml").getInputStream());

			JasperPrint jasperPrint2 = JasperFillManager.fillReport(compileReport2, new HashMap<>(),
					beanCollectionDataSource2);
			jasperPrintList.add(jasperPrint2);
			
			
			JasperReport compileReport3 = JasperCompileManager
					.compileReport(new ClassPathResource("Hoja3.jrxml").getInputStream());

			JasperPrint jasperPrint3 = JasperFillManager.fillReport(compileReport3, new HashMap<>(),
					beanCollectionDataSource3);
			jasperPrintList.add(jasperPrint3);
			
			JasperReport compileReport4 = JasperCompileManager
					.compileReport(new ClassPathResource("Hoja4.jrxml").getInputStream());

			JasperPrint jasperPrint4 = JasperFillManager.fillReport(compileReport4, new HashMap<>(),
					beanCollectionDataSource4);
			jasperPrintList.add(jasperPrint4);
			
			JasperReport compileReport5 = JasperCompileManager
					.compileReport(new ClassPathResource("Hoja5.jrxml").getInputStream());

			JasperPrint jasperPrint5 = JasperFillManager.fillReport(compileReport5, new HashMap<>(),
					beanCollectionDataSource5);
			jasperPrintList.add(jasperPrint5);
			
			JasperReport compileReport6 = JasperCompileManager
					.compileReport(new ClassPathResource("Hoja6.jrxml").getInputStream());

			JasperPrint jasperPrint6 = JasperFillManager.fillReport(compileReport6, new HashMap<>(),
					beanCollectionDataSource6);
			jasperPrintList.add(jasperPrint6);
			

			// byte data[] = JasperExportManager.exportReportToPdf(jasperPrint);
			return generateReport(jasperPrintList);
		} catch (IOException | JRException e) {
			log.info("Error : {}", e);
		}
		return null;
	}

	private byte[] generateReport(List<JasperPrint> jasperPrintList) throws JRException {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		JRPdfExporter exporter = new JRPdfExporter();

		exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrintList));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));
		SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
		configuration.setCreatingBatchModeBookmarks(true);
		exporter.setConfiguration(configuration);
		exporter.exportReport();
		return baos.toByteArray();
	}

}
