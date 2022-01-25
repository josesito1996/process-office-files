package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import com.example.demo.controller.ProcessController;
import com.example.demo.model.FileRequest;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ProcessOfficeFilesApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private ProcessController controller;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}

	@Test
	void controllerTest() throws Exception {
		String id = "67f4a66f-6d89-41bb-b639-372a078acb43";
		assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/api-files/getFile",
				FileRequest.builder().idFile(id).build(), String.class))
						.contains("67f4a66f-6d89-41bb-b639-372a078acb43");
	}

}
