package com.example.demo.model.lambda;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
@Setter
@ToString
public class LambdaFileBase64Request implements Serializable {

	private static final long serialVersionUID = -3545427961114912278L;

	private String httpMethod;
	
	private String idFile;
	
	private String type;
	
	private String fileName;
	
	private String bucketName;
	
}
