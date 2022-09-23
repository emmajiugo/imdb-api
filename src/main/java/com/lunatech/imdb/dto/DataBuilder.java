package com.lunatech.imdb.dto;

import com.lunatech.imdb.dao.MovieDao;
import com.lunatech.imdb.model.*;
import lombok.extern.log4j.Log4j2;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
@Log4j2
public class DataBuilder {

    @EJB
    private MovieDao movieDao;

    public Movie buildMovieModel(TitleBasics model) {
        return Movie.builder()
                .id(model.getTconst())
                .type(model.getTitleType())
                .primaryTitle(model.getPrimaryTitle())
                .originalTitle(model.getOriginalTitle())
                .isAdult(model.getIsAdult())
                .startYear(model.getStartYear())
                .endYear(model.getEndYear())
                .runtimeMinutes(model.getRuntimeMinutes())
                .genres(model.getGenres())
                .rating(model.getTitleRatings() != null ? buildMovieRatingModel(model.getTitleRatings()) : null)
                .crew(buildCrewModel(model.getTitleCrew()))
                .casts(model.getTitlePrincipals().stream().map(TitlePrincipals::buildCastModel).collect(Collectors.toList()))
                .build();
    }

    public List<FeaturedMovie> buildFeaturedMoviesModel(NameBasics model) {

        List<String> movieTitles = model.getKnownForTitles() != null ? Arrays.asList(model.getKnownForTitles().split(",")) : new ArrayList<>();

        // get related movies for the actor
        List<FeaturedMovie> featuredMovies = new ArrayList<>();

        for (String titleId : movieTitles) {
            Optional<TitleBasics> optionalTitle = movieDao.getMovieById(titleId);

            optionalTitle.ifPresent(titleBasic -> featuredMovies.add(FeaturedMovie.builder()
                    .id(titleBasic.getTconst())
                    .actorName(model.getPrimaryName())
                    .primaryTitle(titleBasic.getPrimaryTitle())
                    .originalTitle(titleBasic.getOriginalTitle())
                    .casts(titleBasic.getTitlePrincipals().stream()
                            .map(TitlePrincipals::buildCastModel)
                            .filter(cast -> (cast.getCategory().equalsIgnoreCase("actor") || cast.getCategory().equalsIgnoreCase("actress")))
                            .collect(Collectors.toList()))
                    .build()));
        }

        return featuredMovies;
    }

    public List<MovieCast> buildMovieCastModel(List<TitleBasics> movieTitles) {

        // get all movies and their cast
        List<MovieCast> movieCasts = new ArrayList<>();

        for (TitleBasics movie : movieTitles) {

            movieCasts.add(MovieCast.builder()
                    .title(movie.getPrimaryTitle())
                    .casts(movie.getTitlePrincipals().stream()
                            .filter(cast -> (cast.getCategory().equalsIgnoreCase("actor") || cast.getCategory().equalsIgnoreCase("actress")))
                            .map(model -> model.buildCastModel().getName())
                            .collect(Collectors.toList()))
                    .build());
        }

        //return movieCasts;
        // stream out movies with no actors/actresses before returning them
        return movieCasts.stream().filter(m -> !m.getCasts().isEmpty()).collect(Collectors.toList());
    }

    private Rating buildMovieRatingModel(TitleRatings model) {
        return Rating.builder()
                .averageRating(model.getAverageRating())
                .numVotes(model.getNumVotes())
                .build();
    }

    private Crew buildCrewModel(TitleCrew model) {
        List<String> directors = model.getDirectors() != null ? Arrays.asList(model.getDirectors().split(",")) : new ArrayList<>();

        List<String> writers = model.getWriters() != null ? Arrays.asList(model.getWriters().split(",")) : new ArrayList<>();

        return Crew.builder()
                .directors(getCrewMembers(directors))
                .writers(getCrewMembers(writers))
                .build();
    }

    private List<CrewMember> getCrewMembers(List<String> crewIds) {
        List<CrewMember> crewDetails = new ArrayList<>();
        for (String crewId : crewIds) {
            Optional<NameBasics> optionalMember = movieDao.getMemberById(crewId);

            optionalMember.ifPresent(nameBasics -> crewDetails.add(nameBasics.buildCrewMemberModel()));
        }
        return crewDetails;
    }

}
