package com.erickvasquez.documentos.models.entities;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@Entity
@Table(name = "playlists")
@ToString(exclude = {"songxplaylist"})
public class PlayList {

	@Id
	@Column(name= "code")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID code;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "description")
	private String description;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_code",nullable = true)
	private User user;
	
	@OneToMany(mappedBy = "playlist",fetch = FetchType.LAZY)
	@JsonIgnore
	private List<SongXPlaylist> songxplaylist;

	public PlayList(String title, String description, User user) {
		super();
		this.title = title;
		this.description = description;
		this.user = user;
	}
}
