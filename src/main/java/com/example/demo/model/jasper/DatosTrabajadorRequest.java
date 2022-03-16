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
public class DatosTrabajadorRequest implements Serializable {

	private static final long serialVersionUID = 6891662130349460474L;

	@NotNull
	@NotEmpty
	private String nombresTrabajador;

	@NotNull
	@NotEmpty
	private String apellidosTrabajador;

	@NotNull
	@NotEmpty
	private String nacionalidadTrabajador;

	@NotNull
	@NotEmpty
	private String tipoDocumentoTrabajador;

	@NotNull
	@NotEmpty
	@Size(min = 8)
	private String nroDocumentoTrabajador;

	@NotNull
	@NotEmpty
	private String domicilioTrabajador;

	@NotNull
	@NotEmpty
	private String departamentoTrabajador;

	@NotNull
	@NotEmpty
	private String provinciaTrabajador;

	@NotNull
	@NotEmpty
	private String distritoTrabajador;

}
