package com.alisa.amazon.clone.backend.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1")
@Slf4j
public class SimpleRestApiController {

    @Value("${spring.application.name:mydefault}")
    public String appName;

    @GetMapping
    public ResponseEntity<String> greeting() {
        log.info("In greeting endpoint: {} application", appName);
        return new ResponseEntity<String>("Hello from Kimmy!! App name = " + appName, HttpStatus.OK);
    }
}
