package com.erickvasquez.documentos.models.entities;

import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "token")
public class Token {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "code")
	private UUID code;
	
	@Column(name = "content")
	private String content;
	
	@Column(name = "timestamp", insertable = false, updatable = false)
	private Date timestamp;
	
	@Column(name = "active", insertable = false)
	private Boolean active;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_code")
	private User user;

	public Token(String content, User user) {
		super();
		this.content = content;
		this.user = user;
	}
	
	
	
	

}
