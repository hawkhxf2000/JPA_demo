# JPA Data 学习笔记
## 动态查询
- ### Query by Example
新建Entity实体类，将需要进行查询的字符串传入对应的类属性，然后利用JPA的Example类构建example查询条件，最后使用jpa中的QueryByExampleExecutor接口方法对example进行分析查询。如果传入的参数为null则忽略这个查询条件。    

**缺点：**
1. 只能使用字符串进行查询
2. 不能进行嵌套查询

#### 使用方法：
- Step1: 建立repository,继承QueryByExampleExecutor接口

~~~java
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface UserRepository extends QueryByExampleExecutor<T> {} //T为进行查询的数据库实体类
~~~

- Step2: 新建service层
~~~java
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
~~~

- Step3: 新建测试
~~~java
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

~~~

#### QBE中的匹配器参数资料：
https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#query-by-example.matchers