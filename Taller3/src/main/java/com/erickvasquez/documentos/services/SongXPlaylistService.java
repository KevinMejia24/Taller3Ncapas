package com.erickvasquez.documentos.services;

import com.erickvasquez.documentos.models.entities.PlayList;
import com.erickvasquez.documentos.models.entities.Song;

public interface SongXPlaylistService {

	void save(Song song, PlayList playlist, java.sql.Timestamp timestamp) throws Exception;
	void delete(String songCode, String PlaylistCode) throws Exception;
}
