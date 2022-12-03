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
	
	@Query(value = "SELECT COUNT(*) FROM USER",  nativeQuery = true)
	Integer countTotalNumberOfUsers();
	
	@Query(value = "SELECT COUNT(*) FROM USER WHERE fav_category LIKE '%WORLD%'",  nativeQuery = true)
	Integer countTotalNumberOfUsersForCategoryWorld();
		
	@Query(value = "SELECT COUNT(*) FROM USER WHERE fav_category LIKE '%LOCAL%'",  nativeQuery = true)
	Integer countTotalNumberOfUsersForCategoryLocal();
		
	@Query(value = "SELECT COUNT(*) FROM USER WHERE fav_category LIKE '%BUSINESS%'",  nativeQuery = true)
	Integer countTotalNumberOfUsersForCategoryBusiness();
		
	@Query(value = "SELECT COUNT(*) FROM USER WHERE fav_category LIKE '%TECHNOLOGY%'",  nativeQuery = true)
	Integer countTotalNumberOfUsersForCategoryTechnology();
		
	@Query(value = "SELECT COUNT(*) FROM USER WHERE fav_category LIKE '%ENTERTAINMENT%'",  nativeQuery = true)
	Integer countTotalNumberOfUsersForCategoryEntertainment();
		
	@Query(value = "SELECT COUNT(*) FROM USER WHERE fav_category LIKE '%SPORTS%'",  nativeQuery = true)
	Integer countTotalNumberOfUsersForCategorySports();
		
	@Query(value = "SELECT COUNT(*) FROM USER WHERE fav_category LIKE '%SCIENCE%'",  nativeQuery = true)
	Integer countTotalNumberOfUsersForCategoryScience();
		
	@Query(value = "SELECT COUNT(*) FROM USER WHERE fav_category LIKE '%HEALTH%'",  nativeQuery = true)
	Integer countTotalNumberOfUsersForCategoryHealth();

	@Query(value = "SELECT * FROM USER u WHERE u.location = :location limit 20",  nativeQuery = true)
	List<User> getProfileByLocation(@Param("location") String location);

	@Query(value = "SELECT * FROM USER u WHERE u.fav_category LIKE CONCAT('%',:category,'%') limit 20",  nativeQuery = true)
	List<User> getProfileByNewsCategory(@Param("category") String category);
	
}
