package com.alisa.amazon.clone.backend.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SwaggerConstant {
    public static final String SUCCESS_PHRASE = "Success";
    public static final String CREATED_PHRASE = "Created";
    public static final String JWT_TOKEN_PHRASE = "jwtToken";
    public static final String REQUEST_BODY_PHRASE = "Request body";
    public static final String BAD_REQUEST_PHRASE = "Bad Request";
    public static final String UNAUTHORIZED_REQUEST_PHRASE = "Unauthorized";
    public static final String FORBIDDEN_REQUEST_PHRASE = "Forbidden";
    public static final String NOT_FOUND_REQUEST_PHRASE = "Not Found";
    public static final String UN_PROCESSABLE_REQUEST_PHRASE = "Unprocessable Entity (Business Error)";
    public static final String INTERNAL_SERVER_REQUEST_PHRASE = "Internal Server error occurred";
    public static final String BAD_GATEWAY_REQUEST_PHRASE = "Bad Gateway";
    public static final String SERVICE_UNAVAILABLE_REQUEST_PHRASE = "Service Unavailable";
    public static final String GATEWAY_TIMEOUT_REQUEST_PHRASE = "Gateway Timeout";
    public static final String X_REQUEST_ID_HEADER = "x-request-id";
    public static final String X_REQUEST_ID_HEADER_DESC = "36 characters unique ID used for traceability purpose. If provided on a request, the same value is echoed back. If not provided on a request, API generates and returns UUID.";
    public static final String X_REQUEST_ID_HEADER_EXAMPLE = "sdfs3lkf-d7fs-3sdf-vlbk-32k3fi99r56k";
}
