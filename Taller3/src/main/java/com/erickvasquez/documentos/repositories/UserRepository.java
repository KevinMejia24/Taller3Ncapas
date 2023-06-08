package com.erickvasquez.documentos.repositories;

import java.util.UUID;

import org.springframework.data.repository.ListCrudRepository;

import com.erickvasquez.documentos.models.entities.User;

public interface UserRepository extends ListCrudRepository<User, UUID>{

	User findOneByUsernameOrEmail(String username, String email);
}
