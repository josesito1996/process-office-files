package com.example.demo.model.request;

import static com.example.demo.util.Contants.REGEX_EMAIL;
import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

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
public class ContratoEnviarCorreoRequest implements Serializable {

	private static final long serialVersionUID = -7735498009100518174L;

	@NotNull
	private Long id;

	@NotNull
	@NotEmpty
	@Pattern(regexp = REGEX_EMAIL)
	private String correo;

}
