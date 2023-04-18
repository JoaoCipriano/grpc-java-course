package com.pilgrim.protobuf;

import com.pilgrim.model.Person;

public class PersonDemo {

    public static void main(String[] args) {
        Person person1 = Person.newBuilder()
                .setName("John")
                .setAge(20)
                .build();
        Person person2 = Person.newBuilder()
                .setName("john")
                .setAge(20)
                .build();
        System.out.println(person1.equals(person2));
    }
}
