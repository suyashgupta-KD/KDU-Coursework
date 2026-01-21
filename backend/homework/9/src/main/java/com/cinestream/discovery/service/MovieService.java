package com.cinestream.discovery.service;

import com.cinestream.discovery.exception.MovieNotFoundException;
import com.cinestream.discovery.model.Director;
import com.cinestream.discovery.model.Movie;
import com.cinestream.discovery.model.Review;
import com.cinestream.discovery.repository.DirectorRepository;
import com.cinestream.discovery.repository.MovieRepository;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

/**
 * Service layer handling movie business logic.
 */
@Service
@AllArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final DirectorRepository directorRepository;

    // Exercise 1: Basic implementation to disocver the movie
    public Movie findMovieById(String id) {
        return movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException(id));
    }

    // Exercise 2: Added implementation to disocver the movie + director
    public Director findDirectorById(String id) {
        return directorRepository.findById(id).orElse(null);
    }

    // Exercise 3: Addded implementation to add a review
    public Movie addReview(String movieId, String comment, int rating) {
        Movie movie = findMovieById(movieId);
        if (movie == null)
            return null;

        movie.getReviews().add(new Review(comment, rating));
        return movie;
    }
}
