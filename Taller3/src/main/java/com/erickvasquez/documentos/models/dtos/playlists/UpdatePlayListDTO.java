package com.erickvasquez.documentos.models.dtos.playlists;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdatePlayListDTO {


	@NotBlank(message = "Warn! Playlist code is required")
	private String code;
	
	@NotBlank(message = "Warn! Title is required")
	private String title;
	
	@NotBlank(message = "Warn! Description is required")
	private String description;
}
