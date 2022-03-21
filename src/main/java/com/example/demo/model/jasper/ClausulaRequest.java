package com.example.demo.model.jasper;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
public class ClausulaRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1555904018515834970L;

	@NotNull
	@NotEmpty
	private String rubro;
	
	@NotNull
	@NotEmpty
	private String desempenio;
	
	@NotNull
	@NotEmpty
	private String experiencia;
	
	@NotNull
	@NotEmpty
	private String cargo;
	
	@NotNull
	@NotEmpty
	private String actividad;
	
	@NotNull
	@NotEmpty
	private String laboresAsignadas;
	
	@NotNull
	@NotEmpty
	private String areaLaboral;
	
	@NotNull
	@NotEmpty
	private String tipoContrato;
	
	@NotNull
	@NotEmpty
	private String fechaInicio;
	
	private String fechaHasta;
	
	private boolean esIndefinido;
	
	@NotNull
	private Double sueldo;
	
	@NotNull
	@NotEmpty
	private String moneda;
	
	@NotNull
	@NotEmpty
	private String laboresAsignadas2;
	
}
