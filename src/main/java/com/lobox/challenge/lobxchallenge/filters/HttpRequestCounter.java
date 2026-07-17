package com.lobox.challenge.lobxchallenge.filters;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.LongAdder;

@Component
public class HttpRequestCounter {

    private final LongAdder requestCounter = new LongAdder();

    public void increment() {
        requestCounter.increment();
    }

    public long getCount() {
        return requestCounter.sum();
    }

}
