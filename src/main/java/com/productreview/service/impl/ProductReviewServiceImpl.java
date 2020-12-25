package com.productreview.service.impl;

import com.productreview.model.ProductReview;
import com.productreview.repo.ProductReviewRepo;
import com.productreview.service.ProductReviewService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class ProductReviewServiceImpl implements ProductReviewService {

    @Autowired
	ProductReviewRepo productReviewRepo;

	@PreAuthorize("hasRole('ADMIN')")
	@Override
	public Mono<ProductReview> createProductReview(ProductReview productReview) {
		log.debug("Inside createProductReview service method...");
		return productReviewRepo.createProductReview(productReview);
	}
	@PreAuthorize("hasRole('ADMIN')")
	@Override
	public Flux<ProductReview> getAllProductReview() {
		log.debug("Inside getAllProductReview service method...");
		return productReviewRepo.getAllProductReview();
	}
	@PreAuthorize("hasRole('ADMIN')")
	@Override
	public Mono<ProductReview> getProductReviewByProductId(String productID) {
		log.debug("Inside getProductReviewById service method...");
		return productReviewRepo.getProductReviewByProductId(productID);
	}
	@PreAuthorize("hasRole('ADMIN')")
	@Override
	public Mono<ProductReview> deleteProductReviewByProductId(String productID) {
		log.debug("Inside deleteProductReviewByProductId service method...");
		return productReviewRepo.deleteProductReviewByProductId(productID);
	}
	@PreAuthorize("hasRole('ADMIN')")
	@Override
	public Mono<ProductReview> updateProductReviewByProductId(String productID, ProductReview productReview) {
		log.debug("Inside updateProductReviewByProductId service method...");
		return productReviewRepo.updateProductReviewByProductId(productID, productReview);
	}

}
