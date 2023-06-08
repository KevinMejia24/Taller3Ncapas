package com.erickvasquez.documentos.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erickvasquez.documentos.models.dtos.users.ChangePasswordDTO;
import com.erickvasquez.documentos.models.dtos.users.LoginDTO;
import com.erickvasquez.documentos.models.entities.User;
import com.erickvasquez.documentos.repositories.UserRepository;
import com.erickvasquez.documentos.services.AuthServices;

@Service
public class AuthServiceImplements implements AuthServices{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User LogIn(LoginDTO data) {
		User user = userRepository.findOneByUsernameOrEmail(data.getId(),data.getId());
		if (user == null) return null;
		if (!user.getPassword().equals(data.getPassword())) return null;
		
		return user;
	}
	
	@Override
	public void changePassword(ChangePasswordDTO data) throws Exception {
		User user = userRepository.findOneByUsernameOrEmail(data.getId(), data.getId());
		
		if (user == null) return;
		if (!user.getPassword().equals(data.getPassword())) return;
		
		user.setPassword(data.getNewPassword());
		userRepository.save(user);
	}
}
