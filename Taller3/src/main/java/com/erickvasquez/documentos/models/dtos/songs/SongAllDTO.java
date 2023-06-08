package com.erickvasquez.documentos.models.dtos.songs;

import java.util.UUID;

import lombok.Data;

@Data
public class SongAllDTO {

	private UUID id;
	private String title;
	private String duration;
	
	public SongAllDTO(UUID id, String title, String duration) {
		super();
		this.id = id;
		this.title = title;
		this.duration = duration;
	}
}
