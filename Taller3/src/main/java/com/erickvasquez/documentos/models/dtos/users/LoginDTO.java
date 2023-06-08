package com.erickvasquez.documentos.models.dtos.users;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {

	
	@NotBlank(message = "Warn! required identification")
	private String id;
	
	@NotBlank(message = "Warn! required password")
	private String password;
	
}
