package com.erickvasquez.documentos.services;

import java.util.List;

import com.erickvasquez.documentos.models.dtos.playlists.SavePlayListDTO;
import com.erickvasquez.documentos.models.dtos.playlists.UpdatePlayListDTO;
import com.erickvasquez.documentos.models.entities.PlayList;
import com.erickvasquez.documentos.models.entities.User;

public interface PlaylistService {
	
	void save(SavePlayListDTO playlistInfo, User user) throws Exception;
	 void update(UpdatePlayListDTO playlistInfo) throws Exception;
	 void deleteOneById(String code) throws Exception;
	 PlayList findOneById(String code);
	 List<PlayList> findAll();
	 
	List<PlayList> findTitle(String title);

}
