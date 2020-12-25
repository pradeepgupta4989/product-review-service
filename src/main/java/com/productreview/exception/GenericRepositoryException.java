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
 * <p>
 * GenericRepositoryException.
 * </p>
 *
 * @author Markus Antonius
 */
@DomainException(
    errorMessage = "Requested Service is not responding. Please contact support team",
    errorHttpStatus = HttpStatus.SERVICE_UNAVAILABLE,
    errorSequence = "001")
public class GenericRepositoryException extends DomainBaseException {

    static final long serialVersionUID = 1L;

    /**
     * Instantiates a new Domain base exception.
     *
     * @param errors the errors
     */
    public GenericRepositoryException(final Collection<DomainError> errors) {
        super(errors);
    }

    /**
     * Instantiates a new Domain base exception.
     *
     * @param cause the cause
     */
    public GenericRepositoryException(final String cause) {
        super(cause);
    }
}
