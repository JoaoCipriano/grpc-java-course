package com.grpcflix.movie.service;

import com.grpcflix.movie.repository.MovieRepository;
import com.pilgrim.grpcflix.movie.MovieDto;
import com.pilgrim.grpcflix.movie.MovieSearchRequest;
import com.pilgrim.grpcflix.movie.MovieSearchResponse;
import com.pilgrim.grpcflix.movie.MovieServiceGrpc;
import io.grpc.stub.StreamObserver;
import io.micronaut.grpc.annotation.GrpcService;
import lombok.RequiredArgsConstructor;

@GrpcService
@RequiredArgsConstructor
public class MovieService extends MovieServiceGrpc.MovieServiceImplBase {

    private final MovieRepository movieRepository;

    @Override
    public void getMovies(MovieSearchRequest request, StreamObserver<MovieSearchResponse> responseObserver) {
        var movieDtoList = this.movieRepository.findByGenreOrderByYearDesc(request.getGenre().toString())
                .stream()
                .map(movie -> MovieDto.newBuilder()
                        .setTitle(movie.getTitle())
                        .setYear(movie.getYear())
                        .setRating(movie.getRating())
                        .build()
                )
                .toList();
        responseObserver.onNext(MovieSearchResponse.newBuilder().addAllMovie(movieDtoList).build());
        responseObserver.onCompleted();
    }
}