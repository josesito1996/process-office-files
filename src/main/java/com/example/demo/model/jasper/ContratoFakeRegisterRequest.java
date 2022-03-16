package com.example.demo.model.jasper;

import java.io.Serializable;

import javax.validation.Valid;
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
public class ContratoFakeRegisterRequest implements Serializable {

	private static final long serialVersionUID = -7735558045158457743L;

	@Valid
	@NotNull
	private ContratoDatosPrincipalesRequest datosPrincipales;
	
	@Valid
	@NotNull
	private ClausulaRequest clausulas;
	
	@NotNull
	@NotEmpty
	private String idTemplate;
}
