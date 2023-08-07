package com.pilgrim.client.loadbalancing;

import com.pilgrim.client.rpctypes.BalanceStreamObserver;
import com.pilgrim.model.Balance;
import com.pilgrim.model.BalanceCheckRequest;
import com.pilgrim.model.BankServiceGrpc;
import com.pilgrim.model.DepositRequest;
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
class NginxClientTest {

    private BankServiceGrpc.BankServiceBlockingStub blockingStub;
    private BankServiceGrpc.BankServiceStub stub;

    @BeforeAll
    void setUp() {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 8585)
                .usePlaintext()
                .build();
        blockingStub = BankServiceGrpc.newBlockingStub(managedChannel);
        stub = BankServiceGrpc.newStub(managedChannel);
    }

    @Test
    void balanceTest() {
        for (int i = 0; i < 100; i++) {
            int randomInt = ThreadLocalRandom.current().nextInt(1, 10);
            BalanceCheckRequest balanceCheckRequest = BalanceCheckRequest.newBuilder()
                    .setAccountNumber(randomInt)
                    .build();
            Balance balance = this.blockingStub.getBalance(balanceCheckRequest);
            System.out.println(
                    "Received : " + balance.getAmount()
            );
            Assertions.assertEquals(randomInt * 100, balance.getAmount());
        }
    }

    @Test
    void cashStreamingRequest() {
        CountDownLatch latch = new CountDownLatch(1);
        Assertions.assertDoesNotThrow(() -> {
            StreamObserver<DepositRequest> streamObserver = this.stub.cashDeposit(new BalanceStreamObserver(latch));
            for (int i = 0; i < 10; i++) {
                DepositRequest depositRequest = DepositRequest.newBuilder().setAccountNumber(8).setAmount(10).build();
                streamObserver.onNext(depositRequest);
            }
            streamObserver.onCompleted();
            latch.await();
        });
    }
}
