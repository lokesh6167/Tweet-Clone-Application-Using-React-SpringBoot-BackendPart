package com.example.demo;

import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

@Configuration
@EnableDynamoDBRepositories(basePackages = "com.tweetapp.repository")
public class DynamoDbConfig {

	@Value("${amazon.access.key}")
	private String accesskey;

	@Value("${amazon.access.secret-key}")
	private String secretkey;

	@Value("${amazon.region}")
	private String region;

	@Value("${amazon.end-point.url}")
	private String endpoint;

	@SuppressWarnings("deprecation")
	@Bean
	    public AmazonDynamoDB amazonDynamoDB() {
	        AmazonDynamoDB amazonDynamoDB
	                = new AmazonDynamoDBClient(amazonAWSCredentials());

	        if (!endpoint.isEmpty()) {
	            amazonDynamoDB.setEndpoint(endpoint);
	        }

	        return AmazonDynamoDBClientBuilder.standard()
	                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint, region))
	                .withCredentials(amazonAWSCredentialsProvider())
	                .build();
	    }

	    public AWSCredentialsProvider amazonAWSCredentialsProvider() {
	        return new AWSStaticCredentialsProvider(amazonAWSCredentials());
	    }

	    @Bean
	    public AWSCredentials amazonAWSCredentials() {
	        return new BasicAWSCredentials(accesskey, secretkey);
	    }

}
