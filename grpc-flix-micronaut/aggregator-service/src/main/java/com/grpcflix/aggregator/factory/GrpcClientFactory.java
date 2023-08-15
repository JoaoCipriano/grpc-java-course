package com.grpcflix.aggregator.factory;

import com.pilgrim.grpcflix.movie.MovieServiceGrpc;
import com.pilgrim.grpcflix.user.UserServiceGrpc;
import io.grpc.ManagedChannel;
import io.micronaut.context.annotation.Factory;
import io.micronaut.grpc.annotation.GrpcChannel;
import jakarta.inject.Singleton;

@Factory
public class GrpcClientFactory {

    @Singleton
    public UserServiceGrpc.UserServiceBlockingStub userClientStub(@GrpcChannel("user-service") ManagedChannel channel) {
        return UserServiceGrpc
                .newBlockingStub(channel);
    }

    @Singleton
    public MovieServiceGrpc.MovieServiceBlockingStub movieClientStub(@GrpcChannel("movie-service") ManagedChannel channel) {
        return MovieServiceGrpc
                .newBlockingStub(channel);
    }
}
