package com.pilgrim.protobuf;

import com.pilgrim.model.Person;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PersonDemo {

    public static void main(String[] args) throws IOException {
//        Person person = Person.newBuilder()
//                .setName("John")
//                .setAge(20)
//                .build();

        Path path = Paths.get("person.ser");
//        Files.write(path, person.toByteArray());

        byte[] bytes = Files.readAllBytes(path);
        Person newPerson = Person.parseFrom(bytes);
        System.out.println(newPerson);
    }
}
