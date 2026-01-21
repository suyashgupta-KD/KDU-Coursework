package com.cinestream.discovery.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.cinestream.discovery.model.Movie;

@Repository
public class MovieRepository {
    private final List<Movie> movies = List.of(
            new Movie("1", "Inception", "Sci-Fi", "101"),
            new Movie("2", "Interstellar", "Sci-Fi", "101"),
            new Movie("3", "The Dark Knight", "Action", "101"),
            new Movie("4", "Pulp Fiction", "Crime", "102"));

    public Optional<Movie> findById(String id) {
        return movies.stream()
                .filter(m -> m.getId().equals(id))
                .findFirst();
    }
}
