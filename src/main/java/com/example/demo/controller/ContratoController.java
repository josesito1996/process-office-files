package com.example.demo.controller;

import java.util.Arrays;
import java.util.Base64;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.jasper.ClausulaRequest;
import com.example.demo.model.jasper.ContratoDatosPrincipalesRequest;
import com.example.demo.model.jasper.ContratoFakeRegisterRequest;
import com.example.demo.model.jasper.ContratoRequestJasper;
import com.example.demo.model.jasper.DatosEmpleadorRequest;
import com.example.demo.model.jasper.DatosTrabajadorRequest;
import com.example.demo.service.JasperService;

@RestController
@RequestMapping("/api-contrato")
public class ContratoController {

	@Autowired
	private JasperService jasperService;

	@PostMapping(path = "/viewPdf")
	public ResponseEntity<byte[]> pdfContrato1(@RequestBody @Valid ContratoFakeRegisterRequest request) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=Contrato-Preview.pdf");
		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
				.body(jasperService.pdfContrato1(Arrays.asList(transform(request))));
	};
	
	@PostMapping(path = "/viewPdfBase64")
	public ResponseEntity<String> pdfContratoBase64(@RequestBody @Valid ContratoFakeRegisterRequest request) {
		HttpHeaders headers = new HttpHeaders();
		
		String encoded = Base64.getEncoder().encodeToString(jasperService.pdfContrato1(Arrays.asList(transform(request))));
		return ResponseEntity.ok()
				.headers(headers).contentType(MediaType.APPLICATION_JSON)
				.body("data:application/pdf;base64,".concat(encoded));
	};

	private ContratoRequestJasper transform(ContratoFakeRegisterRequest request) {
		ContratoDatosPrincipalesRequest datosPrincipales = request.getDatosPrincipales();
		ClausulaRequest clausula = request.getClausulas();
		DatosEmpleadorRequest datosEmpleador = datosPrincipales.getDatosEmpleador();
		DatosTrabajadorRequest datoTrabajador = datosPrincipales.getDatosTrabajador();
		return ContratoRequestJasper.builder()
				.idTemplate(request.getIdTemplate())
				.razonSocial(datosEmpleador.getRazonSocial())
				.nroPartida(datosEmpleador.getNroPartida())
				.oficinaRegistral(datosEmpleador.getOficinaRegistral())
				.ruc(datosEmpleador.getRuc())
				.domicilioEmpleador(datosEmpleador.getDomicilioEmpleador())
				.departamentoEmpleador(datosEmpleador.getDepartamentoEmpleador())
				.provinciaEmpleador(datosEmpleador.getProvinciaEmpleador())
				.distritoEmpleador(datosEmpleador.getDistritoEmpleador())
				.nombreRepresentante(datosEmpleador.getNombreRepresentante())
				.apellidoRepresentante(datosEmpleador.getApellidoRepresentante())
				.tipoDocumentoEmpleador(datosEmpleador.getTipoDocumentoEmpleador())
				.nroDocumentoEmpleador(datosEmpleador.getNroDocumentoEmpleador())
				.nombresTrabajador(datoTrabajador.getNombresTrabajador())
				.apellidosTrabajador(datoTrabajador.getApellidosTrabajador())
				.nacionalidadTrabajador(datoTrabajador.getNacionalidadTrabajador())
				.tipoDocumentoTrabajador(datoTrabajador.getTipoDocumentoTrabajador())
				.nroDocumentoTrabajador(datoTrabajador.getNroDocumentoTrabajador())
				.domicilioTrabajador(datoTrabajador.getDomicilioTrabajador())
				.departamentoTrabajador(datoTrabajador.getDepartamentoTrabajador())
				.provinciaTrabajador(datoTrabajador.getProvinciaTrabajador())
				.distritoTrabajador(datoTrabajador.getDistritoTrabajador())
				.rubro(clausula.getRubro())
				.desempeño(clausula.getDesempeño())
				.experiencia(clausula.getExperiencia())
				.cargo(clausula.getCargo())
				.actividad(clausula.getActividad())
				.laboresAsignadas(clausula.getLaboresAsignadas())
				.areaLaboral(clausula.getAreaLaboral())
				.tipoContrato(clausula.getTipoContrato())
				.fechaInicio(clausula.getFechaInicio())
				.fechaHasta(clausula.getFechaHasta())
				.esIndefinido(clausula.isEsIndefinido())
				.sueldo(clausula.getSueldo())
				.moneda(clausula.getMoneda())
				.laboresAsignadas2(clausula.getLaboresAsignadas2())
				.build();
	}

}
