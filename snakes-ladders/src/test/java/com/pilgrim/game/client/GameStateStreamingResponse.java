package com.pilgrim.game.client;

import com.pilgrim.game.Die;
import com.pilgrim.game.GameState;
import com.pilgrim.game.Player;
import io.grpc.stub.StreamObserver;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

public class GameStateStreamingResponse implements StreamObserver<GameState> {

    private StreamObserver<Die> dieStreamObserver;
    private CountDownLatch latch;

    public GameStateStreamingResponse(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void onNext(GameState gameState) {
        var players = gameState.getPlayersList();
        players.forEach(p -> System.out.println(p.getName() + ":" + p.getPosition()));
        var isGameOver = players.stream()
                .anyMatch(p -> p.getPosition() == 100);
        if (isGameOver) {
            System.out.println("Game Over!");
            this.dieStreamObserver.onCompleted();
        } else {
            this.roll();
        }
        System.out.println("----------------------------------");
    }

    @Override
    public void onError(Throwable throwable) {
        this.latch.countDown();
    }

    @Override
    public void onCompleted() {
        this.latch.countDown();
    }

    public void setDieStreamObserver(StreamObserver<Die> streamObserver) {
        this.dieStreamObserver = streamObserver;
    }

    private void roll() {
        int dieValue = ThreadLocalRandom.current().nextInt(1, 7);
        Die die = Die.newBuilder().setValue(dieValue).build();
        this.dieStreamObserver.onNext(die);
    }
}
