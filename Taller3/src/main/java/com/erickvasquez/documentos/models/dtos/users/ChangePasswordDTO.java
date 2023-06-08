package com.erickvasquez.documentos.models.dtos.users;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChangePasswordDTO {

	@NotBlank(message = "Warn! Id is required")
	private String id;

	@NotBlank(message = "Warn! Old password is required")
	@Size(min = 5, message = "Warn! password size is 5 chars")
	private String password;
	
	@NotBlank(message = "Warn! new password is required")
	@Size(min = 5, message = "Warn! password size is 5 chars")
	private String newPassword;
	
}
