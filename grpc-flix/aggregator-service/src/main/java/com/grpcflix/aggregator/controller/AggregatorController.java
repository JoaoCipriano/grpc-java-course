package com.grpcflix.aggregator.controller;

import com.grpcflix.aggregator.dto.RecommendedMovie;
import com.grpcflix.aggregator.dto.UserGenre;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AggregatorController {

    @GetMapping("/user/{loginId}")
    public List<RecommendedMovie> getMovies(@PathVariable String loginId) {
        return null;
    }

    @PutMapping("/user")
    public void setUserGenre(@RequestBody UserGenre userGenre) {

    }
}
