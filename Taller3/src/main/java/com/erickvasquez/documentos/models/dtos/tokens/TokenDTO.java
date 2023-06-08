package com.erickvasquez.documentos.models.dtos.tokens;

import com.erickvasquez.documentos.models.entities.Token;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TokenDTO {
	private String token;
	
	public TokenDTO(Token token) {
		this.token = token.getContent();
	}
}
