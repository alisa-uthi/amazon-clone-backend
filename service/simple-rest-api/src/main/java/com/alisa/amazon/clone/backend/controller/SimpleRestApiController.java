package com.alisa.amazon.clone.backend.controller;

import com.alisa.amazon.clone.backend.entity.MyUser;
import com.alisa.amazon.clone.backend.repository.MyUserRepository;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    @Operation(summary = "Get all Users", description = "Returns a list of users", operationId = "getAllUsers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ok, successful operation",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = MyUser.class)))) ,
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found")})
    public Iterator<MyUser> getAllUsers() {
        log.info("Get All Users...");
        return myUserRepository.findAll().iterator();
    }

    @PostMapping("/users/add")
    @Operation(summary = "Add new User", description = "Returns a created users", operationId = "addNewUser")
    @Parameters(value = {
            @Parameter(in = ParameterIn.QUERY, name = "name", description = "Name of user", required = true, example = "John Doe", schema = @Schema(type = "string")),
            @Parameter(in = ParameterIn.QUERY, name = "email", description = "Email of user", required = true, example = "test@example.com", schema = @Schema(type = "string"))
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ok, successful operation",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = MyUser.class))) ,
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found")})
    public MyUser addUser(@RequestParam("name") String name,String email) {
        MyUser newUser = new MyUser(name, email);
        log.info("Add New User: {}", newUser);
        return myUserRepository.save(newUser);
    }
}
