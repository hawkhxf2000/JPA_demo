package com.example.jpa_demo;

import com.example.jpa_demo.dao.UserRepository;
import com.example.jpa_demo.entity.UserDemo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class JPQLTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void findUserByUserName(){
        UserDemo userDemo= userRepository.findUserByUsername("user11");
        System.out.println(userDemo);
    }

    @Test
    public void findUserEndWith(){
        UserDemo user = userRepository.findUserLike("user", 2L);
        System.out.println(user);
    }

}
