package com.example.demo.entities;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
@Table(name = "CONTACT")
public class Contact {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int cId;
	private String name;

	
	@ManyToOne
	@JsonIgnore
	private User user;

	public Contact(int cId, String name, User user) {
		super();
		this.cId = cId;
		this.name = name;
		this.user = user;
	}
	
	public Contact() {
		// TODO Auto-generated constructor stub
	}

	public int getcId() {
		return cId;
	}

	public void setcId(int cId) {
		this.cId = cId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return this.cId==((Contact)obj).getcId();
	}
	
	
	
}
