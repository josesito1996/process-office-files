package com.example.demo.repo;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.stereotype.Repository;

import com.example.demo.model.ResourceSami;


@EnableScan
@Repository
public interface RepoResourceSami extends GenericRepo<ResourceSami, String> {
	
}
