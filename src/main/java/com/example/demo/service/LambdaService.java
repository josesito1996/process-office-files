package com.example.demo.service;

import com.example.demo.lambda.LambdaFileBase64Request;
import com.example.demo.lambda.LambdaUploadFileRequest;
import com.example.demo.model.lambda.LambdaMailRequestSendgrid;
import com.google.gson.JsonObject;

public interface LambdaService {

	String obtenerBase64(LambdaFileBase64Request request);
	
	Boolean uploadFile(LambdaUploadFileRequest request);
	
	JsonObject enviarCorreo(LambdaMailRequestSendgrid request);
}
