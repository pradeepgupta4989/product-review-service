package com.productreview.handler;


import com.productreview.model.ProductReview;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class ProductReviewRequestValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return ProductReview.class.isAssignableFrom(clazz);
    }
    @Override
    public void validate(Object target, Errors errors){
        ValidationUtils.rejectIfEmptyOrWhitespace(
                errors,"reviews","field.required");
        ProductReview request=(ProductReview)target;
        if(request.getReviews()==null){
            
            errors.rejectValue(
                    "reviews",
                    "field.required",
                    new Object[]{Integer.valueOf(1)},
                    "The Product Review should not be empty");
        }
    }
        }