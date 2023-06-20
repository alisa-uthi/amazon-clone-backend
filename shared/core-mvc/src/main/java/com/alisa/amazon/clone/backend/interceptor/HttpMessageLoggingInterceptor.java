package com.alisa.amazon.clone.backend.interceptor;

import com.alisa.amazon.clone.backend.model.HttpMessageLogging;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.net.InetAddress;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class HttpMessageLoggingInterceptor implements HandlerInterceptor {
    private static final Logger log = LoggerFactory.getLogger(HttpMessageLoggingInterceptor.class);

    @Value("${microservice-framework.core.request-response-log.log-payload:true}")
    private boolean isLogPayload;
    @Value("${microservice-framework.core.request-response-log.log-req-headers:true}")
    private boolean isLogRequestHeaders;
    @Value("${microservice-framework.core.request-response-log.max-payload-size:3000}")
    private Integer maxPayloadSize;

    @Value("${spring.application.name}")
    private String applicationName;

    public HttpMessageLoggingInterceptor() {
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpMessageLogging httpMessageLogging = new HttpMessageLogging();
        httpMessageLogging.setServiceName(this.getServiceNameFromHttpServletRequest(request));

        String hostName = InetAddress.getLocalHost().getHostName();
        httpMessageLogging.setHostName(hostName);

        httpMessageLogging.setMicroserviceName(applicationName);

        if (isLogRequestHeaders) {
            httpMessageLogging.setHttpRequestHeaders(this.getHttpRequestHeaderFromHttpServletRequest(request));
        } else {
            log.info("Request headers logging is disabled as per application properties (microservice-framework.core.request-response-log.log-req-headers)");
        }

        httpMessageLogging.setHttpRequestMethod(request.getMethod().toUpperCase());
        httpMessageLogging.setHttpRequestQuery(request.getQueryString());
        httpMessageLogging.setHttpRequestUri(request.getRequestURI());
        httpMessageLogging.setClientTransactionId(request.getHeader("x-client-transaction-id"));
        httpMessageLogging.setServerTransactionId(request.getHeader("x-server-transaction-id"));
        httpMessageLogging.setProcessingStartTime(System.currentTimeMillis());

        // If client doesn't send x-request-id, generate a new one
        if (StringUtils.isBlank(request.getHeader("x-request-id"))) {
            UUID uuid = UUID.randomUUID();
            response.setHeader("x-request-id", uuid.toString());    // echo x-request-id back in response
            httpMessageLogging.setXRequestId(uuid.toString());
        } else {
            response.setHeader("x-request-id", request.getHeader("x-request-id"));
            httpMessageLogging.setXRequestId(request.getHeader("x-request-id"));
        }

        if (this.isLogPayload) {
            byte[] body = StreamUtils.copyToByteArray(request.getInputStream());
            String requestPayload = this.getPayloadWithinMaxSize(new String(body));
            httpMessageLogging.setRequestData(requestPayload);
        } else {
            log.info("Request and response payload logging is disabled as per application properties (microservice-framework.core.request-response-log.log-payload)");
        }

        request.setAttribute(HttpMessageLogging.class.getCanonicalName(), httpMessageLogging);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HttpMessageLogging httpMessageLogging = (HttpMessageLogging) request.getAttribute(HttpMessageLogging.class.getCanonicalName());
        if (httpMessageLogging != null) {
            httpMessageLogging.setStatusCode(response.getStatus());
            httpMessageLogging.setProcessingFinishTime(System.currentTimeMillis());
            String contentType = response.getContentType();
            httpMessageLogging.setHttpResponseContentType(contentType);
        }
    }

    public String getServiceNameFromHttpServletRequest(HttpServletRequest request) {
        String method = request.getMethod().toUpperCase();
        String url = request.getRequestURI();
        return method + " " + url;
    }

    private String getPayloadWithinMaxSize(String payload) {
        return payload.length() <= this.maxPayloadSize ? payload : payload.substring(0, this.maxPayloadSize);
    }

    public Map<String, String> getHttpRequestHeaderFromHttpServletRequest(HttpServletRequest request) {
        Map<String, String> headerMap = null;
        Enumeration<String> headerNames = request.getHeaderNames();

        String headerName;
        String headerValue;
        if (headerNames != null) {
            headerMap = new LinkedHashMap<>();
            while (headerNames.hasMoreElements()) {
                headerName = headerNames.nextElement();
                if (headerName.equalsIgnoreCase("Authorization")) {
                    headerValue = "...masked...";
                } else {
                    headerValue = request.getHeader(headerName);
                }
                headerMap.put(headerName, headerValue);
            }
        }
        return headerMap;
    }
}
