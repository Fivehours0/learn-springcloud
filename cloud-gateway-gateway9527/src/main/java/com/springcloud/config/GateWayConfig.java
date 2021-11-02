package com.springcloud.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GateWayConfig {
    // 当访问http://localhost:9527/guonei的时候会转发至http://news.baidu.com/guonei
    @Bean
    public RouteLocator customRouteLocater(RouteLocatorBuilder routeLocatorBuilder) {
        // 对应yml里的routes
        RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();
        // 访问的网站是 http://news.baidu.com/guonei
        routes.route("path_routh3", r ->
            r.path("/guonei").uri("http://news.baidu.com/guonei")).build();
        return routes.build();
    }
}
