package com.erickvasquez.documentos.services;

import java.util.List;

import com.erickvasquez.documentos.models.dtos.songs.SaveSongDTO;
import com.erickvasquez.documentos.models.dtos.songs.UpdateSongDTO;
import com.erickvasquez.documentos.models.entities.Song;

public interface SongService {

	void save(SaveSongDTO songInfo) throws Exception;
    void update(UpdateSongDTO songInfo) throws Exception;
    void deleteOneById(String code) throws Exception;
    Song findOneById(String code);
    Song findOneByTitle(String title);
    List<Song> findAll();
    
    List<Song> findFragmentTitle(String title);
}
