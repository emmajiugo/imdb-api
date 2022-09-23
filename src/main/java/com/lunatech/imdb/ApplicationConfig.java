package com.lunatech.imdb;

import com.lunatech.imdb.controller.MoviesController;
import com.lunatech.imdb.exceptions.ConstraintViolationExceptionMapper;
import com.lunatech.imdb.exceptions.CustomerRequestFilter;
import com.lunatech.imdb.exceptions.JacksonObjectMapper;
import com.lunatech.imdb.exceptions.UnrecognizedFieldExceptionMapper;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Set;

/**
 *
 * @author emmanueladeyemi
 */
@ApplicationPath("imdb")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(MoviesController.class);
        resources.add(ConstraintViolationExceptionMapper.class);
        resources.add(JacksonObjectMapper.class);
        resources.add(UnrecognizedFieldExceptionMapper.class);
        resources.add(CustomerRequestFilter.class);
    }
    
}
