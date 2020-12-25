package com.productreview.exception;

import org.springframework.http.HttpStatus;

@DomainException(
	    errorMessage = "ProductReview ID not Found",
	    errorHttpStatus = HttpStatus.NOT_FOUND,
	    errorSequence = "404")
public class ProductReviewNotFoundException {

}
