package com.erickvasquez.documentos.repositories;

import java.util.UUID;

import org.springframework.data.repository.ListCrudRepository;

import com.erickvasquez.documentos.models.entities.SongXPlaylist;

public interface SongxPlayListRepository extends ListCrudRepository<SongXPlaylist, UUID> {

}
