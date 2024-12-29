package com.nutech.mar.repository;

import com.nutech.mar.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

	@Query(value = "SELECT * FROM tb_user WHERE username = :username", nativeQuery = true)
	User findByUsername(@Param("username") String username);
	
}