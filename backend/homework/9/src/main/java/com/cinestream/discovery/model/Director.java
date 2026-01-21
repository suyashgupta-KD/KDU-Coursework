package com.cinestream.discovery.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents director for a movie in the CineStream catalog.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Director {
    private String id;
    private String name;
}
