package com.erickvasquez.documentos.models.dtos.playlists;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SavePlayListDTO {

	@NotBlank(message = "Error! Title is required")
	private String title;
	
	@NotBlank(message = "Error! Description is required")
	private String description;
}
