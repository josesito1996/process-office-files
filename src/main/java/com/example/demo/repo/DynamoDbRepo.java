package com.example.demo.repo;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.example.demo.repo.model.ResourceSamiPojo;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DynamoDbRepo {

	@Autowired
	private DynamoDB dynamoDB;

	public ResourceSamiPojo resourceSamiById(String id) {
		log.info("DynamoDbRepo.resourceSamiById");
		Table tagleResource = dynamoDB.getTable("resources_sami");
		GetItemSpec spec = new GetItemSpec().withPrimaryKey("id_resource", id);
		Item resourceItem = tagleResource.getItem(spec);
		final ObjectMapper mapper = new ObjectMapper();
		if (resourceItem == null) {
			return ResourceSamiPojo.builder().build();
		}
		return mapper.convertValue(resourceItem.asMap(), ResourceSamiPojo.class);
	}
	
	public void updateResource(String id, String url) {
		
	}

}
