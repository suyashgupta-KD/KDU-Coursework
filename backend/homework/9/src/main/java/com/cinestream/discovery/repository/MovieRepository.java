package com.cinestream.discovery.repository;

import com.cinestream.discovery.model.Movie;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The repository providing in memory storage of movies
 * saves their directors
 * and reviews
 */
@Repository
public class MovieRepository {

    // Exercise 1:
    private final List<Movie> movies = new ArrayList<>(List.of(
            new Movie("1", "Inception", "Sci-Fi", "101", new ArrayList<>()),
            new Movie("2", "Interstellar", "Sci-Fi", "101", new ArrayList<>()),
            new Movie("3", "The Dark Knight", "Action", "101", new ArrayList<>()),
            new Movie("4", "Pulp Fiction", "Crime", "102", new ArrayList<>())));

    public Optional<Movie> findById(String id) {
        return movies.stream().filter(m -> m.getId().equals(id)).findFirst();
    }
}
