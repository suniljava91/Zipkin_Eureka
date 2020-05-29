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
class Microservice1Controller {

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

	@GetMapping(value = "/microservice1")
	public String method1() {
		long startTime = System.currentTimeMillis();
		LOG.info("Inside method1");
		String baseUrl = "http://localhost:8084/microservice2";
		String response = (String) restTemplate.exchange(baseUrl, HttpMethod.GET, null, String.class).getBody();
		long endTime = System.currentTimeMillis();

		long timeElapsed = endTime - startTime;
		LOG.info("The response received by method1 is " + response);
		LOG.info("Execution time in second :  " + timeElapsed);
		return response;
	}
}
