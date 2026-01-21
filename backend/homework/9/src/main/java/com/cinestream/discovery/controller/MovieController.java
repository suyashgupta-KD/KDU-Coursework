package com.cinestream.discovery.controller;

import com.cinestream.discovery.model.Director;
import com.cinestream.discovery.model.Movie;
import com.cinestream.discovery.service.MovieService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @QueryMapping
    public Movie findMovieById(@Argument String id) {
        return movieService.findMovieById(id);
    }

    @SchemaMapping(typeName = "Movie", field = "director")
    public Director director(Movie movie) {
        return movieService.findDirectorById(movie.getDirectorId());
    }
}
