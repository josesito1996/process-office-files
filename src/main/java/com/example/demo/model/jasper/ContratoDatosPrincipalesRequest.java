package com.example.demo.model.jasper;

import java.io.Serializable;

import javax.validation.Valid;
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
public class ContratoDatosPrincipalesRequest implements Serializable {

	private static final long serialVersionUID = 8179020004278763213L;

	@NotNull
	@Valid
	private DatosEmpleadorRequest datosEmpleador;
	
	@NotNull
	@Valid
	private DatosTrabajadorRequest datosTrabajador;

}
