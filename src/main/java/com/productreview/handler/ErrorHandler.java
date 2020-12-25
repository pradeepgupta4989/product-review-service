/*
 * Copyright (c) 2018 The Emirates Group. All Rights Reserved. The information specified here is confidential and remains property of the Emirates Group.
 * groupId     - com.emirates.ocsl
 * artifactId  - profile-service
 * name        - profile-service
 * description - Profile Service
 * 2019
 */
package com.productreview.handler;

import com.productreview.exception.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.BiConsumer;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;

/**
 * The type Error handler.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ErrorHandler {

    private static final String FUNCTIONAL_ERROR_MESSAGE = "Error Occurred while calling service";

    private final ThrowableTranslator throwableTranslator;

    private final BiConsumer<Throwable, Throwable> stackTraceLogging = (Throwable stackTrace1, Throwable stackTrace2) ->
        log.error("{}{}", FUNCTIONAL_ERROR_MESSAGE, stackTrace1.getStackTrace());

    private <T extends Throwable> Mono<ServerResponse> getResponse(final Mono<T> monoError) {
        return monoError
            .flatMap(throwable -> throwableTranslator.apply(throwable)
                .flatMap(errorList -> ServerResponse
                    .status(errorList.stream()
                        .findFirst()
                        .map(DomainError::getHttpStatus)
                        .orElseGet(() -> DomainException.DEFAULT_HTTP_STATUS)
                    )
                    .body(fromObject(errorList))));
    }

    /**
     * Resource not found mono.404
     * @param request the request
     * @return the mono
     */
    public Mono<ServerResponse> notFound(final ServerRequest request) {
        return Mono.just(new PathNotFoundException(""))
            .transform(this::getResponse);
    }
    /**
     * Method not allowed mono.
     * @param request the request
     * @return the mono
     */
    public Mono<ServerResponse> methodNotAllowed(final ServerRequest request) {
        return Mono.just(new MethodNotAllowedException(""))
            .transform(this::getResponse);
    }
    /**
     * Throwable error mono.
     * @param error the error
     * @return the mono
     */
    Mono<ServerResponse> throwableError(final Throwable error) {
        return Mono.just(error).doAfterSuccessOrError(stackTraceLogging).transform(this::getResponse);
    }
    /**
     * Bad Request error.
     * @param request the request
     * @return the mono
     */
    public Mono<ServerResponse> badRequest(final ServerRequest request) {
    	return Mono.just(new DomainValidationException(""))
    			.transform(this::getResponse);
    }
}
