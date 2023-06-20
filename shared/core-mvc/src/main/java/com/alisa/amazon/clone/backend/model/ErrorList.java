package com.alisa.amazon.clone.backend.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.List;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Schema(name = "ErrorList", description = "Error List")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorList implements Serializable {
    private List<ErrorDetail> errors;

    public ErrorList() {
    }

    public ErrorList(List<ErrorDetail> errors) {
        this.errors = errors;
    }

    @JsonProperty(value = "errors", required = true)
    @Schema(
            requiredMode = REQUIRED,
            type = "List<ErrorDetail>",
            description = "List of errors"
    )
    public List<ErrorDetail> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorDetail> errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
        return "ErrorList{" +
                "errors=" + errors +
                '}';
    }
}
