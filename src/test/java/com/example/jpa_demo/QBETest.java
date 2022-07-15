package com.example.jpa_demo;

import com.example.jpa_demo.entity.UserDemo;
import com.example.jpa_demo.service.UserQBEService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class QBETest {

    @Autowired
    private UserQBEService userQBEService;

    @Test
    public void QBEBynameTest(){
        List<UserDemo> userDemoList = userQBEService.findByNameDync("ser",null);
        userDemoList.forEach(System.out::println);
    }

    @Test
    public void QBEIsExistedTest(){
        Boolean result = userQBEService.QBEIsExisted("user1",null);
        System.out.println(result);
    }

}
