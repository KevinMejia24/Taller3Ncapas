package com.erickvasquez.documentos.models.dtos.songs;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SaveSongDTO {

	@NotBlank(message = "Warn! title is required")
	private String title;
	
	private int duration;
}
