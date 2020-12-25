/*
 * Copyright (c) 2018 The Emirates Group. All Rights Reserved. The information specified here is confidential and remains property of the Emirates Group.
 * groupId     - com.emirates.ocsl
 * artifactId  - ocsl-dom
 * name        - ocsl-dom
 * description - OCSL Domain Object Model Project
 * 2019
 */
package com.productreview.exception;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.core.codec.DecodingException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebInputException;

import com.couchbase.client.java.error.DocumentAlreadyExistsException;
import com.couchbase.client.java.error.DocumentDoesNotExistException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import lombok.Getter;

/**
 * The type Generic throwable translator.
 *
 * @author S750976
 */
@Getter
public class GenericThrowableTranslator {

    private static final String FUNCTIONAL_ERROR_MESSAGE = "Error Occurred while calling service.";

    /**
     * The Ocsl exception translation.
     */
    public Function<Throwable, Optional<Collection<DomainError>>> ocslExceptionTranslation = throwable ->
        Optional.ofNullable(throwable)
            .filter(test -> test instanceof DomainBaseException)
            .map(value -> (DomainBaseException) value)
            .map(DomainBaseException::getErrors);

    /**
     * The Generic exceptions extension.
     */
    public Function<Throwable, Optional<Collection<DomainError>>> genericExceptionsExtension = throwable -> {

        final Collection<DomainError> domainErrors = new ArrayList<>();

        if (throwable instanceof DecodingException
            || throwable instanceof InvalidFormatException
            || throwable instanceof ServerWebInputException) {
            domainErrors.add(DomainError.builder()
                .errorMessage("Request provided is not valid. " + throwable.getMessage())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .errorSequence("001")
                .build());
        }
        else if (throwable instanceof IllegalArgumentException) {
            domainErrors.add(DomainError.builder()
                .errorMessage(throwable.getMessage())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .errorSequence("001")
                .build());
        }
        else if (throwable instanceof DocumentDoesNotExistException) {
            domainErrors.add(DomainError.builder()
                .errorMessage("Requested resource cannot be found.")
                .httpStatus(HttpStatus.BAD_REQUEST)
                .errorSequence("002")
                .build());
        }
        else if (throwable instanceof OptimisticLockingFailureException) {
            domainErrors.add(DomainError.builder()
                .errorMessage(throwable.getMessage())
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .errorSequence("001")
                .build());
        }
        else if (throwable instanceof DocumentAlreadyExistsException) {
            domainErrors.add(DomainError.builder()
                .errorMessage("Resource already exist.")
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .errorSequence("003")
                .build());
        }
        return Optional.ofNullable(domainErrors).filter(errors -> !errors.isEmpty());
    };

    /**
     * The Fallback translation.
     */
    public Function<Throwable, Collection<DomainError>> fallbackTranslation = throwable -> Collections.singletonList(DomainError.builder()
        .errorMessage(throwable.getMessage())
        .httpStatus(DomainException.DEFAULT_HTTP_STATUS)
        .errorSequence(DomainException.DEFAULT_SEQUENCE)
        .build());

    /**
     * The Generic translation.
     */
    public Function<Throwable, Collection<DomainError>> genericTranslation = throwable ->
        ocslExceptionTranslation.apply(throwable)
            .orElseGet(() -> genericExceptionsExtension.apply(throwable)
                .orElseGet(() -> fallbackTranslation.apply(throwable)));

}
