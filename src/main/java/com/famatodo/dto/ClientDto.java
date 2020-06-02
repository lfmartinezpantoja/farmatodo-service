package com.famatodo.dto;



import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientDto {
	@NotNull
	private int indentificationNumber;
	@NotBlank
	@NotNull
	private String firstName;
	private String secondName;
	@NotNull
	private String lastName;
	private String surName;
	@NotNull
	private int age;
	@NotNull
	private String email;
	@NotNull
	private String username;
	@NotNull
	private String password;
}
