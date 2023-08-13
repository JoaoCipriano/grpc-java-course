package com.grpcflix.aggregator.service;

import com.grpcflix.aggregator.dto.RecommendedMovie;
import com.pilgrim.grpcflix.movie.MovieSearchRequest;
import com.pilgrim.grpcflix.movie.MovieSearchResponse;
import com.pilgrim.grpcflix.movie.MovieServiceGrpc;
import com.pilgrim.grpcflix.user.UserResponse;
import com.pilgrim.grpcflix.user.UserSearchRequest;
import com.pilgrim.grpcflix.user.UserServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserMovieService {

    @GrpcClient("user-service")
    private UserServiceGrpc.UserServiceBlockingStub userStub;

    @GrpcClient("movie-service")
    private MovieServiceGrpc.MovieServiceBlockingStub movieStub;

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
