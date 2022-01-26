package com.example.demo.model;

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
@Setter
@NoArgsConstructor
@ToString
public class ActuacionFileRequest implements Serializable {

	private static final long serialVersionUID = -807407274620743302L;

	private String idArchivo;
	
	private String nombreArchivo;
	
	private String type;
	
}
