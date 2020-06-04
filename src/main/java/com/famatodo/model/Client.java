package com.famatodo.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "clients")
public class Client implements Serializable {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long clientId;
	private String identificationNumber;
	private String firstName;
	private String secondName;
	private String lastName;
	private String surName;
	private int age;
	private String email;
	private String username;
	private String password;
	private boolean enabled;
	private static final long serialVersionUID = 2784757528833800103L;
	
}
