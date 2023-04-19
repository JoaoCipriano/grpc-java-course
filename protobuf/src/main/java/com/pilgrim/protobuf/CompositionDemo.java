package com.pilgrim.protobuf;

import com.pilgrim.model.Address;
import com.pilgrim.model.Car;
import com.pilgrim.model.Person;

import java.util.ArrayList;
import java.util.List;

public class CompositionDemo {

    public static void main(String[] args) {
        Address address = Address.newBuilder()
                .setPostbox(123)
                .setStreet("main street")
                .setCity("New York")
                .build();

        Car accord = Car.newBuilder()
                .setMake("Honda")
                .setModel("Accord")
                .setYear(2023)
                .build();

        Car civic = Car.newBuilder()
                .setMake("Honda")
                .setModel("Civic")
                .setYear(2023)
                .build();

        List<Car> cars = new ArrayList<>();
        cars.add(accord);
        cars.add(civic);

        Person person = Person.newBuilder()
                .setName("John")
                .setAge(20)
                .setAddress(address)
                .addAllCar(cars)
                .build();

        System.out.println(person);
    }
}
