package com.grpcflix.aggregator.service;

import com.grpcflix.aggregator.dto.RecommendedMovie;
import com.pilgrim.grpcflix.movie.MovieSearchRequest;
import com.pilgrim.grpcflix.movie.MovieServiceGrpc;
import com.pilgrim.grpcflix.user.UserSearchRequest;
import com.pilgrim.grpcflix.user.UserServiceGrpc;
import io.micronaut.grpc.annotation.GrpcChannel;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Singleton
@RequiredArgsConstructor
public class UserMovieService {

    @GrpcChannel("user-service")
    private final UserServiceGrpc.UserServiceBlockingStub userStub;

    @GrpcChannel("movie-service")
    private final MovieServiceGrpc.MovieServiceBlockingStub movieStub;

    public List<RecommendedMovie> getUserMovieSuggestions(String loginId) {
        var userSearchRequest = UserSearchRequest.newBuilder().setLoginId(loginId).build();
        var userResponse = this.userStub.getUserGenre(userSearchRequest);
        var movieSearchRequest = MovieSearchRequest.newBuilder().setGenre(userResponse.getGenre()).build();
        var movieSearchResponse = this.movieStub.getMovies(movieSearchRequest);
        return movieSearchResponse.getMovieList()
                .stream()
                .map(movieDto -> new RecommendedMovie(movieDto.getTitle(), movieDto.getYear(), movieDto.getRating()))
                .toList();
    }
}
