package com.example.demo.config;

import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableDynamoDBRepositories(basePackages = "com.example.demo.repo")
@Slf4j
public class Config {

	@Value("${aws.config.access-key}")
	private String accessKey;

	@Value("${aws.config.secret-key}")
	private String secretKey;

	@Value("${aws.config.service-endpoint}")
	private String serviceEndpoint;

	@Value("${aws.config.region}")
	private String region;
	
	@Value("${spring.profiles.active}")
	private String enviroment;

	public AwsClientBuilder.EndpointConfiguration endpointConfiguration() {
		return new AwsClientBuilder.EndpointConfiguration(serviceEndpoint, region);
	}

	@Bean
	public AmazonDynamoDB amazonDynamoDB() {

		return AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(endpointConfiguration())
				.withCredentials(awsCredentialsProvider()).build();
	}
	
	@Bean
	@Primary
	public DynamoDBMapper dynamoDBMapper(AmazonDynamoDB amazonDynamoDB) {
		log.info("Enviromment {}", enviroment);
		DynamoDBMapper mapper = new DynamoDBMapper(amazonDynamoDB,
				new DynamoDBMapperConfig.Builder().withTableNameResolver(new TableNameResolver(enviroment)).build());
		return mapper;
	}

	@Bean
	public DynamoDB getDynamoDB() {
		return new DynamoDB(amazonDynamoDB());
	}

	public AWSCredentialsProvider awsCredentialsProvider() {
		return new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey));
	}

	@Bean
	public AWSLambda getAwsLambda() {
		return AWSLambdaClientBuilder.standard().withCredentials(awsCredentialsProvider()).withRegion(Regions.US_EAST_2)
				.build();
	}

}
