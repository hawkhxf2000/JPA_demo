package com.example.jpa_demo.dao;

import com.example.jpa_demo.entity.UserDemo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface UserRepository extends PagingAndSortingRepository<UserDemo,Long> {
    //增删改查

    //查询
    @Query("FROM UserDemo where username=?1")
    UserDemo findUserByUsername(String username);

    @Query("select u FROM UserDemo u where u.nickname like ?1% and u.id = ?2")
    UserDemo findUserLike(String username, Long id);
}
