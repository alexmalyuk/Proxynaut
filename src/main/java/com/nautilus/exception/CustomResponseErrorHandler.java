package com.nautilus.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.server.ResponseStatusException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Slf4j
public class CustomResponseErrorHandler extends DefaultResponseErrorHandler {

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        HttpStatus statusCode = (HttpStatus) response.getStatusCode();
        String statusText = response.getStatusText();
        String responseBody = new BufferedReader(
                new InputStreamReader(response.getBody(), StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));
        log.error("Response error: {} {}, body: {}", statusCode, statusText, responseBody);

        if (statusCode.equals(HttpStatusCode.valueOf(404))) {
            throw new ResponseStatusException(statusCode, responseBody);
        } else {
            throw new OtherResponseErrorException();
        }
    }

}
