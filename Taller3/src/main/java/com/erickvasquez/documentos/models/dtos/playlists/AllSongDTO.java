package com.erickvasquez.documentos.models.dtos.playlists;

import java.util.UUID;

import lombok.Data;

@Data
public class AllSongDTO {

	UUID code;
	private String title;
	private String duration;
	private  java.sql.Timestamp  date;
	
	
	public AllSongDTO(UUID code, String title, String duration,  java.sql.Timestamp  date) {
		super();
		this.code = code;
		this.title = title;
		this.duration = duration;
		this.date = date;
	}
}
