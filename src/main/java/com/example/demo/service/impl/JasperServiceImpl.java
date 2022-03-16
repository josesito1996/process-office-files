package com.example.demo.service.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.jasper.ContratoRequestJasper;
import com.example.demo.service.JasperService;

import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
@Slf4j
public class JasperServiceImpl implements JasperService {

	@Override
	public byte[] pdfContrato1(List<ContratoRequestJasper> list) {
		log.info("JasperServiceImpl.pdfContrato1");
		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(list, false);
		try {
			JasperReport compileReport = JasperCompileManager
					.compileReport(new FileInputStream("src/main/resources/Hoja1.jrxml"));
			JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, new HashMap<>(),
					beanCollectionDataSource);
			byte data[] = JasperExportManager.exportReportToPdf(jasperPrint);
			return data;
		} catch (FileNotFoundException | JRException e) {
			log.info("Error : {}", e);
		}
		return null;
	}

}
