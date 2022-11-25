package com.news.crimson.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.news.crimson.entity.User;

public interface UserRepo extends JpaRepository<User, Integer> {

	@Query(value = "SELECT u.id FROM USER u WHERE u.email = :email and u.login_source = :source",  nativeQuery = true)
	List<Integer> getUserIDByEmailAndSource(@Param("email") String email, @Param("source") String source);
	
	@Query(value = "SELECT * FROM USER u WHERE u.email = :email and u.login_source = :source",  nativeQuery = true)
	List<User> getUserByEmailAndSource(@Param("email") String email, @Param("source") String source);
	
	@Query(value = "SELECT * FROM USER u WHERE u.id = :id",  nativeQuery = true)
	List<User> getUserById(@Param("id") Integer id);
		
	
}
