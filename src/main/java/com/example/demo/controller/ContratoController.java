package com.example.demo.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.jasper.ContratoFakeRegisterRequest;
import com.example.demo.model.request.ContratoEnviarCorreoRequest;
import com.example.demo.service.ContratoService;

@RestController
@RequestMapping("/api-contrato")
public class ContratoController {

	@Autowired
	private ContratoService contratoService;

	
	@PostMapping(path = "/mailContract")
	public boolean enviarContratoPorCorreo(@RequestBody @Valid ContratoEnviarCorreoRequest request) {
		return contratoService.enviarContratoPorCorreo(request);
	}
	
	@PostMapping(path = "/viewPdf")
	public ResponseEntity<byte[]> pdfContrato1(@RequestBody @Valid ContratoFakeRegisterRequest request) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=Contrato-Preview.pdf");
		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
				.body(contratoService.pdfContrato(request));
	}
	
	@PostMapping(path = "/viewPdfBase64")
	public ResponseEntity<String> pdfContratoBase64(@RequestBody @Valid ContratoFakeRegisterRequest request) {
		return ResponseEntity.ok()
				.body(contratoService.pdfContrato1(request));
	}

	

}
