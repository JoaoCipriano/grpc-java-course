package com.grpcflix.aggregator.dto;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record UserGenre(
        String loginId,
        String genre) {
}