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

@DomainException(
    errorMessage = "The method specified in the Request-Line is not allowed for the resource identified by the Request-URI.",
    errorHttpStatus = HttpStatus.METHOD_NOT_ALLOWED,
    errorSequence = "001")
public class MethodNotAllowedException extends DomainBaseException {

    private static final long serialVersionUID = 1560957983115500932L;

    /**
     * Instantiates a new Domain base exception.
     *
     * @param errors the errors
     */
    public MethodNotAllowedException(final Collection<DomainError> errors) {
        super(errors);
    }

    /**
     * Instantiates a new Domain base exception.
     *
     * @param cause the cause
     */
    public MethodNotAllowedException(final String cause) {
        super(cause);
    }
}
