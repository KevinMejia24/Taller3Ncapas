package com.erickvasquez.documentos.models.dtos.response;

import java.util.List;

import com.erickvasquez.documentos.models.dtos.playlists.AllSongDTO;
import com.erickvasquez.documentos.models.entities.PlayList;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlayListSongsDTO {

	private PlayList playlist;
	private List<AllSongDTO>song;
	private String total;
}
