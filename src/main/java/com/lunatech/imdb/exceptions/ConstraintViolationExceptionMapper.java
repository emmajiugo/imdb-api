package com.lunatech.imdb.exceptions;

import com.lunatech.imdb.bean.ResponseBuilder;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.stream.Collectors;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {

        final String message = exception.getConstraintViolations().stream()
                .map(cv -> extractPropertyName(cv.getPropertyPath().toString()) + " : " + cv.getMessage())
                .collect(Collectors.joining(", "));

        return ResponseBuilder.build(null, false, message, Response.Status.BAD_REQUEST, this.getClass());
    }

    private String extractPropertyName(String path) {
        return path.substring(path.lastIndexOf('.') + 1);
    }
}
