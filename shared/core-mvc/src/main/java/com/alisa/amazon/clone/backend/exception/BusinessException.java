package com.alisa.amazon.clone.backend.exception;

import com.alisa.amazon.clone.backend.model.ErrorDetail;
import com.alisa.amazon.clone.backend.model.ErrorList;

import java.util.ArrayList;
import java.util.List;

public class BusinessException extends RuntimeException {
    private final ErrorList errorList;

    public BusinessException(String code, String message, String description) {
        super("Business Exception occurred with code: " + code + ", message: " + message + ", description: " + description);
        ArrayList<ErrorDetail> errorDetails = new ArrayList<>();
        errorDetails.add(new ErrorDetail(code, message, description));
        this.errorList = new ErrorList(errorDetails);
    }

    public BusinessException(String code, String message, String description, String originalErrorCode, String originalErrorMessage) {
        super("Business Exception occurred with code: " + code + ", message: " + message + ", description: " + description + ", originalErrorCode: " + originalErrorCode + ", originalErrorMessage: " + originalErrorMessage);
        ArrayList<ErrorDetail> errorDetails = new ArrayList<>();
        errorDetails.add(new ErrorDetail(code, message, description, originalErrorCode, originalErrorMessage));
        this.errorList = new ErrorList(errorDetails);
    }

    public BusinessException(List<ErrorDetail> errorDetails) {
        this.errorList = new ErrorList(errorDetails);
    }

    public ErrorList getErrorList() {
        return errorList;
    }
}