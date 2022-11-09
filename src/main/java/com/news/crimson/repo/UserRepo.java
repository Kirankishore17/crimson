package com.news.crimson.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.news.crimson.entity.User;

public interface UserRepo extends JpaRepository<User, Integer> {

}
