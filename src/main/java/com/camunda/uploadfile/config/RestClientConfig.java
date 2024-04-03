package com.camunda.uploadfile.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Configuration
public class RestClientConfig {

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        BufferingClientHttpRequestFactory bufferingClientHttpRequestFactory = new BufferingClientHttpRequestFactory(
                requestFactory);
        restTemplate.setRequestFactory(bufferingClientHttpRequestFactory);

        // Now, add the interceptor
        List<String> interceptors = Collections.singletonList("");
        restTemplate.setInterceptors(Collections.singletonList((ClientHttpRequestInterceptor) interceptors));
        return restTemplate;
    }
}


