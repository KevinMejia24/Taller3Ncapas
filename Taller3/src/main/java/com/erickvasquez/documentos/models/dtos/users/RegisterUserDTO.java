package com.erickvasquez.documentos.models.dtos.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterUserDTO {

	@NotBlank(message = "Warn! username is required")
	private String username;
	
	@NotBlank(message = "Warn! email is required")
	@Email(message = "Warn! email must be valid")
	private String email;
	
	@NotBlank(message = "Warn! password is required")
	@Size(min = 8, message = "Warn! password size is 8 chars")
	@Pattern(regexp = "^[a-zA-Z0-9._?]+$")
	private String password;
}
