package com.example.jpa_demo;

import com.example.jpa_demo.dao.UserRepository;
import com.example.jpa_demo.entity.UserDemo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;

@SpringBootTest
public class UserTest {

    @Autowired
    private UserRepository userRepository;


//    @Test
//    public void findById(){
//        UserDemo userDemo = userService.findByID(2L);
//        System.out.println(userDemo);
//    }

    @Test
    public void save(){
        UserDemo userDemo = new UserDemo();
        for (long i = 11; i < 21; i++) {
            String username = "user"+i;
            userDemo.setId(i);
            userDemo.setUsername(username);
            userDemo.setPassword("123456");
            userDemo.setNickname(username);
            System.out.println(userDemo);
            userRepository.save(userDemo);
        }
    }

    @Test
    public void findAllById(){
        Iterable<UserDemo> userList = userRepository.findAllById(Arrays.asList(2L,3L,4L));
        System.out.println(userList);
    }

    @Test
    public void findAllPaginated(){
        Page<UserDemo> list = userRepository.findAll(PageRequest.of(1,5));
        System.out.println(list.getTotalPages());
        System.out.println(list.getTotalElements());
        System.out.println(list.getContent());  //getContent方法是获取list中的所有数据
    }


}
