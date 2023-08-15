package com.grpcflix.user.service;

import com.grpcflix.user.repository.UserRepository;
import com.pilgrim.grpcflix.common.Genre;
import com.pilgrim.grpcflix.user.UserGenreUpdateRequest;
import com.pilgrim.grpcflix.user.UserResponse;
import com.pilgrim.grpcflix.user.UserSearchRequest;
import com.pilgrim.grpcflix.user.UserServiceGrpc;
import io.grpc.stub.StreamObserver;
import io.micronaut.grpc.annotation.GrpcService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@GrpcService
@RequiredArgsConstructor
public class UserService extends UserServiceGrpc.UserServiceImplBase {

    private final UserRepository userRepository;

    @Override
    public void getUserGenre(UserSearchRequest request, StreamObserver<UserResponse> responseObserver) {
        UserResponse.Builder builder = UserResponse.newBuilder();
        this.userRepository.findById(request.getLoginId())
                .ifPresent(user -> {
                    builder.setName(user.getName())
                            .setLoginId(user.getLoginId())
                            .setGenre(Genre.valueOf(user.getGenre()));
                });
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }

    @Override
    @Transactional
    public void updateUserGenre(UserGenreUpdateRequest request, StreamObserver<UserResponse> responseObserver) {
        UserResponse.Builder builder = UserResponse.newBuilder();
        this.userRepository.findById(request.getLoginId())
                .ifPresent(user -> {
                    user.setGenre(request.getGenre().toString());
                    builder.setName(user.getName())
                            .setLoginId(user.getLoginId())
                            .setGenre(Genre.valueOf(user.getGenre()));
                });
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }
}
