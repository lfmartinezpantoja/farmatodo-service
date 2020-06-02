package com.famatodo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClientResponseDto {

	private String message;
	private Long clientId;
}
