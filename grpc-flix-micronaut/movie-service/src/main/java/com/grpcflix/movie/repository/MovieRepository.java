package com.grpcflix.movie.repository;

import com.grpcflix.movie.entity.Movie;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {

    List<Movie> findByGenreOrderByYearDesc(String genre);
}