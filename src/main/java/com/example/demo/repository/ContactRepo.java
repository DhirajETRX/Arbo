package com.example.demo.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entities.Contact;
import com.example.demo.entities.User;

public interface ContactRepo extends JpaRepository<Contact, Integer>{

}
