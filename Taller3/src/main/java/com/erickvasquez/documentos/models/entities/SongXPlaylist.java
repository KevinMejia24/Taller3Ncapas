package com.erickvasquez.documentos.models.entities;

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
@Table(name = "songsxplaylists")
public class SongXPlaylist {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "code")
    private UUID code;

    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "song_code", nullable = true)
    private Song song;
    
    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "playlist_code", nullable = true)
    private PlayList playlist;
    
    @Column(name = "date_added", nullable = true)
    private  java.sql.Timestamp  date_added;

    
	public SongXPlaylist( Song song, PlayList playlist,  java.sql.Timestamp  date_added) {
		super();
		this.song = song;
		this.playlist = playlist;
		this.date_added = date_added;
	}
}
