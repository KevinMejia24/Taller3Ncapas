package com.erickvasquez.documentos.services.implementations;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erickvasquez.documentos.models.dtos.playlists.SavePlayListDTO;
import com.erickvasquez.documentos.models.dtos.playlists.UpdatePlayListDTO;
import com.erickvasquez.documentos.models.entities.PlayList;
import com.erickvasquez.documentos.models.entities.User;
import com.erickvasquez.documentos.repositories.PlayListRepository;
import com.erickvasquez.documentos.services.PlaylistService;

@Service
public class PlayListServiceImplement implements PlaylistService {

	@Autowired
	private PlayListRepository playlistRepository;

	@Override
	public void save(SavePlayListDTO playlistInfo, User user) throws Exception {
		PlayList playlist = new PlayList(
				playlistInfo.getTitle(),
				playlistInfo.getDescription(),
				user
				);
				
		playlistRepository.save(playlist);
	}

	@Override
	public void update(UpdatePlayListDTO playlistInfo) throws Exception {
		UUID _code = UUID.fromString(playlistInfo.getCode());
		PlayList playlist = playlistRepository.findById(_code).orElse(null);
		
		if (playlist == null) return;
		
		playlist.setTitle(playlistInfo.getTitle());
		playlist.setDescription(playlistInfo.getDescription());
		
		playlistRepository.save(playlist);
	}

	@Override
	public void deleteOneById(String code) throws Exception {
		UUID _code = UUID.fromString(code);
		playlistRepository.deleteById(_code);
	}

	@Override
	public PlayList findOneById(String code) {
		try {
			UUID _code = UUID.fromString(code);
			return playlistRepository.findById(_code).orElse(null);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<PlayList> findAll() {
		return playlistRepository.findAll();
	}
	
	// user for playlist
		@Override
		public List<PlayList> findTitle(String title) {
			return playlistRepository.findByTitleContaining(title);
		}
		
}
