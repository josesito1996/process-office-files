package com.example.demo.service;

import com.example.demo.model.jasper.ContratoFakeRegisterRequest;
import com.example.demo.model.request.ContratoEnviarCorreoRequest;

public interface ContratoService {
	public boolean enviarContratoPorCorreo(ContratoEnviarCorreoRequest request);
	
	public byte[] pdfContrato(ContratoFakeRegisterRequest request);
	
	public String pdfContrato1(ContratoFakeRegisterRequest request);
	
}
