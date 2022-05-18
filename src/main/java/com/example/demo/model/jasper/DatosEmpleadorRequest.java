package com.example.demo.model.jasper;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
public class DatosEmpleadorRequest implements Serializable {

	private static final long serialVersionUID = -8630787738854901165L;

	@NotNull
	@NotEmpty
	private String razonSocial;
	
	@NotNull
	@NotEmpty
	private String nroPartida;
	
	@NotNull
	@NotEmpty
	private String oficinaRegistral;
	
	@NotNull
	@NotEmpty
	@Size(min = 11)
	private String ruc;
	
	@NotNull
	@NotEmpty
	private String domicilioEmpleador;
	
	@NotNull
	@NotEmpty
	private String departamentoEmpleador;
	
	@NotNull
	@NotEmpty
	private String provinciaEmpleador;
	
	@NotNull
	@NotEmpty
	private String distritoEmpleador;
	
	@NotNull
	@NotEmpty
	private String nombreRepresentante;
	
	@NotNull
	@NotEmpty
	private String apellidoRepresentante;
	
	@NotNull
	@NotEmpty
	private String tipoDocumentoEmpleador;
	
	@NotNull
	@NotEmpty
	private String nroDocumentoEmpleador;
	
}
