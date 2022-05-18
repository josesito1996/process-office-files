package com.example.demo.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.NotFoundException;
import com.example.demo.model.ResourceSami;
import com.example.demo.repo.GenericRepo;
import com.example.demo.repo.RepoResourceSami;
import com.example.demo.service.ResourceSamiService;

@Service
public class ResourceSamiServiceImpl extends CrudImpl<ResourceSami, String> implements ResourceSamiService {

	@Autowired
	private RepoResourceSami repo;

	@Override
	protected GenericRepo<ResourceSami, String> getRepo() {
		return repo;
	}

	@Override
	public ResourceSami verUnoPorId(String id) {
		Optional<ResourceSami> uno = verPorId(id);
		if (uno.isEmpty()) {
			throw new NotFoundException("No existe registro con ID : " + id);
		}
		return uno.get();
	}

}
