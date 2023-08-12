package com.pilgrim.client.metadata;

import com.pilgrim.client.deadline.DeadlineInterceptor;
import com.pilgrim.client.rpctypes.MoneyStreamingResponse;
import com.pilgrim.model.Balance;
import com.pilgrim.model.BalanceCheckRequest;
import com.pilgrim.model.BankServiceGrpc;
import com.pilgrim.model.WithdrawRequest;
import io.grpc.Deadline;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.MetadataUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MetadataClientTest {

    private BankServiceGrpc.BankServiceBlockingStub blockingStub;
    private BankServiceGrpc.BankServiceStub bankServiceStub;

    @BeforeAll
    void setUp() {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 6565)
                .intercept(MetadataUtils.newAttachHeadersInterceptor(ClientConstants.getClientToken()))
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
        for (int i = 0; i < 20; i++) {
            try {
                int random = ThreadLocalRandom.current().nextInt(1,4);
                System.out.println("Random : " + random);
                Balance balance = this.blockingStub
                        .withCallCredentials(new UserSessionToken("user-secret-" + random + ":standard"))
                        .getBalance(balanceCheckRequest);
                System.out.println(
                        "Received : " + balance.getAmount()
                );
                Assertions.assertEquals(685, balance.getAmount());
            } catch (StatusRuntimeException exception) {
                exception.printStackTrace();
            }
        }
    }

    @Test
    void withdrawTest() {
        var withdrawRequest = WithdrawRequest.newBuilder()
                .setAccountNumber(6)
                .setAmount(50)
                .build();

        try {
            this.blockingStub
                    .withDeadline(Deadline.after(2, TimeUnit.SECONDS))
                    .withdraw(withdrawRequest)
                    .forEachRemaining(money -> System.out.println("Received : " + money.getValue()));

        } catch (StatusRuntimeException exception) {
            //
        }
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
