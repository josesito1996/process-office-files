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
public class Attachment implements Serializable {

	private static final long serialVersionUID = 5142151203373032048L;
	
	private String content64;
	
	private String disposition;
	
	private String fileName;
	
	private String type;

}
