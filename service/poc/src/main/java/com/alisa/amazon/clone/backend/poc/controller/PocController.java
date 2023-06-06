package com.alisa.amazon.clone.backend.poc.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1")
@Slf4j
public class PocController {
    @GetMapping
    public ResponseEntity<String> greeting() {
        log.info("In greeting endpoint");
        return new ResponseEntity<String>("Hello from Kimmy", HttpStatus.OK);
    }
}
