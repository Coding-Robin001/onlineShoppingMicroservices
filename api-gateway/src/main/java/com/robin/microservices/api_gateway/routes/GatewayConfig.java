package com.robin.microservices.api_gateway.routes;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;


import java.net.URI;


@Configuration
public class GatewayConfig {

    @Value("${gateway.routes.product}")
    private String productService;

    @Value("${gateway.routes.order}")
    private String orderService;

    @Value("${gateway.routes.inventory}")
    private String inventoryService;

    @Value("${swagger.routes.product}")
    private String productDocs;

    @Value("${swagger.routes.order}")
    private String orderDocs;

    @Value("${swagger.routes.inventory}")
    private String inventoryDocs;

        @Bean
        public RouterFunction<ServerResponse> apiRoutes() {
            return GatewayRouterFunctions.route("api_gateway")
                    .route(RequestPredicates.path("/api/product"),
                            HandlerFunctions.http(productService))
                    .filter(CircuitBreakerFilterFunctions.
                            circuitBreaker("productServiceCircuitBreaker", URI.
                                    create("forward:/fallbackRoute")))
                    .route(RequestPredicates.path("/api/order"),
                            HandlerFunctions.http(orderService))
                    .filter(CircuitBreakerFilterFunctions.
                            circuitBreaker("orderServiceCircuitBreaker", URI.
                                    create("forward:/fallbackRoute")))
                    .route(RequestPredicates.path("/api/inventory"),
                            HandlerFunctions.http(inventoryService))
                    .filter(CircuitBreakerFilterFunctions.
                            circuitBreaker("inventoryServiceCircuitBreaker", URI.
                                    create("forward:/fallbackRoute")))
                    .build();
        }

    @Bean
    public RouterFunction<ServerResponse> fallbackRoute() {
        return GatewayRouterFunctions.route("fallbackRoute")
                .GET("/fallbackRoute", request ->
                        ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE)
                                .body("Service currently not available, try again later!")).build();
    }

    @Bean
    public RouterFunction<ServerResponse> swaggerFallbackRoute() {
        return GatewayRouterFunctions.route("swaggerFallbackRoute")
                .GET("/swagger-fallback", request ->
                        ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE)
                                .body("API Documentation is currently unavailable. Please try again later."))
                .build();
    }


//    @Bean
//        public RouterFunction<ServerResponse> swaggerRoutes() {
//            return GatewayRouterFunctions.route("swagger_routes")
//                    .route(RequestPredicates.path("/api-docs/product"),
//                            HandlerFunctions.http(productDocs))
//                    .route(RequestPredicates.path("/api-docs/order"),
//                            HandlerFunctions.http(orderDocs))
//                    .route(RequestPredicates.path("/api-docs/inventory"),
//                            HandlerFunctions.http(inventoryDocs))
//                    .build();
//        }

    @Bean
    public RouterFunction<ServerResponse> swaggerRoutes() {
        return GatewayRouterFunctions.route("swagger_routes")
                .route(RequestPredicates.path("/api-docs/product"),
                        HandlerFunctions.http(productDocs))
                .filter(CircuitBreakerFilterFunctions.
                        circuitBreaker("swaggerProductCircuitBreaker",
                                URI.create("forward:/swagger-fallback")))

                .route(RequestPredicates.path("/api-docs/order"),
                        HandlerFunctions.http(orderDocs))
                .filter(CircuitBreakerFilterFunctions.
                        circuitBreaker("swaggerOrderCircuitBreaker",
                                URI.create("forward:/swagger-fallback")))

                .route(RequestPredicates.path("/api-docs/inventory"),
                        HandlerFunctions.http(inventoryDocs))
                .filter(CircuitBreakerFilterFunctions.
                        circuitBreaker("swaggerInventoryCircuitBreaker",
                                URI.create("forward:/swagger-fallback")))

                .build();
    }

}

