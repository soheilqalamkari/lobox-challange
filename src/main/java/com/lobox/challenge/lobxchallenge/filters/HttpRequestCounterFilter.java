package com.lobox.challenge.lobxchallenge.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Component
public class HttpRequestCounterFilter extends OncePerRequestFilter {

    private final HttpRequestCounter httpRequestCounter;
    private static final String[] EXCLUDED_PATHS = {
           "/swagger-ui.html",
            "/swagger-ui/",
            "/v3/api-docs",
            "/v3/api-docs.yml"
    };

    @Override
    protected void doFilterInternal(HttpServletRequest request , HttpServletResponse response , FilterChain filterChain) throws ServletException, IOException {
        httpRequestCounter.increment();
        filterChain.doFilter(request,response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI()
                .substring(request.getContextPath().length());
        return Stream.of(EXCLUDED_PATHS)
                .anyMatch(str-> path.startsWith(str));
    }

    @Override
    protected boolean shouldNotFilterAsyncDispatch() {
       return true;
    }

    @Override
    protected boolean shouldNotFilterErrorDispatch() {
        return true;
    }
}
