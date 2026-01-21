package com.cinestream.discovery.exception;

public class MovieNotFoundException extends RuntimeException {
    public MovieNotFoundException(String movieId) {
        super("Movie not found with id: " + movieId);
    }
}
