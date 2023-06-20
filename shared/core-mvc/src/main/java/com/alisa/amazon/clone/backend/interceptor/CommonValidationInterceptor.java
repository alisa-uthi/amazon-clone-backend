package com.alisa.amazon.clone.backend.interceptor;

import com.alisa.amazon.clone.backend.exception.BusinessException;
import com.alisa.amazon.clone.backend.model.ErrorDetail;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.ArrayList;

@Component
public class CommonValidationInterceptor implements HandlerInterceptor {
    private static final Logger log = LoggerFactory.getLogger(CommonValidationInterceptor.class);
    @Value("${microservice-framework.core.validate-generic-headers:true}")
    private boolean isValidateGenericHeaders;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ArrayList<ErrorDetail> errorDetails = new ArrayList<>();
        if (this.isValidateGenericHeaders) {
            String xRequestId = request.getHeader("x-request-id");
            if (StringUtils.isBlank(xRequestId)) {
                errorDetails.add(new ErrorDetail("H001", "x-request-id HEADER IS REQUIRED", "x-request-id HTTP header is required."));
            } else if (xRequestId.length() != 36) {
                errorDetails.add(new ErrorDetail("H002", "x-request-id HEADER IS INVALID", "Length of x-request-id in HTTP header must be 36 digits."));
            }
        }
        if (errorDetails.size() > 0) {
            throw new BusinessException(errorDetails);
        } else {
            return true;
        }
    }
}