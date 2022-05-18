package com.example.demo.model;

import java.io.Serializable;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Builder
@DynamoDBTable(tableName = "resources_sami")
@Getter
@NoArgsConstructor
@Setter
@ToString
public class ResourceSami implements Serializable {

	private static final long serialVersionUID = 3120009878273156733L;

	@DynamoDBHashKey
	@DynamoDBAutoGeneratedKey
	@DynamoDBAttribute(attributeName = "id_resource")
	private String id;

	@DynamoDBAttribute
	private String uploadDate;

	@DynamoDBAttribute
	private String fileName;

	@DynamoDBAttribute
	private String customFileName;

	@DynamoDBAttribute
	private String description;

	@DynamoDBAttribute
	private String type;

	@DynamoDBAttribute
	private Boolean isUtil;

	@DynamoDBAttribute
	private Boolean isFavorite;

	@DynamoDBAttribute
	private Integer bytes;

	@DynamoDBAttribute
	private Boolean isRemoved;

	@DynamoDBAttribute
	private String userName;

	@DynamoDBAttribute
	private String category;

	@DynamoDBAttribute
	private String url;

	@DynamoDBAttribute
	private String pngFileName;
}
