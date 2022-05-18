package com.example.demo.lambda;

import java.io.Serializable;
import java.util.List;

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
public class LambdaUploadFileRequest implements Serializable {

	private static final long serialVersionUID = 6185604867149394349L;

	private String httpMethod;
	
	private List<LambdaFileRequest> requestBody;
	
	private String bucketName;
	
}
