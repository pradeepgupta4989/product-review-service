package com.productreview.repo;

import com.productreview.model.ProductReview;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductReviewRepo {
        
    Mono<ProductReview> createProductReview(ProductReview productReview);
    Flux<ProductReview> getAllProductReview();
    Mono<ProductReview> getProductReviewByProductId(String productID);
    Mono<ProductReview> deleteProductReviewByProductId(String productID);
    Mono<ProductReview> updateProductReviewByProductId(String productID, ProductReview productReview);
}
