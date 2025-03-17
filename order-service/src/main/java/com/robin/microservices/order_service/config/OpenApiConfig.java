package com.robin.microservices.order_service.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI orderServiceApi(){
        return new OpenAPI()
                .info(new Info().title("Order Service API")
                        .description("Rest API for order service, a component of onlineShoppingMicroservices")
                        .version("v0.0.1")
                        .license(new License().name("apache 2.0")))
                .externalDocs(new ExternalDocumentation().description("more on order service on this link")
                        .url("https://order-service-dummy-url/docs"));
    }
}
