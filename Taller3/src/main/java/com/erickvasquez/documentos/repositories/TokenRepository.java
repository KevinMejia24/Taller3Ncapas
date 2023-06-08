package com.erickvasquez.documentos.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.ListCrudRepository;

import com.erickvasquez.documentos.models.entities.Token;
import com.erickvasquez.documentos.models.entities.User;

public interface TokenRepository extends ListCrudRepository<Token, UUID>{
	List<Token> findByUserAndActive(User user, Boolean active);

}
