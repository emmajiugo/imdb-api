/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lunatech.imdb.controller;

import com.lunatech.imdb.dto.*;
import com.lunatech.imdb.services.BaconSearchService;
import com.lunatech.imdb.services.MoviesService;
import lombok.extern.log4j.Log4j2;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Log4j2
@Path(value = "movies")
@Produces(value = MediaType.APPLICATION_JSON)
public class MoviesController {
    
    @Inject
    MoviesService moviesService;

    @Inject
    BaconSearchService baconSearchService;

    @POST
    @Path(value = "/")
    public Response searchMovies(@Valid SearchRequest request,
                                 @BeanParam RequestParam param) {
        
        log.info("REQUEST: "+request);

        Pagination paginate = new Pagination();
        paginate.setPage(param.getPage());
        paginate.setPerPage(param.getPerPage());
        
        return moviesService.moviesSearch(request, paginate);
    }

    @GET
    @Path(value = "/top-rated")
    public Response getTopRatedMovieByGenre(@BeanParam RequestParam param) {

        log.info("QueryParam: "+ param.getGenre());

        Pagination paginate = new Pagination();
        paginate.setPage(param.getPage());
        paginate.setPerPage(param.getPerPage());

        return moviesService.searchTopRatedMovie(param.getGenre(), paginate);

    }

    @POST
    @Path(value = "/bacon-number")
    public Response findBaconNumber(@Valid BaconRequest request) {

        log.info("REQUEST: "+request);

        return baconSearchService.findBaconPath(request);
    }
}
