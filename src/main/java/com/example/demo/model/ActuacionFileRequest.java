package com.example.demo.model;

import static com.example.demo.util.Contants.REGEX_UUID;
import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

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

	@NotNull
	@NotNull
	@Pattern(regexp = REGEX_UUID)
	private String idArchivo;
	
	@NotNull
	@NotNull
	private String nombreArchivo;
	
	@NotNull
	@NotNull
	private String type;
	
}
