package com.nautilus.controller;

import com.nautilus.dto.ApproveRequestDto;
import com.nautilus.exception.CustomResponseErrorHandler;
import com.nautilus.validate.ApproveRequestValidator;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/edo/task")
@RequiredArgsConstructor
@Slf4j
public class TaskController {

    @Value("${external.endpoint.url}")
    private String externalEndpointUrl;

    @Value("${external.endpoint.authorization.header}")
    private String authorizationHeader;

    private static final String CALL_ENDPOINT = "call endpoint : %s ";
    private final RestTemplateBuilder restTemplateBuilder;
    private RestTemplate restTemplate;
    private final ApproveRequestValidator approveRequestValidator;

    @PostConstruct
    public void init() {
        this.restTemplate = restTemplateBuilder
                .errorHandler(new CustomResponseErrorHandler())
                .build();
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<String> getTaskPage(
            @RequestHeader Map<String, String> headers,
            @PathVariable @Validated UUID taskId,
            HttpServletRequest request) {

        log.info(String.format(CALL_ENDPOINT, "/edo/task/" + taskId));

        HttpHeaders newHeaders = new HttpHeaders();
        newHeaders.setAll(headers);
        newHeaders.set("Authorization", authorizationHeader);
        HttpEntity<String> entity = new HttpEntity<>(newHeaders);
        String urlWithParams = externalEndpointUrl + "/task/" + taskId;

        ResponseEntity<String> response = restTemplate.exchange(
                urlWithParams,
                HttpMethod.valueOf(request.getMethod()),
                entity,
                String.class);

        return ResponseEntity.status(response.getStatusCode())
                .headers(response.getHeaders())
                .body(response.getBody());
    }

    @PostMapping("/approve")
    public ResponseEntity<String> approveTask(
            @RequestHeader Map<String, String> headers,
            @RequestBody @Validated ApproveRequestDto requestBody,
            HttpServletRequest request) {

        log.info(String.format(CALL_ENDPOINT, "/task/approve - " + requestBody));

        approveRequestValidator.validate(requestBody);


        HttpHeaders newHeaders = new HttpHeaders();
        newHeaders.setAll(headers);
        newHeaders.set("Authorization", authorizationHeader);
        HttpEntity<ApproveRequestDto> entity = new HttpEntity<>(requestBody, newHeaders);
        String urlWithParams = externalEndpointUrl + "/task/approve";

        ResponseEntity<String> response = restTemplate.exchange(
                urlWithParams,
                HttpMethod.valueOf(request.getMethod()),
                entity,
                String.class);

        return ResponseEntity.status(response.getStatusCode())
                .headers(response.getHeaders())
                .body(response.getBody());
    }

    @GetMapping("/thanks")
    public ResponseEntity<String> getThanksPage(
            @RequestHeader Map<String, String> headers,
            HttpServletRequest request) {

        log.info(String.format(CALL_ENDPOINT, "/edo/task/thanks"));

        HttpHeaders newHeaders = new HttpHeaders();
        newHeaders.setAll(headers);
        newHeaders.set("Authorization", authorizationHeader);
        HttpEntity<String> entity = new HttpEntity<>(newHeaders);
        String urlWithParams = externalEndpointUrl + "/task/thanks";

        ResponseEntity<String> response = restTemplate.exchange(
                urlWithParams,
                HttpMethod.valueOf(request.getMethod()),
                entity,
                String.class);

        return ResponseEntity.status(response.getStatusCode())
                .headers(response.getHeaders())
                .body(response.getBody());
    }

}