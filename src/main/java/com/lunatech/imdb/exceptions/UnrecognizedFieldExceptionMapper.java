package com.lunatech.imdb.exceptions;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.Map;

@Provider
public class UnrecognizedFieldExceptionMapper implements ExceptionMapper<UnrecognizedPropertyException> {

    @Override
    public Response toResponse(UnrecognizedPropertyException e) {

        Map<String, Object > error = new HashMap<>();
        error.put("status", false);
        error.put("message", "field '"+e.getPropertyName()+"' is not permitted in this context");
        
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(error)
                .build();
    }

}
