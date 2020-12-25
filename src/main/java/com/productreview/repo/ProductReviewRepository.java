package com.productreview.repo;

import com.productreview.model.ProductReview;
import org.springframework.data.couchbase.repository.ReactiveCouchbaseRepository;

public interface ProductReviewRepository extends ReactiveCouchbaseRepository<ProductReview, String>{

}
