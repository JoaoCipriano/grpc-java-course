package com.pilgrim.protobuf;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.InvalidProtocolBufferException;
import com.pilgrim.json.JPerson;
import com.pilgrim.model.Person;

public class PerformanceTest {

    public static void main(String[] args) {

        //json
        JPerson person1 = new JPerson();
        person1.setName("John");
        person1.setAge(20);
        ObjectMapper mapper = new ObjectMapper();
        Runnable runnable1 = () -> {
            try {
                byte[] bytes = mapper.writeValueAsBytes(person1);
                mapper.readValue(bytes, JPerson.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        //protobuf
        Person person2 = Person.newBuilder()
                .setName("John")
                .setAge(20)
                .build();
        Runnable runnable2 = () -> {
            try {
                byte[] bytes = person2.toByteArray();
                Person person = Person.parseFrom(bytes);
            } catch (InvalidProtocolBufferException e) {
                e.printStackTrace();
            }
        };
    }

    private static void runPerformanceTest(Runnable runnable, String method) {
        long time1 = System.currentTimeMillis();
        for (int i = 0; i < 1_000_000; i++) {
            runnable.run();
        }
        long time2 = System.currentTimeMillis();
        System.out.println(
                method + " : " + (time2 - time1) + " ms"
        );
    }
}
