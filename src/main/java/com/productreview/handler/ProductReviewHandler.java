package com.productreview.handler;

import com.productreview.exception.DomainValidationException;
import com.productreview.exception.GeneralServiceException;
import com.productreview.exception.PathNotFoundException;
import com.productreview.model.ProductReview;
import com.productreview.service.ProductReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductReviewHandler {

	@Autowired
	private ProductReviewService productReviewService;
	private final ErrorHandler errorHandler;

	public Mono<ServerResponse> createProductReview(final ServerRequest request) {
		log.debug("Inside createProductReview method...");
		Validator validator = new ProductReviewRequestValidator();
		return request.bodyToMono(ProductReview.class)
				 .flatMap(body -> {
	                    Errors errors = new BeanPropertyBindingResult(
	                            body,
	                            ProductReview.class.getName());
	                    validator.validate(body, errors);

	                    if (errors == null || errors.getAllErrors().isEmpty()) {
	                        return   Mono.just(body);
	                    } else {
	                        throw new DomainValidationException(
	                                errors.getAllErrors().toString());
	                    }
	                })
				.flatMap(productReview -> productReviewService.createProductReview(productReview))
				.flatMap(
						productReview -> ServerResponse.created(URI.create("/productReview/create/" + productReview.getProductID()))
								.contentType(MediaType.APPLICATION_JSON).body(Mono.just(productReview), ProductReview.class))
		.switchIfEmpty(Mono.error(new GeneralServiceException(" Received empty response.")))
        .onErrorResume(errorHandler::throwableError); 
	}

	public Mono<ServerResponse> updateProductReviewByProductId(final ServerRequest request) {
		log.debug("Inside updateProductReviewByProductId method with Id: " + request.pathVariable("productID"));
		return request.bodyToMono(ProductReview.class)
				.flatMap(productReview -> productReviewService.updateProductReviewByProductId(request.pathVariable("productID"), productReview))
				.flatMap(productReview -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
						.body(Mono.just(productReview), ProductReview.class))
				//.switchIfEmpty(ServerResponse.notFound().build());
				.switchIfEmpty(Mono.error(new PathNotFoundException("ProductReview with ProductID not Found")))
		        .onErrorResume(errorHandler::throwableError); 
	}

	public Mono<ServerResponse> deleteProductReviewByProductId(final ServerRequest request) {
		log.debug("Inside deleteProductReviewByProductId method with Id: " + request.pathVariable("productID"));
		return productReviewService.deleteProductReviewByProductId(request.pathVariable("productID"))
				.flatMap(productReview -> ServerResponse.ok().body(Mono.just(productReview), ProductReview.class))
				.switchIfEmpty(Mono.error(new PathNotFoundException("ProductReview with ProductID not Found")))
		        .onErrorResume(errorHandler::throwableError); 
	}

	public Mono<ServerResponse> getProductReviewById(final ServerRequest request) {
		log.debug("Inside getProductReviewById method with id : " + request.pathVariable("productID"));
		return productReviewService.getProductReviewByProductId(request.pathVariable("productID"))
				.flatMap(productReview -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
						.body(Mono.just(productReview), ProductReview.class))
				//.switchIfEmpty(ServerResponse.notFound().build());
				.switchIfEmpty(Mono.error(new PathNotFoundException("ProductReview with ProductID not Found")))
		        .onErrorResume(errorHandler::throwableError); 
	}

	public Mono<ServerResponse> getAllProductReview(final ServerRequest request) {
		log.debug("Inside getAllProductReview method...");
		return productReviewService
				.getAllProductReview().collectList().flatMap(passengers -> ServerResponse.ok()
						.contentType(MediaType.APPLICATION_JSON).body(Mono.just(passengers), List.class))
				.onErrorResume(errorHandler::throwableError);
	}

}
