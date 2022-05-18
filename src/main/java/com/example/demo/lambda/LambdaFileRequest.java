package com.example.demo.lambda;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Builder
@NoArgsConstructor
@Setter
@ToString
public class LambdaFileRequest implements Serializable {

	private static final long serialVersionUID = 7372198891409020091L;

	private String idFile;
	
	private String nombreArchivo;
	
	private String base64;
	
}
