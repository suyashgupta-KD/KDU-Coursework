package com.cinestream.discovery.service;

import com.cinestream.discovery.model.Director;
import com.cinestream.discovery.model.Movie;
import com.cinestream.discovery.repository.DirectorRepository;
import com.cinestream.discovery.repository.MovieRepository;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final DirectorRepository directorRepository;

    public Movie findMovieById(String id) {
        return movieRepository.findById(id).orElse(null);
    }

    public Director findDirectorById(String id) {
        return directorRepository.findById(id).orElse(null);
    }
}
