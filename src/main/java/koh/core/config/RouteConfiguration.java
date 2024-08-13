package koh.core.config;

import koh.core.base.HttpMethod;
import koh.core.base.SimpleService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.List;

@Getter
@Builder
public class RouteConfiguration {
    @Singular
    final List<Route> routes;

    static class RouteException extends RuntimeException {
        RouteException(HttpMethod method, String path, String serviceClass) {
            super(String.format("Route [%s] at %s is duplicated: Class %s", method, path, serviceClass));
        }
    }

    @Getter
    @AllArgsConstructor
    public static class Route {
        final HttpMethod method;
        final String path;
        final Class<? extends SimpleService> serviceClass;
    }
}
