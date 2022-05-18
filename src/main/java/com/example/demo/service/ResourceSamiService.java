package com.example.demo.service;

import com.example.demo.model.ResourceSami;

public interface ResourceSamiService extends ICrud<ResourceSami, String> {

	
	ResourceSami verUnoPorId(String id);
	
}
