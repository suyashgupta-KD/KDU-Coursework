package com.cinestream.discovery.repository;

import com.cinestream.discovery.model.Director;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DirectorRepository {

    private final List<Director> directors = List.of(
            new Director("101", "Christopher Nolan"),
            new Director("102", "Quentin Tarantino"));

    public Optional<Director> findById(String id) {
        return directors.stream()
                .filter(d -> d.getId().equals(id))
                .findFirst();
    }
}
