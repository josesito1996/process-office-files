package com.example.demo.util;

import static com.example.demo.util.Contants.DEFAULT_BUFFER_SIZE;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.TimeZone;
import java.util.stream.Collectors;

import com.example.demo.exception.BadRequestException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Utils {

	public static void copyInputStreamToFile(InputStream inputStream, File file) throws IOException {
		log.info("Utils.copyInputStreamToFile");
		try (FileOutputStream outputStream = new FileOutputStream(file, false)) {
			int read;
			byte[] bytes = new byte[DEFAULT_BUFFER_SIZE];
			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
		}
	}

	public static File base64ToFile(String base64, String filePath, String fileName) {
		log.info("Utils.base64ToFile");
		try {
			byte[] decodedFile = Base64.getDecoder().decode(getBase64Fragment(base64).getBytes(StandardCharsets.UTF_8));
			Path destinationFile = Paths.get(filePath, fileName);
			Files.write(destinationFile, decodedFile);
			return destinationFile.toFile();
		} catch (Exception e) {
			log.error("Error {}", e);
			return null;
		}
	}

	public static String byteArrayToBase64(byte[] arrayBytes, String mimeType, boolean includeMime) {
		if (arrayBytes.length == 0) {
			throw new BadRequestException("El array de Bytes esta vacio");
		}
		return (includeMime ? ("data:" + mimeType + ";base64,") : "")
				.concat(Base64.getEncoder().encodeToString(arrayBytes));
	}

	public static String fileToBase64(File file) {
		try {
			byte[] fileContent = Files.readAllBytes(file.toPath());
			return Base64.getEncoder().encodeToString(fileContent);
		} catch (IOException e) {
			log.error("Error al convertir archivo a base64 {}", e);
			throw new IllegalStateException("could not read file " + file, e);
		}
	}

	public static String base64Complete(String type, String base64) {
		return "data:".concat(type).concat(";base64,").concat(base64);
	}

	private static String getBase64Fragment(String base64) {
		if (!base64.isEmpty()) {
			String[] cadenas = base64.split(",");
			return cadenas[1];
		}
		return "";
	}

	public static String getExtension(String fileName) {
		return fileName.substring(fileName.indexOf("."), fileName.length());
	}

	public static String fileNameNoExtension(String fileName) {
		return fileName.substring(0, fileName.indexOf("."));
	}

	/**
	 * Parametros para formato de fecha : Dia : "dd" Mes : "MMMM" Anio : yyyy
	 * 
	 * @param zoneTime
	 * @param fechaActual
	 * @return
	 */
	public static String dateZone(String zoneTime, LocalDateTime fechaActual, String paramDate) {
		TimeZone timeZone = TimeZone.getTimeZone(zoneTime);
		Date fecha = convertToDateViaInstant(fechaActual);
		return fecha.toInstant().atZone(timeZone.toZoneId()).toLocalDateTime()
				.format(DateTimeFormatter.ofPattern(paramDate));
	}

	private static Date convertToDateViaInstant(LocalDateTime dateToConvert) {
		return java.util.Date.from(dateToConvert.atZone(ZoneId.systemDefault()).toInstant());
	}

	public static String textToLineBreaks(String text, String delimiter, String relleno) {
		String[] descompuesto = text.split(delimiter);
		return Arrays.asList(descompuesto).stream().collect(Collectors.joining("\n"))
				.concat(relleno != null ? "\n".concat(relleno) : "");
	}

}
