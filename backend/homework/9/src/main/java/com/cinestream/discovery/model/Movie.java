package com.cinestream.discovery.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a movie in the CineStream catalog.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Movie {
    private String id;
    private String title;
    private String genre;
    private String directorId;
    private List<Review> reviews = new ArrayList<>();
}
