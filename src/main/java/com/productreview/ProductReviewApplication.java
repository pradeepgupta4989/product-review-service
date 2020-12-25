package com.productreview;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;
import reactor.netty.DisposableServer;
import reactor.netty.http.server.HttpServer;

@ComponentScan(basePackages = {"com.productreview"})
@EnableWebFlux
public class ProductReviewApplication {
    /*public static void main(String[] args) {
        SpringApplication.run(ProductReviewApplication.class,args);
    }*/
    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context =
                     new AnnotationConfigApplicationContext(ProductReviewApplication.class)) {
            context.getBean(DisposableServer.class).onDispose().block();
        }
    }

    @Bean
    public DisposableServer disposableServer(ApplicationContext context) {
        HttpHandler handler = WebHttpHandlerBuilder.applicationContext(context)
                .build();
        ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(handler);
        HttpServer httpServer = HttpServer.create().host("localhost").port(8083);
        return httpServer.handle(adapter).bindNow();
    }
}
