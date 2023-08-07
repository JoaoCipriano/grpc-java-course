package com.pilgrim.client.loadbalancing;

import io.grpc.EquivalentAddressGroup;
import io.grpc.NameResolver;

import java.util.List;

public class TempNameResolver extends NameResolver {

    private final String service;

    public TempNameResolver(String service) {
        this.service = service;
    }

    @Override
    public String getServiceAuthority() {
        return "temp";
    }

    @Override
    public void shutdown() {

    }

    @Override
    public void refresh() {

    }

    @Override
    public void start(Listener2 listener) {
        List<EquivalentAddressGroup> instances = ServiceRegistry.getInstances(this.service);
        ResolutionResult resolutionResult = ResolutionResult.newBuilder().setAddresses(instances).build();
        listener.onResult(resolutionResult);
    }
}
