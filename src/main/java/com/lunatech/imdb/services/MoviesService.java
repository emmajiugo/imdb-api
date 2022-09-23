/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lunatech.imdb.services;

import com.lunatech.imdb.bean.ResponseBuilder;
import com.lunatech.imdb.dao.MovieDao;
import com.lunatech.imdb.dto.*;
import com.lunatech.imdb.model.TitleBasics;
import com.lunatech.imdb.util.Utility;
import lombok.extern.log4j.Log4j2;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
@Log4j2
public class MoviesService {

    @Inject
    DataBuilder dataBuilder;

    @EJB
    private MovieDao movieDao;

    public Response moviesSearch(SearchRequest request, Pagination paginate) {
        try {
            List<TitleBasics> movieList = movieDao.getMoviesByTitle(request.getKeyword(), paginate);

            if (movieList.isEmpty()) {
                return ResponseBuilder.build(
                        null,
                        false,
                        "No movie found for the title: " + request.getKeyword(),
                        Response.Status.NOT_FOUND,
                        this.getClass());
            }

            List<Movie> movies = movieList.stream().map(title -> dataBuilder.buildMovieModel(title)).collect(Collectors.toList());

            return ResponseBuilder.build(
                    movies,
                    true,
                    "Successful",
                    Response.Status.OK,
                    this.getClass());

        } catch (Exception ex) {
            return ResponseBuilder.build(
                    null,
                    false,
                    ex.getMessage(),
                    Response.Status.INTERNAL_SERVER_ERROR,
                    this.getClass());
        }
    }

    public Response searchTopRatedMovie(String genre, Pagination paginate) {
        try {
            if (Utility.emptyToNull(genre) == null) {
                return ResponseBuilder.build(
                        null,
                        false,
                        "Missing genre",
                        Response.Status.BAD_REQUEST,
                        this.getClass());

            }

            List<TitleBasics> topRatedMovies = movieDao.getTopRatedMoviesByGenre(genre, paginate);

            if (topRatedMovies.isEmpty()) {
                return ResponseBuilder.build(
                        null,
                        false,
                        "No movie found for the genre: " + genre,
                        Response.Status.NOT_FOUND,
                        this.getClass());
            }

            List<Movie> movies = topRatedMovies.stream().map(title -> dataBuilder.buildMovieModel(title)).collect(Collectors.toList());

            return ResponseBuilder.build(
                    movies,
                    true,
                    "Successful",
                    Response.Status.OK,
                    this.getClass());

        } catch (Exception ex) {
            return ResponseBuilder.build(
                    null,
                    false,
                    ex.getMessage(),
                    Response.Status.INTERNAL_SERVER_ERROR,
                    this.getClass());
        }
    }

 }
