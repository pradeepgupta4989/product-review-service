package com.productreview.repo.impl;

import com.productreview.model.ProductReview;
import com.productreview.repo.ProductReviewRepo;
import com.productreview.repo.ProductReviewRepository;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@ViewIndexed(designDoc="productreview", viewName = "all")
public class ProductReviewRepoImpl implements ProductReviewRepo {

	private final ProductReviewRepository productReviewRepository;

	public ProductReviewRepoImpl(ProductReviewRepository productReviewRepository) {
		this.productReviewRepository = productReviewRepository;
	}

	public Mono<ProductReview> createProductReview(ProductReview productReview) {
		return productReviewRepository.save(productReview);
	}

	public Flux<ProductReview> getAllProductReview() {
		return productReviewRepository.findAll();
	}

	public Mono<ProductReview> getProductReviewByProductId(String productId) {
		return productReviewRepository.findById(productId);
	}

	public Mono<ProductReview> deleteProductReviewByProductId(String productId) {
		return this.productReviewRepository.findById(productId)
				.flatMap(p -> this.productReviewRepository.deleteById(p.getProductID()).thenReturn(p));
	}

	public Mono<ProductReview> updateProductReviewByProductId(String productId, ProductReview productReview) {
		return this.productReviewRepository.existsById(productId).map(p -> productReview)
				.flatMap(this.productReviewRepository::save);

	}

}
