package com.pilgrim.client;

import com.pilgrim.model.Money;
import io.grpc.stub.StreamObserver;

public class MoneyStreamingResponse implements StreamObserver<Money> {

    @Override
    public void onNext(Money money) {
        System.out.println("Received async : " + money.getValue());
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println(throwable.getMessage());
    }

    @Override
    public void onCompleted() {
        System.out.println("Server is done!!");
    }
}
