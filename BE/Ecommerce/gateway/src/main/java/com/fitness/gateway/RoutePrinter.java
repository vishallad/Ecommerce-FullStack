package com.fitness.gateway;

import jakarta.annotation.PostConstruct;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoutePrinter {
    private final RouteLocator routeLocator;
    public RoutePrinter(RouteLocator routeLocator) {
        this.routeLocator = routeLocator;
    }

    @PostConstruct
    public void logRoutes() {
        routeLocator.getRoutes().subscribe(route -> {
            System.out.println("Route ID: " + route.getId() + " -> " + route.getUri());
            System.out.println("  Predicates: " + route.getPredicate());
            System.out.println("  Predicates: " + route.getMetadata());
            System.out.println("  Filters: " + route.getFilters());
        });
    }
}
