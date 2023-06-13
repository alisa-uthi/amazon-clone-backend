package com.alisa.amazon.clone.backend.controller;

import com.alisa.amazon.clone.backend.entity.MyUser;
import com.alisa.amazon.clone.backend.repository.MyUserRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Iterator;

@RestController
@RequestMapping("v1")
@Slf4j
public class SimpleRestApiController {

    @Value("${spring.application.name:mydefault}")
    public String appName;

    @Autowired
    private MyUserRepository myUserRepository;

    @PostConstruct
    private void postConstruct() {
        MyUser admin = new MyUser("adminnnn", "admin@example.com");
        MyUser normalUser = new MyUser("userrrr", "user@example.com");
        myUserRepository.save(admin);
        myUserRepository.save(normalUser);
    }

    @GetMapping
    public ResponseEntity<String> greeting() {
        log.info("In greeting endpoint: {} application", appName);
        return new ResponseEntity<String>("Hello from Kimmy!! App name = " + appName, HttpStatus.OK);
    }

    @GetMapping("/users")
    public Iterator<MyUser> getAllUsers() {
        log.info("Get All Users...");
        return myUserRepository.findAll().iterator();
    }

    @PostMapping("/users/add")
    public MyUser addUser(@RequestParam("name") String name, @RequestParam("email") String email) {
        MyUser newUser = new MyUser(name, email);
        return myUserRepository.save(newUser);
    }
}
