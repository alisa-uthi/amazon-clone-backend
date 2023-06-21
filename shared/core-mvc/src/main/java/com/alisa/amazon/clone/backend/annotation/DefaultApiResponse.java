package com.alisa.amazon.clone.backend.annotation;

import com.alisa.amazon.clone.backend.constant.SwaggerConstant;
import com.alisa.amazon.clone.backend.model.ErrorList;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.alisa.amazon.clone.backend.constant.SwaggerConstant.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = SUCCESS_PHRASE, headers = @Header(name = X_REQUEST_ID_HEADER, description = X_REQUEST_ID_HEADER_DESC, schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "400", description = BAD_REQUEST_PHRASE, headers = @Header(name = X_REQUEST_ID_HEADER, description = X_REQUEST_ID_HEADER_DESC, schema = @Schema(type = "string")), content = @Content(schema = @Schema(implementation = ErrorList.class))),
        @ApiResponse(responseCode = "401", description = UNAUTHORIZED_REQUEST_PHRASE, headers = @Header(name = X_REQUEST_ID_HEADER, description = X_REQUEST_ID_HEADER_DESC, schema = @Schema(type = "string"))),
        @ApiResponse(responseCode = "403", description = FORBIDDEN_REQUEST_PHRASE, headers = @Header(name = X_REQUEST_ID_HEADER, description = X_REQUEST_ID_HEADER_DESC, schema = @Schema(type = "string"))),
        @ApiResponse(responseCode = "404", description = NOT_FOUND_REQUEST_PHRASE, headers = @Header(name = X_REQUEST_ID_HEADER, description = X_REQUEST_ID_HEADER_DESC, schema = @Schema(type = "string")), content = @Content(schema = @Schema(implementation = ErrorList.class))),
        @ApiResponse(responseCode = "422", description = UN_PROCESSABLE_REQUEST_PHRASE, headers = @Header(name = X_REQUEST_ID_HEADER, description = X_REQUEST_ID_HEADER_DESC, schema = @Schema(type = "string")), content = @Content(schema = @Schema(implementation = ErrorList.class))),
        @ApiResponse(responseCode = "500", description = INTERNAL_SERVER_REQUEST_PHRASE, headers = @Header(name = X_REQUEST_ID_HEADER, description = X_REQUEST_ID_HEADER_DESC, schema = @Schema(type = "string")), content = @Content(schema = @Schema(implementation = ErrorList.class))),
        @ApiResponse(responseCode = "502", description = BAD_GATEWAY_REQUEST_PHRASE, headers = @Header(name = X_REQUEST_ID_HEADER, description = X_REQUEST_ID_HEADER_DESC, schema = @Schema(type = "string")), content = @Content(schema = @Schema(implementation = ErrorList.class))),
        @ApiResponse(responseCode = "503", description = SERVICE_UNAVAILABLE_REQUEST_PHRASE, headers = @Header(name = X_REQUEST_ID_HEADER, description = X_REQUEST_ID_HEADER_DESC, schema = @Schema(type = "string")), content = @Content(schema = @Schema(implementation = ErrorList.class))),
        @ApiResponse(responseCode = "504", description = GATEWAY_TIMEOUT_REQUEST_PHRASE, headers = @Header(name = X_REQUEST_ID_HEADER, description = X_REQUEST_ID_HEADER_DESC, schema = @Schema(type = "string")), content = @Content(schema = @Schema(implementation = ErrorList.class))),
})
public @interface DefaultApiResponse {
}
