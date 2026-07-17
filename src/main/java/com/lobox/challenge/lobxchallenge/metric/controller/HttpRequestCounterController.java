package com.lobox.challenge.lobxchallenge.metric.controller;

import com.lobox.challenge.lobxchallenge.filters.HttpRequestCounter;
import com.lobox.challenge.lobxchallenge.metric.dto.HttpRequestCountResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/metrics")
public class HttpRequestCounterController {

    private final HttpRequestCounter httpRequestCounter;

    @GetMapping("/v1.0/requests")
    public ResponseEntity<HttpRequestCountResponse> getCountOfRequest() {
        return new ResponseEntity<>(new HttpRequestCountResponse(httpRequestCounter.getCount()), HttpStatus.OK);
    }
}
