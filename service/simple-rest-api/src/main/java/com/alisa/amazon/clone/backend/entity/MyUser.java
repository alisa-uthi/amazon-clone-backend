package com.alisa.amazon.clone.backend.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Entity
@NoArgsConstructor
@Schema(name="My User", description = "My user description")
public class MyUser {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Schema(name = "name", description = "name of user", requiredMode = REQUIRED, example = "John Doe")
    private String name;

    @Schema(name = "email", description = "email of user", requiredMode = REQUIRED, example = "test@example.com")
    private String email;

    public MyUser(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}