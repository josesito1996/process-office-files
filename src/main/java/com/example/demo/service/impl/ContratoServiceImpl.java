package com.example.demo.service.impl;

import static com.example.demo.util.Utils.byteArrayToBase64;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.jasper.ClausulaRequest;
import com.example.demo.model.jasper.ContratoDatosPrincipalesRequest;
import com.example.demo.model.jasper.ContratoFakeRegisterRequest;
import com.example.demo.model.jasper.ContratoRequestJasper;
import com.example.demo.model.jasper.DatosEmpleadorRequest;
import com.example.demo.model.jasper.DatosTrabajadorRequest;
import com.example.demo.model.lambda.Attachment;
import com.example.demo.model.lambda.LambdaMailRequestSendgrid;
import com.example.demo.model.request.ContratoEnviarCorreoRequest;
import com.example.demo.restTemplate.ExternalEndpoint;
import com.example.demo.service.ContratoService;
import com.example.demo.service.JasperService;
import com.example.demo.service.LambdaService;
import com.example.demo.util.Utils;
import com.google.gson.JsonObject;

@Service
public class ContratoServiceImpl implements ContratoService {

	@Autowired
	private ExternalEndpoint external;

	@Autowired
	private LambdaService lambdaService;

	@Autowired
	private JasperService jasperService;

	@Override
	public boolean enviarContratoPorCorreo(ContratoEnviarCorreoRequest request) {
		List<ContratoRequestJasper> list = external.viewOne(request.getId());
		if (list.isEmpty()) {
			throw new BadRequestException("No Existe contrato con el ID " + request);
		}
		String mimeType = "aplication/pdf";
		byte[] arrayBytes = jasperService.pdfContrato1(list);
		ContratoRequestJasper contrato = list.stream().findFirst().orElse(ContratoRequestJasper.builder().build());
		String base64 = byteArrayToBase64(arrayBytes, mimeType, false);
		JsonObject obj = lambdaService
				.enviarCorreo(
						LambdaMailRequestSendgrid.builder().emailFrom("notificacion.sami@sidetechsolutions.com")
								.subject("Contrato de Trabajo : ".concat(contrato.getNombresTrabajador().concat(" ")
										.concat(contrato.getApellidosTrabajador()).concat(" - ")
										.concat(contrato.getNroDocumentoTrabajador())))
								.emailTo(request.getCorreo())
								.content("Estimado colaborador, se le adjunta su correo contrato de trabajo")
								.archivos(Arrays.asList(Attachment.builder().content64(base64).disposition("attachment")
										.fileName(contrato.getNombresTrabajador() + " "
												+ contrato.getApellidosTrabajador().concat(".pdf"))
										.type(mimeType).build()))
								.build());
		int statusCode = obj.get("code").getAsInt();
		return statusCode == 202;
	}

	@Override
	public byte[] pdfContrato(ContratoFakeRegisterRequest request) {
		return jasperService.pdfContrato1(Arrays.asList(transform(request)));
	}

	@Override
	public String pdfContrato1(ContratoFakeRegisterRequest request) {

		return byteArrayToBase64(pdfContrato(request), "application/pdf", true);
	}

	private ContratoRequestJasper transform(ContratoFakeRegisterRequest request) {
		String zone = "America/Lima";
		LocalDateTime fechaActual = LocalDateTime.now();
		ContratoDatosPrincipalesRequest datosPrincipales = request.getDatosPrincipales();
		ClausulaRequest clausula = request.getClausulas();
		DatosEmpleadorRequest datosEmpleador = datosPrincipales.getDatosEmpleador();
		DatosTrabajadorRequest datoTrabajador = datosPrincipales.getDatosTrabajador();
		return ContratoRequestJasper.builder().idTemplate(request.getIdTemplate())
				.razonSocial(datosEmpleador.getRazonSocial()).nroPartida(datosEmpleador.getNroPartida())
				.oficinaRegistral(datosEmpleador.getOficinaRegistral()).ruc(datosEmpleador.getRuc())
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
				.distritoTrabajador(datoTrabajador.getDistritoTrabajador()).rubro(clausula.getRubro())
				.desempenio(clausula.getDesempenio()).experiencia(clausula.getExperiencia()).cargo(clausula.getCargo())
				.actividad(clausula.getActividad()).laboresAsignadas(clausula.getLaboresAsignadas())
				.areaLaboral(clausula.getAreaLaboral()).tipoContrato(clausula.getTipoContrato())
				.fechaInicio(clausula.getFechaInicio()).fechaHasta(clausula.getFechaHasta())
				.esIndefinido(clausula.isEsIndefinido()).sueldo(clausula.getSueldo()).moneda(clausula.getMoneda())
				.laboresAsignadas2(clausula.getLaboresAsignadas2()).dia(Utils.dateZone(zone, fechaActual, "dd"))
				.mes(Utils.dateZone(zone, fechaActual, "MMMM")).anio(Utils.dateZone(zone, fechaActual, "yyyy")).build();
	}
}
