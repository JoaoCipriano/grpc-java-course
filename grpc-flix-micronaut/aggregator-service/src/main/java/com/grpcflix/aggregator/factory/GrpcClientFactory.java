package com.grpcflix.aggregator.factory;

import com.pilgrim.grpcflix.movie.MovieServiceGrpc;
import com.pilgrim.grpcflix.user.UserServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.netty.shaded.io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import io.grpc.netty.shaded.io.netty.handler.ssl.SslContext;
import io.micronaut.context.annotation.Factory;
import io.micronaut.grpc.annotation.GrpcChannel;
import jakarta.inject.Singleton;

import javax.net.ssl.SSLException;
import javax.xml.crypto.URIReferenceException;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;

@Factory
public class GrpcClientFactory {

    @Singleton
    public UserServiceGrpc.UserServiceBlockingStub userClientStub(@GrpcChannel("user-service") ManagedChannel channel) {
        return UserServiceGrpc
                .newBlockingStub(channel);
    }

    @Singleton
    public MovieServiceGrpc.MovieServiceBlockingStub movieClientStub() throws SSLException, URISyntaxException, URIReferenceException {
        URL certificationUrl = Optional.ofNullable(this.getClass().getClassLoader().getResource("certs/ca.cert.pem"))
                .orElseThrow(URIReferenceException::new);
        SslContext sslContext = GrpcSslContexts.forClient()
                .trustManager(new File(certificationUrl.toURI()))
                .build();
        ManagedChannel managedChannel = NettyChannelBuilder.forAddress("localhost", 7575)
                .sslContext(sslContext)
                .build();

        return MovieServiceGrpc
                .newBlockingStub(managedChannel);
    }
}
