package com.pilgrim.protobuf;

import com.pilgrim.model.Person;

public class PersonDemo {

    public static void main(String[] args) {
        Person person = Person.newBuilder()
                .setName("John")
                .setAge(20)
                .build();
        System.out.println(person);
    }
}
