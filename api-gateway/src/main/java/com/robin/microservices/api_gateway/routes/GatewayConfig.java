package com.robin.microservices.api_gateway.routes;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class GatewayConfig {

    @Value("${gateway.routes.product}")
    private String productService;

    @Value("${gateway.routes.order}")
    private String orderService;

    @Value("${gateway.routes.inventory}")
    private String inventoryService;

        @Bean
        public RouterFunction<ServerResponse> apiRoutes() {
            return GatewayRouterFunctions.route("api_gateway")
                    .route(RequestPredicates.path("/api/product"),
                            HandlerFunctions.http(productService))
                    .route(RequestPredicates.path("/api/order"),
                            HandlerFunctions.http(orderService))
                    .route(RequestPredicates.path("/api/inventory"),
                            HandlerFunctions.http(inventoryService))
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

