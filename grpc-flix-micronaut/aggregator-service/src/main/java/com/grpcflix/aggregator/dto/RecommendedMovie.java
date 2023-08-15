package com.grpcflix.aggregator.dto;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record RecommendedMovie(
        String title,
        int year,
        double rating) {
}