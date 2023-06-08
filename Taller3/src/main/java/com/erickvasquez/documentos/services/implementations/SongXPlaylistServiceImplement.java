package com.erickvasquez.documentos.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erickvasquez.documentos.models.entities.PlayList;
import com.erickvasquez.documentos.models.entities.Song;
import com.erickvasquez.documentos.models.entities.SongXPlaylist;
import com.erickvasquez.documentos.repositories.SongxPlayListRepository;
import com.erickvasquez.documentos.services.SongXPlaylistService;

@Service
public class SongXPlaylistServiceImplement  implements SongXPlaylistService{
	
	@Autowired
	private SongxPlayListRepository repository;

	@Override
	public 	void save(Song song, PlayList playlist, java.sql.Timestamp timestamp) throws Exception {
		
		SongXPlaylist relation = new SongXPlaylist(song, playlist, timestamp);
		repository.save(relation);
	}

	@Override
	public void delete(String songCode, String PlaylistCode) throws Exception {
		// TODO : implement delete song from play-list service
	}
}
