package com.lunatech.imdb.bean;

import lombok.extern.log4j.Log4j2;

import javax.ws.rs.core.Response;

@Log4j2
public class ResponseBuilder {

    public static Response build(Object data, boolean responseStatus, String message, Response.Status status, Class type){

        AppResponse processingResponse = new AppResponse();
        processingResponse.setStatus(responseStatus);
        processingResponse.setMessage(message);
        processingResponse.setData(data);

        log.info("Data from : "+ type.getName()+" - "+processingResponse, ResponseBuilder.class);

        return Response.status(status).entity(processingResponse).build();
    }
}
