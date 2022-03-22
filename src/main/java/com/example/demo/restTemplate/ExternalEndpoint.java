package com.example.demo.restTemplate;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.model.jasper.ContratoRequestJasper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ExternalEndpoint {

	@Value("${endpoints.api-contrato}")
	private String apiContrato;

	private RestTemplate restTemplate;

	public ExternalEndpoint(RestTemplateBuilder builder) {
		log.info("ExternalEndpoint.apiContratos : {}", apiContrato);
		restTemplate = builder.errorHandler(new RestTemplateErrorHandler()).build();
	}
	
	public List<ContratoRequestJasper> viewOne(Long id) {
		log.info("ExternalEndpoint.viewOne  : {} ", id);
		ContratoRequestJasper[] lista = restTemplate.getForObject(apiContrato.concat("viewOne/{id}"),
				ContratoRequestJasper[].class, new Object[] { id });
		return Arrays.asList(lista);
	}

}
