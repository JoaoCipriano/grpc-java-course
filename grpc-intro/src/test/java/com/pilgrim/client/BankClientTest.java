package com.pilgrim.client;

import com.pilgrim.model.Balance;
import com.pilgrim.model.BalanceCheckRequest;
import com.pilgrim.model.BankServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BankClientTest {

    private BankServiceGrpc.BankServiceBlockingStub blockingStub;

    @BeforeAll
    void setUp() {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 6565)
                .usePlaintext()
                .build();
        blockingStub = BankServiceGrpc.newBlockingStub(managedChannel);
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
}
