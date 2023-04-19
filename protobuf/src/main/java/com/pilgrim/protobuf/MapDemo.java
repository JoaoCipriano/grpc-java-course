package com.pilgrim.protobuf;

import com.pilgrim.model.Car;
import com.pilgrim.model.Dealer;

public class MapDemo {

    public static void main(String[] args) {

        Car accord = Car.newBuilder()
                .setMake("Honda")
                .setModel("Accord")
                .setYear(2023)
                .build();

        Car civic = Car.newBuilder()
                .setMake("Honda")
                .setModel("Civic")
                .setYear(2022)
                .build();

        Dealer dealer = Dealer.newBuilder()
                .putModel(2022, civic)
                .putModel(2023, accord)
                .build();

        System.out.println(
                dealer.getModelMap()
        );
    }
}
