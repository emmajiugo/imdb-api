/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lunatech.imdb.exceptions;

import com.lunatech.imdb.Global;
import com.lunatech.imdb.util.PropertyProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author emmanueladeyemi
 */
@Provider
public class CustomerRequestFilter implements ContainerRequestFilter {

    @Context
    ResourceInfo resourceInfo;

    @Inject
    PropertyProvider properties;
    
    private static final Logger LOGGER = LogManager.getLogger(CustomerRequestFilter.class);
    
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        String path = requestContext.getUriInfo().getPath();

        try {
            logRequest(requestContext);
        } catch (Exception ex) {

        }

        // IMPORTANT!!! First, Acknowledge any pre-flight test from browsers for this case before validating the headers (CORS stuff)
        if (requestContext.getRequest().getMethod().equals("OPTIONS")) {
            requestContext.abortWith(Response.status(Response.Status.OK).build());

            return;
        }

        requestContext.getHeaders().add("Access-Control-Allow-Origin", "*");
        requestContext.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
        requestContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
        requestContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        requestContext.getHeaders().add("Access-Control-Max-Age", "1209600");

        
        Method method = resourceInfo.getResourceMethod();
        
        boolean present = method.isAnnotationPresent(NoAuthenticate.class);

        if(!present) {

            String headerKey = requestContext.getHeaderString("Authorization");

            if (headerKey == null) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
                return;
            }

            String[] array = headerKey.split(" ");

            List<String> list = Stream.of(array).filter(x -> !x.isEmpty()).collect(Collectors.toList());

            if (!list.get(0).equalsIgnoreCase("Bearer") && !list.get(0).equalsIgnoreCase("Token")) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
                return;
            }

            if (list.size() <= 1) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
                return;
            }

            String apikey = list.get(1);

            if (!properties.getProperty(Global.AUTH_KEY).equalsIgnoreCase(apikey)) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            }

        }

    }

    private void logRequest(ContainerRequestContext requestContext) {

        String path = requestContext.getUriInfo().getPath();

        String headerKey = requestContext.getHeaderString("Authorization");

        JSONObject jSONObject = new JSONObject();
        jSONObject.put("header", headerKey + "")
                .put("path", path + "")
                .put("method", requestContext.getMethod() + "")
                .put("type", requestContext.getMediaType() + "");

        String body = null;

        if (!"GET".equalsIgnoreCase(requestContext.getMethod())) {

            if (requestContext.getEntityStream() != null) {

                BufferedReader br = new BufferedReader(new InputStreamReader(requestContext.getEntityStream()));
                body = br.lines().collect(Collectors.joining());

                requestContext.setEntityStream(new ByteArrayInputStream(body.getBytes()));
            }
        }

        jSONObject.put("body", body);

        LOGGER.info(jSONObject);
    }

}
