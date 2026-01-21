package com.cinestream.discovery.service;

import com.cinestream.discovery.model.Movie;
import com.cinestream.discovery.repository.MovieRepository;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Movie findMovieById(String id) {
        return movieRepository.findById(id).orElse(null);
    }
}
