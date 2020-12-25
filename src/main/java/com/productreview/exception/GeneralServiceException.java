/*
 * Copyright (c) 2018 The Emirates Group. All Rights Reserved. The information specified here is confidential and remains property of the Emirates Group.
 * groupId     - com.emirates.ocsl
 * artifactId  - ocsl-dom
 * name        - ocsl-dom
 * description - OCSL Domain Object Model Project
 * 2019
 */
package com.productreview.exception;

import org.springframework.http.HttpStatus;

import java.util.Collection;

/**
 * The type General service exception.
 */
@DomainException(
    errorMessage = "Unhandled exception has occurred. Please contact Support Team",
    errorHttpStatus = HttpStatus.SERVICE_UNAVAILABLE,
    errorSequence = "001")
public class GeneralServiceException extends DomainBaseException {

    private static final long serialVersionUID = -6210966549387641412L;

    public GeneralServiceException(final Collection<DomainError> errors) {
        super(errors);
    }

    public GeneralServiceException(final String cause) {
        super(cause);
    }
}
