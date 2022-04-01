package com.snowkit.luna.util;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class RestTemplateUtil {

    private static final RestTemplate restTemplate;

    static {
        HttpClient httpClient = HttpClientBuilder.create()
                .setMaxConnTotal(50)
                .setMaxConnPerRoute(20)
                .build();

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setReadTimeout(20 * 1000);
        factory.setConnectTimeout(20 * 1000);
        factory.setHttpClient(httpClient);

        restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(factory);
        restTemplate.setErrorHandler(new RestTemplateErrorHandler());
    }

    protected RestTemplate getRestTemplate() {
        return restTemplate;
    }

    private static class RestTemplateErrorHandler implements ResponseErrorHandler {

        @Override
        public boolean hasError(ClientHttpResponse response) throws IOException {
            return response.getStatusCode().isError();
        }

        @Override
        public void handleError(ClientHttpResponse response) {
            // Nothing
        }
    }
}