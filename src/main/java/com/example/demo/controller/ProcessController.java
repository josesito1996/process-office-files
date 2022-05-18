package com.example.demo.controller;

import static com.example.demo.util.Utils.base64ToFile;
import static com.example.demo.util.Utils.copyInputStreamToFile;
import static com.example.demo.util.Utils.fileNameNoExtension;
import static com.example.demo.util.Utils.fileToBase64;
import static com.example.demo.util.Utils.getExtension;
import static com.example.demo.util.Contants.REGEX_UUID;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.exception.BadRequestException;
import com.example.demo.lambda.LambdaFileBase64Request;
import com.example.demo.lambda.LambdaFileRequest;
import com.example.demo.lambda.LambdaUploadFileRequest;
import com.example.demo.model.ActuacionFileRequest;
import com.example.demo.model.ActuacionFileResponse;
import com.example.demo.model.FileRequest;
import com.example.demo.model.ResourceSami;
import com.example.demo.service.ActuacionFileService;
import com.example.demo.service.ImageService;
import com.example.demo.service.LambdaService;
import com.example.demo.service.ResourceSamiService;

import lombok.extern.slf4j.Slf4j;

@Validated
@RestController
@RequestMapping("/api-files")
@Slf4j
public class ProcessController {

	@Autowired
	ActuacionFileService actuacionFileService;
	
	@Autowired
	ImageService imageService;

	@Autowired
	LambdaService lambdaService;

	@Autowired
	ResourceSamiService resourceSamiService;

	@Autowired
	Environment entornos;

	@GetMapping(path = "/lambdaTest/{id}")
	public String lambdaTest(@PathVariable String id) {
		ResourceSami resource = resourceSamiService.verUnoPorId(id);
		return lambdaService.obtenerBase64(LambdaFileBase64Request.builder().httpMethod("GET")
				.idFile(resource.getId().concat(getExtension(resource.getFileName()))).type(resource.getType())
				.fileName(resource.getCustomFileName()).bucketName("recursos-sami").build());
	}

	@GetMapping(path = "/fileTest/{id}")
	public ResponseEntity<InputStreamResource> getImage(@PathVariable String id,
			@RequestParam(required = false, defaultValue = "false") boolean condicion) throws Exception {
		try {
			// String fileFolder = System.getenv("FILES_FOLDER").concat("/");
			ResourceSami resource = resourceSamiService.verUnoPorId(id);
			String base64 = lambdaService.obtenerBase64(LambdaFileBase64Request.builder().httpMethod("GET")
					.idFile(resource.getId().concat(".png")).type("image/png").fileName(resource.getId().concat(".png"))
					.bucketName(condicion ? "recursos-sami" : "archivos-samy-v1").build());
			File fileBase64 = base64ToFile(base64, "", resource.getId().concat(".png"));
			InputStreamResource inputResource = new InputStreamResource(new FileInputStream(fileBase64));
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.parseMediaType("image/png"));
			headers.add("Access-Control-Allow-Origin", "*");
			headers.add("Access-Control-Allow-Methods", "GET, POST, PUT");
			headers.add("Access-Control-Allow-Headers", "Content-Type");
			headers.add("Content-Disposition", "filename=" + fileBase64.getName());
			headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
			headers.add("Pragma", "no-cache");
			headers.add("Expires", "0");
			headers.setContentLength(fileBase64.length());
			fileBase64.delete();
			return new ResponseEntity<InputStreamResource>(inputResource, headers, HttpStatus.OK);
			/*
			 * InputStreamResource inputResource = new InputStreamResource(new
			 * FileInputStream(fileBase64)); HttpHeaders headers = new HttpHeaders();
			 * headers.setContentType(MediaType.parseMediaType(resource.getType()));
			 * headers.add("Access-Control-Allow-Origin", "*");
			 * headers.add("Access-Control-Allow-Methods", "GET, POST, PUT");
			 * headers.add("Access-Control-Allow-Headers", "Content-Type");
			 * headers.add("Content-Disposition", "filename=" + fileBase64.getName());
			 * headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
			 * headers.add("Pragma", "no-cache"); headers.add("Expires", "0");
			 * headers.setContentLength(fileBase64.length()); fileBase64.delete(); return
			 * new ResponseEntity<InputStreamResource>(inputResource, headers,
			 * HttpStatus.OK);
			 */
		} catch (Exception e) {
			throw new Exception("Error {}" + e);
		}
	}

	@PostMapping(path = "/getFile")
	public String getFile(@RequestBody FileRequest request) {
		ResourceSami resource = resourceSamiService.verUnoPorId(request.getIdFile());
		if (resource.getId() == null) {
			return "";
		}
		// String fileFolder = System.getenv("FILES_FOLDER").concat("/");
		String base64 = lambdaService.obtenerBase64(LambdaFileBase64Request.builder().httpMethod("GET")
				.idFile(resource.getId().concat(getExtension(resource.getFileName()))).type(resource.getType())
				.fileName(resource.getCustomFileName()).bucketName("recursos-sami").build());
		File fileBase64 = base64ToFile(base64, "", resource.getFileName());
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
				String base64FilePng = "data:".concat(resource.getType()).concat(";base64,")
						.concat(fileToBase64(filePng));
				String fileNamePng = fileNameNoExtension(resource.getCustomFileName())
						.concat(getExtension(filePng.getName()));
				Boolean resultadoLambda = lambdaService.uploadFile(LambdaUploadFileRequest.builder().httpMethod("POST")
						.requestBody(Arrays.asList(LambdaFileRequest.builder().idFile(resource.getId())
								.nombreArchivo(fileNamePng).base64(base64FilePng).build()))
						.bucketName("recursos-sami").build());
				if (!resultadoLambda) {
					throw new BadRequestException("Error al cargar arhivo de imagen");
				}
				if (filePng.delete()) {
					String url = "https://79z25zohcj.execute-api.us-east-2.amazonaws.com/dev/api-files/fileTest/";
					String filePngName = resource.getId();
					resource.setPngFileName(fileNamePng);
					resource.setUrl(url.concat(filePngName).concat("?condicion=true"));
					return resourceSamiService.modificar(resource).getId();
				}
			}
			fileBase64.delete();
		}
		return "---";
	}

	@PostMapping(path = "/process")
	public String fileToImage(@RequestParam MultipartFile file, @RequestParam String extension) {
		try {
			log.info("ProcessController.wordFileImage");
			log.info("ProcessController.wordFileImage.extension {}", extension);
			File fileDoc = new File(file.getName());
			copyInputStreamToFile(file.getInputStream(), fileDoc);
			switch (extension) {
			case "DOCX":
				imageService.imageWord(fileDoc);
				break;
			case "XLSX":
				imageService.imageExcel(fileDoc);
				break;
			case "PPTX":
				imageService.imagePpt(fileDoc);
				break;
			case "PDF":
				imageService.imagePdf(fileDoc);
				break;
			default:
				break;
			}
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	@PostMapping(path = "/uploadFilePngActuacion")
	public ActuacionFileResponse uploadFile(@Valid @RequestBody ActuacionFileRequest request) {
		return actuacionFileService.uploadFile(request);
	}
	
	@GetMapping(path = "/fileTestActuacion/{id}")
	public ResponseEntity<InputStreamResource> getImageActuacion(@Valid @PathVariable @Pattern(regexp = REGEX_UUID) String id,@RequestParam(defaultValue = "archivos-samy-v1") String bucketName) throws Exception {
			String base64 = lambdaService.obtenerBase64(LambdaFileBase64Request.builder().httpMethod("GET")
					.idFile(id.concat(".png")).type("image/png").fileName(id.concat(".png"))
					.bucketName(bucketName).build());
			if (base64.equals("---")) {
				throw new BadRequestException("Archivo PNG no existe en AWS");
			}
			File fileBase64 = base64ToFile(base64, "", id.concat(".png"));
			InputStreamResource inputResource = new InputStreamResource(new FileInputStream(fileBase64));
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.parseMediaType("image/png"));
			headers.add("Access-Control-Allow-Origin", "*");
			headers.add("Access-Control-Allow-Methods", "GET, POST, PUT");
			headers.add("Access-Control-Allow-Headers", "Content-Type");
			headers.add("Content-Disposition", "filename=" + fileBase64.getName());
			headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
			headers.add("Pragma", "no-cache");
			headers.add("Expires", "0");
			headers.setContentLength(fileBase64.length());
			fileBase64.delete();
			return new ResponseEntity<InputStreamResource>(inputResource, headers, HttpStatus.OK);
			/*
			 * InputStreamResource inputResource = new InputStreamResource(new
			 * FileInputStream(fileBase64)); HttpHeaders headers = new HttpHeaders();
			 * headers.setContentType(MediaType.parseMediaType(resource.getType()));
			 * headers.add("Access-Control-Allow-Origin", "*");
			 * headers.add("Access-Control-Allow-Methods", "GET, POST, PUT");
			 * headers.add("Access-Control-Allow-Headers", "Content-Type");
			 * headers.add("Content-Disposition", "filename=" + fileBase64.getName());
			 * headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
			 * headers.add("Pragma", "no-cache"); headers.add("Expires", "0");
			 * headers.setContentLength(fileBase64.length()); fileBase64.delete(); return
			 * new ResponseEntity<InputStreamResource>(inputResource, headers,
			 * HttpStatus.OK);
			 */
	}
	
}
