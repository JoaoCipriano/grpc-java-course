package com.pilgrim.client.deadline;

import com.pilgrim.client.rpctypes.BalanceStreamObserver;
import com.pilgrim.client.rpctypes.MoneyStreamingResponse;
import com.pilgrim.model.Balance;
import com.pilgrim.model.BalanceCheckRequest;
import com.pilgrim.model.BankServiceGrpc;
import com.pilgrim.model.DepositRequest;
import com.pilgrim.model.WithdrawRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.concurrent.CountDownLatch;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DeadlineClientTest {

    private BankServiceGrpc.BankServiceBlockingStub blockingStub;
    private BankServiceGrpc.BankServiceStub bankServiceStub;

    @BeforeAll
    void setUp() {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 6565)
                .usePlaintext()
                .build();
        blockingStub = BankServiceGrpc.newBlockingStub(managedChannel);
        bankServiceStub = BankServiceGrpc.newStub(managedChannel);
    }

    @Test
    void balanceTest() {
        BalanceCheckRequest balanceCheckRequest = BalanceCheckRequest.newBuilder()
                .setAccountNumber(7)
                .build();
        Balance balance = this.blockingStub.getBalance(balanceCheckRequest);
        System.out.println(
                "Received : " + balance.getAmount()
        );
        Assertions.assertEquals(70, balance.getAmount());
    }

    @Test
    void withdrawTest() {
        var withdrawRequest = WithdrawRequest.newBuilder()
                .setAccountNumber(7)
                .setAmount(30)
                .build();
        Assertions.assertDoesNotThrow(
                () -> this.blockingStub.withdraw(withdrawRequest)
                        .forEachRemaining(money -> System.out.println("Received : " + money.getValue()))
        );
    }

    @Test
    void withdrawAsyncTest() {
        CountDownLatch latch = new CountDownLatch(1);
        var withdrawRequest = WithdrawRequest.newBuilder()
                .setAccountNumber(7)
                .setAmount(40)
                .build();
        Assertions.assertDoesNotThrow(() -> {
            this.bankServiceStub.withdraw(withdrawRequest, new MoneyStreamingResponse(latch));
            latch.await();
        });
    }
}
