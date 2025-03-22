package com.robin.microservices.api_gateway.routes;

import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.HandlerFunction;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class Route {

        @Bean
        public RouterFunction<ServerResponse> apiRoutes() {
            return GatewayRouterFunctions.route("api_gateway")
                    .route(RequestPredicates.path("/api/product"),
                            HandlerFunctions.http("http://localhost:8080"))
                    .route(RequestPredicates.path("/api/order"),
                            HandlerFunctions.http("http://localhost:8081"))
                    .route(RequestPredicates.path("/api/inventory"),
                            HandlerFunctions.http("http://localhost:8082"))
                    .build();
        }

        @Bean
        public RouterFunction<ServerResponse> swaggerRoutes() {
            return GatewayRouterFunctions.route("swagger_routes")
                    .route(RequestPredicates.path("/api-docs/product"),
                            HandlerFunctions.http("http://localhost:8080/v3/api-docs"))
                    .route(RequestPredicates.path("/api-docs/order"),
                            HandlerFunctions.http("http://localhost:8081/v3/api-docs"))
                    .route(RequestPredicates.path("/api-docs/inventory"),
                            HandlerFunctions.http("http://localhost:8082/v3/api-docs"))
                    .build();
        }
    }

