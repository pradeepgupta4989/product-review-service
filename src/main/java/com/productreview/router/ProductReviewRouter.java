package com.productreview.router;

import com.productreview.handler.ProductReviewHandler;
import com.productreview.handler.ErrorHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.*;

import java.util.function.Consumer;

@Slf4j
@Configuration
public class ProductReviewRouter {

	@Bean
	public RouterFunction<ServerResponse> route(ProductReviewHandler productReviewHandler, ErrorHandler errorHandler) {
		
		final Consumer<ServerRequest> requestConsumer = serverRequest -> 
			log.info("Request path: {}, params: {}, variables: {}, headers: {}", 
					serverRequest.path(),
					serverRequest.queryParams().toString(), 
					serverRequest.pathVariables().toString(),
					serverRequest.headers().asHttpHeaders().entrySet().toString());
		
		return RouterFunctions
				.route(RequestPredicates.POST("/review").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
						productReviewHandler::createProductReview)
				.andRoute(RequestPredicates.GET("/review/{productID}").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
						productReviewHandler::getProductReviewById)
				.andRoute(
						RequestPredicates.PUT("/review/{productID}").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
						productReviewHandler::updateProductReviewByProductId)
				.andRoute(
						RequestPredicates.DELETE("/review/{productID}").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
						productReviewHandler::deleteProductReviewByProductId);
		 
	}
}
