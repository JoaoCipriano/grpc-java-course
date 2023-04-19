package com.pilgrim.protobuf;

import com.pilgrim.model.Address;
import com.pilgrim.model.Car;
import com.pilgrim.model.Person;

public class CompositionDemo {

    public static void main(String[] args) {
        Address address = Address.newBuilder()
                .setPostbox(123)
                .setStreet("main street")
                .setCity("New York")
                .build();

        Car car = Car.newBuilder()
                .setMake("Honda")
                .setModel("Accord")
                .setYear(2023)
                .build();

        Person person = Person.newBuilder()
                .setName("John")
                .setAge(20)
                .setAddress(address)
                .setCar(car)
                .build();

        System.out.println(person);
    }
}
