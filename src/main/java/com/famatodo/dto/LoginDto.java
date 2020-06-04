package com.famatodo.dto;



import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginDto {
	@NotNull
	private String username;
	@NotNull
	private String password;
}
