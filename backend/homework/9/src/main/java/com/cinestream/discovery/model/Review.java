package com.cinestream.discovery.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents reviews for a movie in the CineStream catalog.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    private String comment;
    private int rating;
}
