package com.erickvasquez.documentos.services;

import com.erickvasquez.documentos.models.dtos.users.ChangePasswordDTO;
import com.erickvasquez.documentos.models.dtos.users.LoginDTO;
import com.erickvasquez.documentos.models.entities.User;

public interface AuthServices {

	User LogIn(LoginDTO data);
	void changePassword(ChangePasswordDTO data) throws Exception;
}
