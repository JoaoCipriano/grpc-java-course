package com.pilgrim.game.server;

import com.pilgrim.game.Die;
import com.pilgrim.game.GameServiceGrpc;
import com.pilgrim.game.GameState;
import com.pilgrim.game.Player;
import io.grpc.stub.StreamObserver;

public class GameService extends GameServiceGrpc.GameServiceImplBase {

    @Override
    public StreamObserver<Die> roll(StreamObserver<GameState> responseObserver) {
        var client = Player.newBuilder().setName("client").setPosition(0).build();
        var server = Player.newBuilder().setName("server").setPosition(0).build();
        return new DieStreamingRequest(client, server, responseObserver);
    }
}
