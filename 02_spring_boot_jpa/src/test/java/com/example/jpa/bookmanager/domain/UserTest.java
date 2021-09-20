package com.example.jpa.bookmanager.domain;

import org.apache.tomcat.jni.Local;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    @Test
    void test() {
        User user1 = new User();
        user1.setEmail("test@gmail.com");
        user1.setName("userName");
        user1.setCreatedAt(LocalDateTime.now());
        user1.setUpdatedAt(LocalDateTime.now());
        System.out.println(">>> user1: " + user1);

        // User user2 = new User(null, "testName", "test@gmail.com", LocalDateTime.now(), LocalDateTime.now(), null);
        User user2 = new User(null, "testName", "test@gmail.com", LocalDateTime.now(), LocalDateTime.now());
        System.out.println(">>> user2: " + user2);
        User user3 = new User("testName", "test@gmail.com");
        System.out.println(">>> user3: " + user3);

        User user4 = User.builder()
                .name("testName")
                .email("test@gmail.com")
                .build();
        System.out.println(">>> user4: " + user4);

    }
}