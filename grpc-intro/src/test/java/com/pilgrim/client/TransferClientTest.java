package com.pilgrim.client;

import com.pilgrim.model.TransferRequest;
import com.pilgrim.model.TransferServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TransferClientTest {

    private TransferServiceGrpc.TransferServiceStub stub;

    @BeforeAll
    void setUp() {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 6565)
                .usePlaintext()
                .build();
        stub = TransferServiceGrpc.newStub(managedChannel);
    }

    @Test
    void transfer() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        TransferStreamingResponse response = new TransferStreamingResponse(latch);
        Assertions.assertDoesNotThrow(() -> {
            StreamObserver<TransferRequest> requestStreamObserver = this.stub.transfer(response);
            for (int i = 0; i < 100; i++) {
                var request = TransferRequest.newBuilder()
                        .setFromAccount(ThreadLocalRandom.current().nextInt(1, 10))
                        .setToAccount(ThreadLocalRandom.current().nextInt(1, 10))
                        .setAmount(ThreadLocalRandom.current().nextInt(1, 21))
                        .build();
                requestStreamObserver.onNext(request);
            }
            requestStreamObserver.onCompleted();
            latch.await();
        });
    }
}
