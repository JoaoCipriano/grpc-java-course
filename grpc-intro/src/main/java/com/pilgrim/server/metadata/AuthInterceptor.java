package com.pilgrim.server.metadata;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import io.grpc.Status;

import java.util.Objects;

public class AuthInterceptor implements ServerInterceptor {
    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata, ServerCallHandler<ReqT, RespT> serverCallHandler) {
        String clientToken = metadata.get(ServerConstants.USER_TOKEN);
        if (this.validate(clientToken)) {
            return serverCallHandler.startCall(serverCall, metadata);
        } else {
            Status status = Status.UNAUTHENTICATED.withDescription("invalid token/expired token");
            serverCall.close(status, metadata);
        }
        return new ServerCall.Listener<>() {
        };
    }

    private boolean validate(String token) {
        return Objects.nonNull(token) && token.equals("user-secret-3");
    }
}
