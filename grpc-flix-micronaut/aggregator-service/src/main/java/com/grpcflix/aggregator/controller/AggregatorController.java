package com.grpcflix.aggregator.controller;

import com.grpcflix.aggregator.dto.RecommendedMovie;
import com.grpcflix.aggregator.dto.UserGenre;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Put;

import java.util.List;

@Controller
public class AggregatorController {

    @Get("/users/{loginId}")
    public List<RecommendedMovie> getMovies(@PathVariable String loginId) {
        return null;
    }

    @Put("/users")
    public void setUserGenre(@Body UserGenre userGenre) {

    }
}
