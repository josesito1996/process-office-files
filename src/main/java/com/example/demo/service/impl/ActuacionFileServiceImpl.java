package com.example.demo.service.impl;

import static com.example.demo.util.Utils.getExtension;
import static com.example.demo.util.Utils.base64Complete;

import java.io.File;
import java.util.Arrays;

import static com.example.demo.util.Utils.base64ToFile;
import static com.example.demo.util.Utils.fileToBase64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.BadRequestException;
import com.example.demo.lambda.LambdaFileBase64Request;
import com.example.demo.lambda.LambdaFileRequest;
import com.example.demo.lambda.LambdaUploadFileRequest;
import com.example.demo.model.ActuacionFileRequest;
import com.example.demo.model.ActuacionFileResponse;
import com.example.demo.service.ActuacionFileService;
import com.example.demo.service.ImageService;
import com.example.demo.service.LambdaService;

@Service
public class ActuacionFileServiceImpl implements ActuacionFileService {

	@Autowired
	LambdaService lambdaService;

	@Autowired
	ImageService imageService;

	@Override
	public ActuacionFileResponse uploadFile(ActuacionFileRequest request) {
		final String base64 = lambdaService.obtenerBase64(LambdaFileBase64Request.builder().httpMethod("GET")
				.idFile(request.getIdArchivo().concat(getExtension(request.getNombreArchivo()))).type(request.getType())
				.fileName(request.getNombreArchivo()).bucketName("")// Null para el bucket de arhivos de SAMY
				.build());
		if (base64.equals("---")) {
			throw new BadRequestException("Archivo no existe en AWS");
		}
		File archivo = base64ToFile(base64, "", request.getNombreArchivo());
		if (archivo.exists()) {
			String extension = getExtension(archivo.getName());
			File filePng = null;
			switch (extension) {
			case ".docx":
				filePng = imageService.imageWord(archivo);
				break;
			case ".xlsx":
				filePng = imageService.imageExcel(archivo);
				break;
			case ".pptx":
				filePng = imageService.imagePpt(archivo);
				break;
			case ".pdf":
				filePng = imageService.imagePdf(archivo);
				break;
			default:
				break;
			}
			archivo.delete();
			if (filePng.exists()) {
				final String fileBase64 = fileToBase64(filePng);
				final String fileNamePng = request.getIdArchivo().concat(getExtension(filePng.getName()));
				filePng.delete();
				final boolean resultado = lambdaService.uploadFile(LambdaUploadFileRequest.builder()
						.httpMethod("POST")
						.requestBody(Arrays.asList(LambdaFileRequest.builder()
								.idFile(request.getIdArchivo())
								.nombreArchivo(fileNamePng)
								.base64(base64Complete(request.getType(), fileBase64))
								.build()))
						.bucketName(null)
						.build());
				if (resultado) {
					String url = "https://79z25zohcj.execute-api.us-east-2.amazonaws.com/dev/api-files/fileTest/";
					return ActuacionFileResponse.builder()
							.id(request.getIdArchivo())
							.url(url.concat(fileNamePng).concat("?condicion=false"))
							.build(); 
				} else {
					throw new BadRequestException("Error al subir archivo PNG");
				}
			} else {
				throw new BadRequestException("Archivo png no existe");
			}
		} else {
			throw new BadRequestException("Error al crear archivo desde Amazon");
		}
	}

}
