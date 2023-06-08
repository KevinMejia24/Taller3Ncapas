package com.erickvasquez.documentos.controllers;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erickvasquez.documentos.models.dtos.playlists.AllSongDTO;
import com.erickvasquez.documentos.models.dtos.playlists.SavePlayListDTO;
import com.erickvasquez.documentos.models.dtos.playlists.UpdatePlayListDTO;
import com.erickvasquez.documentos.models.dtos.response.MessageDTO;
import com.erickvasquez.documentos.models.dtos.response.PlayListSongsDTO;
import com.erickvasquez.documentos.models.dtos.songxplaylist.SaveSongXPlaylistDTO;
import com.erickvasquez.documentos.models.entities.PlayList;
import com.erickvasquez.documentos.models.entities.Song;
import com.erickvasquez.documentos.models.entities.SongXPlaylist;
import com.erickvasquez.documentos.models.entities.User;
import com.erickvasquez.documentos.services.PlaylistService;
import com.erickvasquez.documentos.services.SongService;
import com.erickvasquez.documentos.services.SongXPlaylistService;
import com.erickvasquez.documentos.services.UserService;
import com.erickvasquez.documentos.utils.JWTTools;
import com.erickvasquez.documentos.utils.RequestErrorHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/playlist")
@CrossOrigin("*")
public class PlayListController {

	@Autowired
	private JWTTools jwtTools;
	@Autowired
	private PlaylistService playlistService;
	@Autowired
	private SongService songService;
	@Autowired
	private SongXPlaylistService songxPlaylistService;
	@Autowired
	private UserService userService;
	@Autowired
	private RequestErrorHandler errorHandler;
	
	
	@GetMapping("")
	public ResponseEntity<?> getPlayLists() {
		List<PlayList> playlist = playlistService.findAll();
		return new ResponseEntity<>(playlist, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getPlayList(@PathVariable String id) {
		PlayList playlist = playlistService.findOneById(id);
		
		if (playlist == null)
			return new ResponseEntity<>(new MessageDTO("Warn! playlist not found"), HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<>(playlist, HttpStatus.OK);
	}
	

	// Create playlist of user
	@PostMapping("/createplaylist")
	public ResponseEntity<?> createPlayList(
			@RequestBody @Valid SavePlayListDTO data, HttpServletRequest request,BindingResult validations) {
		
		String tokenHeader = request.getHeader("Authorization");
		String token = tokenHeader.substring(7);
		String username = jwtTools.getUsernameFrom(token);
		if (validations.hasErrors()) {
			return new ResponseEntity<>(
					errorHandler.mapErrors(validations.getFieldErrors()), HttpStatus.BAD_REQUEST);
		}
		
		User user = userService.findOneById(username);
		if (user == null)
			return new ResponseEntity<>(new MessageDTO("Warn! user not found"), HttpStatus.NOT_FOUND);
		
		try {
			playlistService.save(data, user);
			return new ResponseEntity<>(new MessageDTO("Excellent! playlist created"), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}
	//Convercion de tiempo
		private String formatDuration(int duration) {
			
			int minutes = duration / 60;
			int seconds = duration % 60;
			return String.format("%02d:%02d", minutes, seconds);
		
		}
		
	
	//Add song 
	@PostMapping("addsong/{id}")
	public ResponseEntity<?> addSongToPlaylist( @PathVariable String id,
			@ModelAttribute @Valid SaveSongXPlaylistDTO data, BindingResult validations) {
		if (validations.hasErrors()) {
			return new ResponseEntity<>(
					errorHandler.mapErrors(validations.getFieldErrors()), HttpStatus.BAD_REQUEST);
		}
		
		Song song = songService.findOneById(data.getSongCode());
		if (song == null)
			return new ResponseEntity<>(new MessageDTO("Warn! song not found"), HttpStatus.NOT_FOUND);

		
		PlayList playlist = playlistService.findOneById(id);
		if (playlist == null)
			return new ResponseEntity<>(new MessageDTO("Warn! playlist not found"), HttpStatus.NOT_FOUND);
		
		List<SongXPlaylist> list = playlist.getSongxplaylist(); 
		boolean verify = false;
		
		for  (SongXPlaylist songXPlaylist : list) {
			if (songXPlaylist.getSong().equals(song)) {
				verify = true;
				
			}
		}
		if (verify)
			return new ResponseEntity<>(new MessageDTO("Song duplicate"), HttpStatus.BAD_REQUEST);
			
		try {
			
			songxPlaylistService.save(song, playlist, new Timestamp(System.currentTimeMillis()));
			return new ResponseEntity<>(new MessageDTO("Excellent! song added to playlist"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// obtenes la lista de canciones del usuario (lista)
	@GetMapping("/songs/{id}")

	public ResponseEntity<?> getPlayListSongs(@PathVariable String id) {
		PlayList playlist = playlistService.findOneById(id);
		
		if (playlist == null)
			return new ResponseEntity<>(new MessageDTO("Warn! playlist not found"), HttpStatus.NOT_FOUND);
		
		// Obtener las relaciones
		List<SongXPlaylist> relations = playlist.getSongxplaylist();
		
		List<AllSongDTO> listsongs = new ArrayList<>();
		
		int count = 0;
		
		for  (SongXPlaylist songXPlaylist : relations) {
				AllSongDTO  allSongDTO = new AllSongDTO(songXPlaylist.getSong().getCode() , songXPlaylist.getSong().getTitle(), formatDuration(songXPlaylist.getSong().getDuration()), 
						songXPlaylist.getDate_added());
				listsongs.add(allSongDTO);
				
				count += songXPlaylist.getSong().getDuration();
		}
		
		
		PlayListSongsDTO response = new PlayListSongsDTO(playlist, listsongs, formatDuration(count));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
	
	
	@PutMapping("")
	public ResponseEntity<?> updatePlayList(
			@ModelAttribute @Valid UpdatePlayListDTO data, BindingResult validations) {
		if (validations.hasErrors()) {
			return new ResponseEntity<>(
					errorHandler.mapErrors(validations.getFieldErrors()), HttpStatus.BAD_REQUEST);
		}
		
		try {
			playlistService.update(data);
			return new ResponseEntity<>(new MessageDTO("Excellent! playlist updated"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletePlayList(@PathVariable String id) {
		try {
			playlistService.deleteOneById(id);
			return new ResponseEntity<>(new MessageDTO("Excellent! playlist deleted"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
