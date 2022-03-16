package com.example.demo.service;

import java.util.List;

import com.example.demo.model.jasper.ContratoRequestJasper;

public interface JasperService {

	public byte[] pdfContrato1(List<ContratoRequestJasper> list);
	
}
