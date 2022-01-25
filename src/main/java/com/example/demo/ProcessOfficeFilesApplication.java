package com.example.demo;

import static com.example.demo.util.Utils.base64ToFile;
import static com.example.demo.util.Utils.fileNameNoExtension;
import static com.example.demo.util.Utils.fileToBase64;
import static com.example.demo.util.Utils.getExtension;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.exception.BadRequestException;
import com.example.demo.lambda.LambdaFileBase64Request;
import com.example.demo.lambda.LambdaFileRequest;
import com.example.demo.lambda.LambdaUploadFileRequest;
import com.example.demo.model.ResourceSami;
import com.example.demo.service.ImageService;
import com.example.demo.service.LambdaService;
import com.example.demo.service.ResourceSamiService;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class ProcessOfficeFilesApplication implements CommandLineRunner {

	@Autowired
	ImageService imageService;

	@Autowired
	LambdaService lambdaService;
	
	@Autowired
	ResourceSamiService resourceSamiService;
	
	public static void main(String[] args) {
		SpringApplication.run(ProcessOfficeFilesApplication.class, args);
	}

	@Override
	public void run(String... args) throws IOException {
		
		log.info("Iniciando aplicacion... :)" + LocalDateTime.now());
		ResourceSami resource = resourceSamiService.verUnoPorId("1a4d63cf-c012-4331-a8ea-2277a11ca9ce");
		String fileFolder = System.getenv("FILES_FOLDER").concat("/");
		String base64 = lambdaService.obtenerBase64(LambdaFileBase64Request.builder()
				.httpMethod("GET")
				.idFile(resource.getId().concat(getExtension(resource.getFileName())))
				.type(resource.getType())
				.fileName(resource.getCustomFileName())
				.bucketName("recursos-sami")
				.build());
			File fileBase64 = base64ToFile(base64, fileFolder, resource.getId().concat(getExtension(resource.getFileName())));
			if (fileBase64.exists()) {
				String extension = getExtension(fileBase64.getName());
				File filePng = null;
				switch (extension) {
				case ".docx":
					filePng = imageService.imageWord(fileBase64);
					break;
				case ".xlsx":
					filePng = imageService.imageExcel(fileBase64);
					break;
				case ".pptx":
					filePng = imageService.imagePpt(fileBase64);
					break;
				case ".pdf":
					filePng = imageService.imagePdf(fileBase64);
					break;
				default:
					break;
				}
				if (filePng.exists()) {
					String base64FilePng = "data:"
							.concat(resource.getType())
							.concat(";base64,")
							.concat(fileToBase64(filePng));
					String fileNamePng = fileNameNoExtension(resource.getCustomFileName()).concat(getExtension(filePng.getName()));
					Boolean resultadoLambda = lambdaService.uploadFile(LambdaUploadFileRequest.builder()
							.httpMethod("POST")
							.requestBody(Arrays.asList(LambdaFileRequest.builder()
									.idFile(resource.getId())
									.nombreArchivo(fileNamePng)
									.base64(base64FilePng)
									.build()))
							.bucketName("recursos-sami")
							.build());
					if (!resultadoLambda) {
						throw new BadRequestException("Error al cargar arhivo de imagen");
					}
					if(filePng.delete()) {
						String url = "https://recursos-sami.s3.us-east-2.amazonaws.com/";
						String filePngName = resource.getId().concat(getExtension(fileNamePng));
						resource.setPngFileName(fileNamePng);
						resource.setUrl(url.concat(filePngName));
						log.info(resourceSamiService.modificar(resource).getId());
					}
				}
				fileBase64.delete();
		}
	
	}

}
