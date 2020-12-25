/*
 * Copyright (c) 2018 The Emirates Group. All Rights Reserved. The information specified here is confidential and remains property of the Emirates Group.
 * groupId     - com.emirates.ocsl
 * artifactId  - ocsl-dom
 * name        - ocsl-dom
 * description - OCSL Domain Object Model Project
 * 2019
 */
package com.productreview.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.http.HttpStatus;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public interface DomainDTO {

    @JsonIgnore
    default Optional<Collection<DomainError>> getValidationErrors() {
        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final javax.validation.Validator validator = factory.getValidator();
        return Optional.of(validator.validate(this))
            .filter(set -> set.size() > 0)
            .map(set -> set.stream()
                .map(violation -> DomainError.builder()
                    .errorMessage(violation.getPropertyPath().toString() + " " + violation.getMessage())
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .errorSequence("001")
                    .build())
                .collect(Collectors.toList())
            );
    }
}
