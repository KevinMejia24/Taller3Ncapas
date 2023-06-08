package com.erickvasquez.documentos.services.implementations;

import java.lang.ProcessHandle.Info;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.erickvasquez.documentos.models.dtos.users.RegisterUserDTO;
import com.erickvasquez.documentos.models.dtos.users.UpdateUserDTO;
import com.erickvasquez.documentos.models.entities.Token;
import com.erickvasquez.documentos.models.entities.User;
import com.erickvasquez.documentos.repositories.UserRepository;
import com.erickvasquez.documentos.repositories.TokenRepository;
import com.erickvasquez.documentos.services.UserService;
import com.erickvasquez.documentos.utils.JWTTools;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImplement  implements UserService{
	
	@Autowired
	private JWTTools jwtTools;
	@Autowired
	private TokenRepository tokenRepository;
	//password encrypt
	@Autowired
	public PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;

	
	/* public void save(RegisterUserDTO userInfo) throws Exception {
		User user = new User(
				userInfo.setUsername(userInfo.getUsername()),
				userInfo.setEmail(userInfo.getEmail()),
				userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()))
				);
		
		userRepository.save(user);
		
	} */
	
	@Override
	public void save(RegisterUserDTO userInfo) throws Exception{
		User user = new User();
		
		user.setUsername(userInfo.getUsername());
		user.setEmail(userInfo.getEmail());
		user.setPassword(
							passwordEncoder.encode(userInfo.getPassword())
						);
		userRepository.save(user);
	}
	@Override
	public void update(UpdateUserDTO userInfo) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(String code) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User findOneById(String code) {
		return userRepository.findOneByUsernameOrEmail(code, code);
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}
	

	@Override
	public User findOneByUsernameOrEmail(String userData) {
		
		return userRepository.findOneByUsernameOrEmail(userData, userData);
	}
	@Override
	public Boolean comparePassword(String toCompare, String current) {
		return passwordEncoder.matches(toCompare, current);
	}
	
	
	@Override
	@Transactional(rollbackOn = Exception.class)
	public Token registerToken(User user) throws Exception{
		cleanTokens(user);
		String tokenString = jwtTools.generateToken(user);
		Token token = new Token(tokenString, user);
		
		tokenRepository.save(token);
		
		return token;	
	}
	
	@Override
	public Boolean isTokenValid(User user, String token) {
		try {
			cleanTokens(user);
			List<Token> tokens = tokenRepository.findByUserAndActive(user, true);
			
			tokens.stream()
				.filter(tk -> tk.getContent().equals(token))
				.findAny()
				.orElseThrow(() -> new Exception());
			
			return true;
		} catch (Exception e) {
			return false;
		}		
	}
	
	@Override
	@Transactional(rollbackOn = Exception.class)
	public void cleanTokens(User user) throws Exception {
		List<Token> tokens = tokenRepository.findByUserAndActive(user, true);
		
		tokens.forEach(token -> {
			if(!jwtTools.verifyToken(token.getContent())) {
				token.setActive(false);
				tokenRepository.save(token);
			}
		});
		
	}
	
	@Override
	public User findUserAuthenticated() {
		String username = SecurityContextHolder
			.getContext()
			.getAuthentication()
			.getName();
		
		return userRepository.findOneByUsernameOrEmail(username, username);
	}
}
