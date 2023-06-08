package com.erickvasquez.documentos.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.ListCrudRepository;

import com.erickvasquez.documentos.models.entities.Song;

public interface SongRepository extends ListCrudRepository<Song, UUID>{

Song findOneByTitle(String title);
	
	List<Song> findByTitleContaining (String title);
}
