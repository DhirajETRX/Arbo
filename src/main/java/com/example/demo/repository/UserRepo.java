package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entities.User;

public interface UserRepo extends JpaRepository<User, Integer>{
	@Query("select u from User u where u.email=?1")
	public User getUserByUserName(String email);
}
