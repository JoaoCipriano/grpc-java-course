package com.pilgrim.server;

import com.pilgrim.model.TransferRequest;
import com.pilgrim.model.TransferResponse;
import com.pilgrim.model.TransferServiceGrpc;
import io.grpc.stub.StreamObserver;

public class TransferService extends TransferServiceGrpc.TransferServiceImplBase {

    @Override
    public StreamObserver<TransferRequest> transfer(StreamObserver<TransferResponse> responseObserver) {
        return super.transfer(responseObserver);
    }
}
