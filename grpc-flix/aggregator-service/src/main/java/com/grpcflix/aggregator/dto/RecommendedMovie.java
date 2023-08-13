package com.grpcflix.aggregator.dto;

public record RecommendedMovie(
        String title,
        int year,
        double rating) {
}
