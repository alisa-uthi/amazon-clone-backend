package com.alisa.amazon.clone.backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class HttpMessageLogging {
    private static final Logger log = LoggerFactory.getLogger(HttpMessageLogging.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    private String clientTransactionId;
    private String serverTransactionId;
    private String serviceName;
    private String hostName;
    private String microserviceName;
    private Map<String, String> httpRequestHeaders;
    private String httpRequestMethod;
    private String httpRequestUri;
    private String httpRequestQuery;
    private String httpResponseContentType;
    private String requestData;
    private String responseData;
    private String xRequestId;
    private int statusCode;
    private long processingStartTime;
    private long processingFinishTime;

    public HttpMessageLogging() {
    }

    public String getClientTransactionId() {
        return clientTransactionId;
    }

    public void setClientTransactionId(String clientTransactionId) {
        this.clientTransactionId = clientTransactionId;
    }

    public String getServerTransactionId() {
        return serverTransactionId;
    }

    public void setServerTransactionId(String serverTransactionId) {
        this.serverTransactionId = serverTransactionId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getMicroserviceName() {
        return microserviceName;
    }

    public void setMicroserviceName(String microserviceName) {
        this.microserviceName = microserviceName;
    }

    public Map<String, String> getHttpRequestHeaders() {
        return httpRequestHeaders;
    }

    public void setHttpRequestHeaders(Map<String, String> httpRequestHeaders) {
        this.httpRequestHeaders = httpRequestHeaders;
    }

    public String getHttpRequestMethod() {
        return httpRequestMethod;
    }

    public void setHttpRequestMethod(String httpRequestMethod) {
        this.httpRequestMethod = httpRequestMethod;
    }

    public String getHttpRequestUri() {
        return httpRequestUri;
    }

    public void setHttpRequestUri(String httpRequestUri) {
        this.httpRequestUri = httpRequestUri;
    }

    public String getHttpRequestQuery() {
        return httpRequestQuery;
    }

    public void setHttpRequestQuery(String httpRequestQuery) {
        this.httpRequestQuery = httpRequestQuery;
    }

    public String getHttpResponseContentType() {
        return httpResponseContentType;
    }

    public void setHttpResponseContentType(String httpResponseContentType) {
        this.httpResponseContentType = httpResponseContentType;
    }

    public String getRequestData() {
        return requestData;
    }

    public void setRequestData(String requestData) {
        this.requestData = requestData;
    }

    public String getResponseData() {
        return responseData;
    }

    public void setResponseData(String responseData) {
        this.responseData = responseData;
    }

    @JsonProperty("x-request-id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public String getXRequestId() {
        return xRequestId;
    }

    public void setXRequestId(String xRequestId) {
        this.xRequestId = xRequestId;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd HH:mm:ss.SSS'Z'",
            timezone = "GMT"
    )
    public Date getProcessingStartTime() {
        return new Date(processingStartTime);
    }

    public void setProcessingStartTime(long processingStartTime) {
        this.processingStartTime = processingStartTime;
    }

    @JsonIgnore
    public long getProcessingFinishTime() {
        return processingFinishTime;
    }

    public void setProcessingFinishTime(long processingFinishTime) {
        this.processingFinishTime = processingFinishTime;
    }

    @JsonProperty("processingTime")
    public long getProcessingTime() {
        return this.processingFinishTime - this.processingStartTime;
    }

    public void processLog() {
        try {
            String httpMessageJsonString = mapper.writeValueAsString(this);
            log.info(httpMessageJsonString);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
    }
}