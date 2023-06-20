package com.alisa.amazon.clone.backend.filter;

import com.alisa.amazon.clone.backend.model.CachedBodyHttpServletRequest;
import com.alisa.amazon.clone.backend.model.HttpMessageLogging;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@Order(value = Ordered.HIGHEST_PRECEDENCE)
@Component
@WebFilter(filterName = "ContentCachingFilter", urlPatterns = "/*")
public class ContentCachingFilter extends OncePerRequestFilter {
    @Value("${microservice-framework.core.request-response-log.max-payload-size:3000}")
    private Integer maxPayloadSize;
    @Value("${microservice-framework.core.request-response-log.log-payload:true}")
    private boolean isLogPayload;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        // Request
        CachedBodyHttpServletRequest cachedBodyHttpServletRequest = new CachedBodyHttpServletRequest(httpServletRequest);
        // Response
        ContentCachingResponseWrapper responseCacheWrapperObject = new ContentCachingResponseWrapper(httpServletResponse);

        try {
            filterChain.doFilter(cachedBodyHttpServletRequest, responseCacheWrapperObject);
        } finally {
            HttpMessageLogging httpMessageLogging = (HttpMessageLogging) httpServletRequest.getAttribute(HttpMessageLogging.class.getCanonicalName());
            if (httpMessageLogging != null) {
                if (this.isLogPayload) {
                    byte[] copy = responseCacheWrapperObject.getContentAsByteArray();
                    String responsePayload = this.getPayloadWithinMaxSize(new String(copy, httpServletResponse.getCharacterEncoding()));
                    httpMessageLogging.setResponseData(responsePayload);
                }
                // Because in Response, this function is triggered AFTER interceptor(unlike Request), I have to process log here
                httpMessageLogging.processLog();
            }
            responseCacheWrapperObject.copyBodyToResponse();
        }

        // Ref:
        // https://www.baeldung.com/spring-reading-httpservletrequest-multiple-times
        // https://stackoverflow.com/questions/63934694/how-to-get-response-body-from-servletresponse-in-spring-boot-filter
    }

    private String getPayloadWithinMaxSize(String payload) {
        return payload.length() <= this.maxPayloadSize ? payload : payload.substring(0, this.maxPayloadSize);
    }
}
