package com.cinestream.discovery.controller;

import com.cinestream.discovery.model.Director;
import com.cinestream.discovery.model.Movie;
import com.cinestream.discovery.service.MovieService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

/**
 * GraphQL controller for Movie-related queries and mutations.
 * Acts as the entry point for the CineStream Discovery API.
 */
@Controller
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    /**
     * Fetch a movie by its ID.
     *
     * @param id movie identifier
     * @return Movie object or null if not found
     */

    @QueryMapping
    public Movie findMovieById(@Argument String id) {
        return movieService.findMovieById(id);
    }

    /**
     * Resolves the director field for a Movie.
     * Called only when the client requests the director.
     */
    @SchemaMapping(typeName = "Movie", field = "director")
    public Director director(Movie movie) {
        return movieService.findDirectorById(movie.getDirectorId());
    }

    /**
     * Adds a review to a movie.
     *
     * @param movieId movie identifier
     * @param comment review text
     * @param rating  rating (1–5)
     * @return updated Movie
     */
    @MutationMapping
    public Movie addReview(@Argument String movieId,
            @Argument String comment,
            @Argument int rating) {
        return movieService.addReview(movieId, comment, rating);
    }
}
