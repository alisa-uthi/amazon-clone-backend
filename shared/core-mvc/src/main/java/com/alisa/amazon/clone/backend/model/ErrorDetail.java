package com.alisa.amazon.clone.backend.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.MDC;

import java.io.Serializable;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Schema(name = "ErrorDetail", description = "Error Detail")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorDetail implements Serializable {
    public static final String REST_REQUEST_ERROR_CODE = "E0001";
    public static final String REST_REQUEST_ERROR_MESSAGE = "INVALID REQUEST";
    public static final String REST_REQUEST_ERROR_MORE_INFO = "Check request header(s)/query parameter(s)/path parameter/request body for invalid input.";

    public static final String REQUEST_BODY_ERROR_CODE = "E0002";
    public static final String REQUEST_BODY_ERROR_MESSAGE = "REQUEST BODY IS INVALID";
    public static final String REQUEST_BODY_ERROR_MORE_INFO = "Check request body for invalid input.";

    public static final String INVALID_REQUEST_ID_ERROR_CODE = "E0003";
    public static final String INVALID_REQUEST_ID_ERROR_MESSAGE = "x-request-id HEADER IS INVALID";
    public static final String INVALID_REQUEST_ID_ERROR_MORE_INFO = "Length of x-request-id HTTP header must be 30 characters.";

    public static final String MISSING_REQUEST_ID_ERROR_CODE = "E0004";
    public static final String MISSING_REQUEST_ID_ERROR_MESSAGE = "x-request-id HEADER IS REQUIRED";
    public static final String MISSING_REQUEST_ID_ERROR_MORE_INFO = "x-request-id HTTP header is  required.";

    public static final String MAX_UPLOAD_SIZE_EXCEEDED_ERROR_CODE = "E0005";
    public static final String MAX_UPLOAD_SIZE_EXCEEDED_ERROR_MESSAGE = "MAXIMUM UPLOAD SIZE EXCEEDED";
    public static final String MAX_UPLOAD_SIZE_EXCEEDED_ERROR_MORE_INFO = "Upload size exceeds maximum size of %d bytes.";

    public static final String GENERAL_API_ERROR_CODE = "F9999";
    public static final String GENERAL_API_ERROR_MESSAGE = "GENERAL ERROR";
    public static final String GENERAL_API_ERROR_MORE_INFO = "Please contact the application's support team for more information.";

    private String code;
    private String message;

    private String description;
    private String originalErrorCode;
    private String originalErrorMessage;
    private String traceId;

    public ErrorDetail(String code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
        this.traceId = this.getTraceIdData();
    }

    public ErrorDetail(String code, String message, String description, String originalErrorCode, String originalErrorMessage) {
        this.code = code;
        this.message = message;
        this.description = description;
        this.originalErrorCode = originalErrorCode;
        this.originalErrorMessage = originalErrorMessage;
        this.traceId = this.getTraceIdData();
    }

    @JsonProperty(value = "code", required = true)
    @Schema(
            requiredMode = REQUIRED,
            type = "String",
            example = GENERAL_API_ERROR_CODE,
            description = "Error Code"
    )
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @JsonProperty(value = "message", required = true)
    @Schema(
            requiredMode = REQUIRED,
            type = "String",
            example = GENERAL_API_ERROR_MESSAGE,
            description = "Error Message"
    )
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @JsonProperty(value = "description", required = true)
    @Schema(
            requiredMode = REQUIRED,
            type = "String",
            example = GENERAL_API_ERROR_MORE_INFO,
            description = "Error Description"
    )
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty(value = "traceId", required = true)
    @Schema(
            requiredMode = REQUIRED,
            type = "String",
            example = "asd983kfd8934jkhgd0dksd93k0wl334",
            description = "Trace ID that can be  used for traceability."
    )
    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    @JsonProperty(value = "originalErrorCode", required = false)
    @Schema(
            requiredMode = NOT_REQUIRED,
            type = "String",
            example = "E10001",
            description = "Original Error Code from backend"
    )
    public String getOriginalErrorCode() {
        return originalErrorCode;
    }

    public void setOriginalErrorCode(String originalErrorCode) {
        this.originalErrorCode = originalErrorCode;
    }

    @JsonProperty(value = "originalErrorMessage", required = false)
    @Schema(
            requiredMode = NOT_REQUIRED,
            type = "String",
            example = "ORIGINAL ERROR: E1234 ORIGINAL ERROR MESSAGE",
            description = "More Information on the problem and solution. Or, the error message from backend."
    )
    public String getOriginalErrorMessage() {
        return originalErrorMessage;
    }

    public void setOriginalErrorMessage(String originalErrorMessage) {
        this.originalErrorMessage = originalErrorMessage;
    }

    @Override
    public String toString() {
        return "ErrorDetail{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", description='" + description + '\'' +
                ", originalErrorCode='" + originalErrorCode + '\'' +
                ", originalErrorMessage='" + originalErrorMessage + '\'' +
                ", traceId='" + traceId + '\'' +
                '}';
    }

    private String getTraceIdData() {
        return MDC.get("traceId");
    }
}