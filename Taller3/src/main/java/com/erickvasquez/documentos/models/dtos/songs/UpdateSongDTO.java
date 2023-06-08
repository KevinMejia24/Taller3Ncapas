package com.erickvasquez.documentos.models.dtos.songs;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateSongDTO {

	@NotBlank(message = "Warn! code is required")
	private String code;
	
	@NotBlank(message = "Warn! title is required")
	private String title;
	
	private int duration;
}
