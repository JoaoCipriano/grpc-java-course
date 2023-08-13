package com.grpcflix.aggregator.service;

import com.grpcflix.aggregator.dto.RecommendedMovie;
import com.grpcflix.aggregator.dto.UserGenre;
import com.pilgrim.grpcflix.common.Genre;
import com.pilgrim.grpcflix.movie.MovieSearchRequest;
import com.pilgrim.grpcflix.movie.MovieServiceGrpc;
import com.pilgrim.grpcflix.user.UserGenreUpdateRequest;
import com.pilgrim.grpcflix.user.UserSearchRequest;
import com.pilgrim.grpcflix.user.UserServiceGrpc;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
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

    public void setUserGenre(UserGenre userGenre) {
        var userGenreUpdateRequest = UserGenreUpdateRequest.newBuilder()
                .setLoginId(userGenre.loginId())
                .setGenre(Genre.valueOf(userGenre.genre().toUpperCase()))
                .build();
        var userResponse = this.userStub.updateUserGenre(userGenreUpdateRequest);
        log.info(String.format("The %s genre has updated", userResponse.getName()));
    }
}
