package com.lobox.challenge.lobxchallenge.configurations.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.List;

@RequiredArgsConstructor
@Configuration
public class SwaggerConfiguration {

    private final Environment environment;

    @Bean
    public OpenAPI devOpenApiServers() {
        Server localServer = new Server();
        localServer.setUrl(UriComponentsBuilder.newInstance()
                .host(environment.getProperty("server.address"))
                .port(environment.getProperty("server.port"))
                .path(environment.getRequiredProperty("server.servlet.context-path"))
                .toUriString());
        localServer.setDescription("Coming soon..");
        return new OpenAPI().servers(List.of(localServer));
    }
}
