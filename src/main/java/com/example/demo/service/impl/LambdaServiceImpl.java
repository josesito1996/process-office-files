package com.example.demo.service.impl;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;
import com.example.demo.lambda.LambdaFileBase64Request;
import com.example.demo.lambda.LambdaUploadFileRequest;
import com.example.demo.service.LambdaService;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LambdaServiceImpl implements LambdaService {

	@Autowired
	AWSLambda awsLambda;

	@Override
	public String obtenerBase64(LambdaFileBase64Request request) {
		log.info("LambdaServiceImpl.obtenerBase64 {}", request);
		try {
			Gson gson = new Gson();
			String payLoad = gson.toJson(request);
			InvokeRequest invokeRequest = new InvokeRequest().withFunctionName("lambda-test").withPayload(payLoad);
			InvokeResult result = awsLambda.invoke(invokeRequest);
			String ans = new String(result.getPayload().array(), StandardCharsets.UTF_8);
			JsonElement element = JsonParser.parseString(ans);
			return element.getAsString().replace("\n", "");
		} catch (Exception e) {
			log.error("Error al obtener base64 {}", e);
			return "";
		}
	}

	@Override
	public Boolean uploadFile(LambdaUploadFileRequest request) {
		log.info("LambdaServiceImpl.uploadFile");
		try {
			Gson gson = new Gson();
			String payLoad = gson.toJson(request);
			log.info("PayLoad {}", payLoad);
			InvokeRequest invokeRequest = new InvokeRequest().withFunctionName("lambda-test").withPayload(payLoad);
			InvokeResult result = awsLambda.invoke(invokeRequest);
			log.info("REsult {}", result.getPayload());
			String ans = new String(result.getPayload().array(), StandardCharsets.UTF_8);
			return Boolean.parseBoolean(ans);
		} catch (Exception e) {
			log.error("Error al cargar base64 {}", e);
			return false;
		}
	}

}
