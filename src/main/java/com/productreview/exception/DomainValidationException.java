/*
 * Copyright (c) 2018 The Emirates Group. All Rights Reserved. The information specified here is confidential and remains property of the Emirates Group.
 * groupId     - com.emirates.ocsl
 * artifactId  - ocsl-dom
 * name        - ocsl-dom
 * description - OCSL Domain Object Model Project
 * 2019
 */
package com.productreview.exception;

import java.util.Collection;

import org.springframework.http.HttpStatus;

/**
 * The type Validation exception.
 */
@DomainException(
    errorMessage = "Request provided is not valid.",
    errorHttpStatus = HttpStatus.BAD_REQUEST,
    errorSequence = "001")
public class DomainValidationException extends DomainBaseException {

    private static final long serialVersionUID = -910165772138345485L;

    /**
     * Instantiates a new Domain base exception.
     *
     * @param errors the errors
     */
    public DomainValidationException(final Collection<DomainError> errors) {
        super(errors);
    }

    /**
     * Instantiates a new Domain base exception.
     *
     * @param cause the cause
     */
    public DomainValidationException(final String cause) {
        super(cause);
    }

}
