package com.news.crimson.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.news.crimson.entity.BookmarkedNews;

public interface BookmarkedNewsRepo extends JpaRepository<BookmarkedNews, Integer>{

	@Query(value = "SELECT * FROM BookmarkedNews u WHERE u.user_id = :userId",  nativeQuery = true)
	public String getBookmarkedNewsByUserId(@Param("userId") Integer userId);


}
