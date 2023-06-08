package com.erickvasquez.documentos.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.ListCrudRepository;

import com.erickvasquez.documentos.models.entities.PlayList;

public interface PlayListRepository extends ListCrudRepository<PlayList, UUID> {

	List<PlayList> findByTitleContaining(String title);
}
