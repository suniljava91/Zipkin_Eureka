package com.zipkin.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import brave.sampler.Sampler;

import java.time.Duration;
import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
class Microservice3Controller {

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());

	@Autowired
	RestTemplate restTemplate;

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	@Bean
	public Sampler defaultSampler() {
		return Sampler.ALWAYS_SAMPLE;
	}
	
	@GetMapping(value = "/microservice3")
	public String method3() {
		long startTime = System.currentTimeMillis();
		LOG.info("Inside method3");
		String baseUrl = "http://localhost:8088/microservice4";
		try {
		Thread.sleep(1000);
		} catch (Exception ex) {

		}
		String response = (String) restTemplate.exchange(baseUrl, HttpMethod.GET, null, String.class).getBody();
		
		long endTime = System.currentTimeMillis();

		long timeElapsed = endTime - startTime;
		
		LOG.info("The response received by method3 is " + response);
		LOG.info("Execution time in second :  " + timeElapsed);
		
		return response;
	}
}
