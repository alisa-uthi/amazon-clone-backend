package com.alisa.amazon.clone.backend.exception;

import com.alisa.amazon.clone.backend.model.ErrorDetail;
import com.alisa.amazon.clone.backend.model.ErrorList;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.BindException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class ApplicationExceptionResolver extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {Exception.class, BusinessException.class})
    @ResponseBody
    private ResponseEntity<Object> handleAllExceptions(Exception ex) {
        HttpStatus status = getStatusFromException(ex);
        List<ErrorDetail> errorList = new ArrayList<>();

        if (ex instanceof BusinessException bex) {
            errorList = bex.getErrorList().getErrors();
        } else {
            ErrorDetail errorDetail = new ErrorDetail(ErrorDetail.GENERAL_API_ERROR_CODE, ErrorDetail.GENERAL_API_ERROR_MESSAGE, ErrorDetail.GENERAL_API_ERROR_MORE_INFO);
            errorList.add(errorDetail);
        }
        ErrorList errorResponse = new ErrorList(errorList);
        return new ResponseEntity<>(errorResponse, status);
    }

    private HttpStatus getStatusFromException(Exception ex) {
        if (ex instanceof HttpRequestMethodNotSupportedException) {
            return HttpStatus.METHOD_NOT_ALLOWED;
        } else if (ex instanceof HttpMediaTypeNotSupportedException) {
            return HttpStatus.UNSUPPORTED_MEDIA_TYPE;
        } else if (ex instanceof HttpMediaTypeNotAcceptableException) {
            return HttpStatus.NOT_ACCEPTABLE;
        } else if (ex instanceof MissingPathVariableException) {
            return HttpStatus.BAD_REQUEST;
        } else if (ex instanceof MissingServletRequestParameterException) {
            return HttpStatus.BAD_REQUEST;
        } else if (ex instanceof ServletRequestBindingException) {
            return HttpStatus.BAD_REQUEST;
        } else if (ex instanceof ConversionNotSupportedException) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        } else if (ex instanceof TypeMismatchException) {
            return HttpStatus.BAD_REQUEST;
        } else if (ex instanceof HttpMessageNotReadableException) {
            return HttpStatus.BAD_REQUEST;
        } else if (ex instanceof HttpMessageNotWritableException) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        } else if (ex instanceof MethodArgumentNotValidException) {
            return HttpStatus.BAD_REQUEST;
        } else if (ex instanceof MissingServletRequestPartException) {
            return HttpStatus.BAD_REQUEST;
        } else if (ex instanceof BindException) {
            return HttpStatus.BAD_REQUEST;
        } else if (ex instanceof EnumConstantNotPresentException) {
            return HttpStatus.BAD_REQUEST;
        } else if (ex instanceof NotImplementedException) {
            return HttpStatus.NOT_IMPLEMENTED;
        } else if (ex instanceof NullPointerException) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        } else if (ex instanceof NoSuchElementException) {
            return HttpStatus.NOT_FOUND;
        } else if (ex instanceof BusinessException) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        } else {
            log.warn("Unknown exception type: {}", ex.getClass().getName());
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
