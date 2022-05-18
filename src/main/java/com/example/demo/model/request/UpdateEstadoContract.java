package com.example.demo.model.request;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
@ToString
public class UpdateEstadoContract implements Serializable {

	private static final long serialVersionUID = -14057979566626194L;

	private Long id;
	
	private Long idEstadoContrato;
	
}
