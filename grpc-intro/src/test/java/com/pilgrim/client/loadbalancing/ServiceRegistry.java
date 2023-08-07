package com.pilgrim.client.loadbalancing;

import io.grpc.EquivalentAddressGroup;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceRegistry {

    private static final Map<String, List<EquivalentAddressGroup>> MAP = new HashMap<>();

    // payment-service 127.0.0.1:8080,128.0.0.1:8080
    public static void register(String service, List<String> instances) {
        List<EquivalentAddressGroup> addressGroupList = instances.stream()
                .map(i -> i.split(":"))
                .map(a -> new InetSocketAddress(a[0], Integer.parseInt(a[1])))
                .map(EquivalentAddressGroup::new)
                .toList();
        MAP.put(service, addressGroupList);
    }

    public static List<EquivalentAddressGroup> getInstances(String service) {
        return MAP.get(service);
    }
}
