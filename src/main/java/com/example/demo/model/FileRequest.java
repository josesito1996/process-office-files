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
@NoArgsConstructor
@Setter
@ToString
public class FileRequest implements Serializable {

	private static final long serialVersionUID = 2643145958431993078L;
	
	String idFile;
	
}
