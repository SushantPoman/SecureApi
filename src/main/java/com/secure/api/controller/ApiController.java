package com.secure.api.controller;

import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {
	
	final org.slf4j.Logger logger = LoggerFactory.getLogger(ApiController.class);

    @GetMapping("/data")
    public String getSecureData() {
    	logger.info("data sent");
        return "This is Spring Boot API secured data!";
    }
}