package com.gp.electro.store.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import com.gp.electro.store.entities.User;
@Component
public interface UserRepo extends JpaRepository<User, String> {
	//implementation is given dynamically at runtime
	
	
Optional<User>  findByEmail(String email);
Optional<User> findByEmailAndPassword(String email,String password);

@Query("select u from  User u where u.about LIKE %:keyword%")
Optional<List<User>>findByNameContaining(@Param("keyword") String keyword);

//@Query("select u from User u where u.about LIKE %:keyword%")

@Query("select u.email from User u")
List<String> getAllEmail();

}
