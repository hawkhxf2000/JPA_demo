package com.example.jpa_demo.service;

import com.example.jpa_demo.dao.UserRepository;
import com.example.jpa_demo.entity.UserDemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserQBEService {

    @Autowired
    private UserRepository userRepository;

    //使用用户名和昵称进行动态查询
    public List<UserDemo> findByNameDync(String username, String nickname){
        UserDemo example = new UserDemo();
        example.setUsername(username);
        example.setNickname(nickname);

        //使用ExampleMatcher增加匹配条件
        ExampleMatcher matcher = ExampleMatcher.matching()                    //指match所有参数
                .withIgnorePaths("id")
                .withIgnoreCase("username","nickname")          //忽略大小写
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING) //包含查询字符串
                .withMatcher("nickname", m->m.startsWith());     //nickname以查询字符串开头

        //通过example构建查询条件
        Example<UserDemo> userDemoExample = Example.of(example,matcher);     //生成example范例

        return (List<UserDemo>) userRepository.findAll(userDemoExample);     //使用example进行查询
    }

    //使用用户名和昵称动态查询是否在数据库中存在对应记录
    public boolean QBEIsExisted(String username, String nickname){
        UserDemo example =new UserDemo();
        example.setUsername(username);
        example.setNickname(nickname);

        Example<UserDemo> userDemoExample = Example.of(example);

        return userRepository.exists(userDemoExample);
    }
}
