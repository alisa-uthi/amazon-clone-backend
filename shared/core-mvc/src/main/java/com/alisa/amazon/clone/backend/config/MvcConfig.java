package com.alisa.amazon.clone.backend.config;

import com.alisa.amazon.clone.backend.interceptor.CommonValidationInterceptor;
import com.alisa.amazon.clone.backend.interceptor.HttpMessageLoggingInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableConfigurationProperties
@Slf4j
public class MvcConfig implements WebMvcConfigurer {
    @Autowired
    CommonValidationInterceptor commonValidationInterceptor;
    @Autowired
    HttpMessageLoggingInterceptor httpMessageLoggingInterceptor;

    public MvcConfig() {
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(commonValidationInterceptor);
        registry.addInterceptor(httpMessageLoggingInterceptor);
//        registry.addInterceptor(new UserInterceptor());          // See example in https://github.com/eugenp/tutorials/blob/master/spring-security-modules/spring-security-web-mvc-custom/src/main/java/com/baeldung/web/interceptor/UserInterceptor.java
//        registry.addInterceptor(new SessionTimerInterceptor());  // See example in https://github.com/eugenp/tutorials/blob/master/spring-security-modules/spring-security-web-mvc-custom/src/main/java/com/baeldung/web/interceptor/SessionTimerInterceptor.java
    }
}
