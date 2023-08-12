package com.pilgrim.client.rpctypes;

import com.pilgrim.model.Balance;
import com.pilgrim.model.BalanceCheckRequest;
import com.pilgrim.model.BankServiceGrpc;
import com.pilgrim.model.DepositRequest;
import com.pilgrim.model.WithdrawRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.netty.shaded.io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import io.grpc.netty.shaded.io.netty.handler.ssl.SslContext;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import javax.net.ssl.SSLException;
import java.io.File;
import java.util.concurrent.CountDownLatch;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BankClientTest {

    private BankServiceGrpc.BankServiceBlockingStub blockingStub;
    private BankServiceGrpc.BankServiceStub bankServiceStub;

    @BeforeAll
    void setUp() throws SSLException {

        SslContext sslContext = GrpcSslContexts.forClient()
                .trustManager(new File("C:\\Users\\USER\\dev\\git\\grpc-java-course\\ssl-tls\\ca.cert.pem"))
                .build();

        ManagedChannel managedChannel = NettyChannelBuilder.forAddress("localhost", 6565)
//                .usePlaintext()
                .sslContext(sslContext)
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
        Assertions.assertEquals(700, balance.getAmount());
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
    void withdrawAsyncTest() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        var withdrawRequest = WithdrawRequest.newBuilder()
                .setAccountNumber(7)
                .setAmount(670)
                .build();
        this.bankServiceStub.withdraw(withdrawRequest, new MoneyStreamingResponse(latch));
        latch.await();
    }

    @Test
    void cashStreamingRequest() {
        CountDownLatch latch = new CountDownLatch(1);
        Assertions.assertDoesNotThrow(() -> {
            StreamObserver<DepositRequest> streamObserver = this.bankServiceStub.cashDeposit(new BalanceStreamObserver(latch));
            for (int i = 0; i < 10; i++) {
                DepositRequest depositRequest = DepositRequest.newBuilder().setAccountNumber(8).setAmount(10).build();
                streamObserver.onNext(depositRequest);
            }
            streamObserver.onCompleted();
            latch.await();
        });
    }
}
