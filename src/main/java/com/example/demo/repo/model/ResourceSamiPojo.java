package com.example.demo.repo.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

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
public class ResourceSamiPojo implements Serializable {

	private static final long serialVersionUID = -3326580802206759023L;

	@JsonProperty("id_resource")
	private String id;

	private String uploadDate;

	private String fileName;

	private String customFileName;

	private String description;

	private String type;

	private Integer isUtil;

	private Integer isFavorite;

	private Integer bytes;

	private Integer isRemoved;

	private String userName;

	private String category;

}
