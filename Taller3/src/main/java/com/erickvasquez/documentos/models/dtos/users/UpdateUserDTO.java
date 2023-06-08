package com.erickvasquez.documentos.models.dtos.users;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateUserDTO {

	@NotBlank(message = "Warn! Code is required")
	private UUID code;
	
	@NotBlank(message = "Warn! Username is required")
	private String username;
	
	@NotBlank(message = "Warn! Email code is required")
	private String email;
}
