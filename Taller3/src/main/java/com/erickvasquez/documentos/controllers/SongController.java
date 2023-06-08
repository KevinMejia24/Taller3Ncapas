package com.erickvasquez.documentos.controllers;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.erickvasquez.documentos.models.dtos.response.MessageDTO;
import com.erickvasquez.documentos.models.dtos.songs.SaveSongDTO;
import com.erickvasquez.documentos.models.dtos.songs.SongAllDTO;
import com.erickvasquez.documentos.models.dtos.songs.UpdateSongDTO;
import com.erickvasquez.documentos.models.entities.Song;
import com.erickvasquez.documentos.services.SongService;
import com.erickvasquez.documentos.utils.RequestErrorHandler;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/song/")
@CrossOrigin("*")
public class SongController {

	@Autowired
	private SongService songService;
	@Autowired
	private RequestErrorHandler errorHandler;
	
	//rute /song con el buscador
	@GetMapping("")
	public ResponseEntity<?> getSongs(@RequestParam(required = false) String title) {
	    if (title != null && !title.isEmpty()) {
	        List<Song> songs = songService.findFragmentTitle(title);
	        List<SongAllDTO> songDTOs = new ArrayList<>();
	        for (Song song : songs) {
	            String duration = formatDuration(song.getDuration());
	            SongAllDTO songDTO = new SongAllDTO(song.getCode(), song.getTitle(), duration);
	            songDTOs.add(songDTO);
	        }
	        return new ResponseEntity<>(songDTOs, HttpStatus.OK);
	    } else {
	        List<Song> songs = songService.findAll();
	        List<SongAllDTO> songDTOs = new ArrayList<>();
	        for (Song song : songs) {
	            String duration = formatDuration(song.getDuration());
	            SongAllDTO songDTO = new SongAllDTO(song.getCode(), song.getTitle(), duration);
	            songDTOs.add(songDTO);
	        }
	        return new ResponseEntity<>(songDTOs, HttpStatus.OK);
	    }
	}


	@GetMapping("/{title}")
	public ResponseEntity<?> getSongByTitle(@PathVariable String title) {
		Song song = songService.findOneByTitle(title);
		if (song == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<>(song, HttpStatus.OK);
	}
	
	
	@GetMapping("/code/{code}")
	public ResponseEntity<?> getSongByCode(@PathVariable String code) {
		Song song = songService.findOneById(code);
		if (song == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<>(song, HttpStatus.OK);
	}
	
	//Convercion de tiempo
	private String formatDuration(int duration) {
		
		int minutes = duration / 60;
		int seconds = duration % 60;
		return String.format("%02d:%02d", minutes, seconds);
	
	}
	
	@PostMapping("")
	public ResponseEntity<?> createSong(
			@ModelAttribute @Valid SaveSongDTO data, BindingResult validations) {
		if (validations.hasErrors()) {
			return new ResponseEntity<>(
					errorHandler.mapErrors(validations.getFieldErrors()), HttpStatus.BAD_REQUEST);
		}
		
		try {
			songService.save(data);
			return new ResponseEntity<>(new MessageDTO("Excellent! song created"), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
	@PutMapping("/updatesong/")
	public ResponseEntity<?> updateSong(
			@ModelAttribute @Valid UpdateSongDTO data, BindingResult validations) {
		if (validations.hasErrors()) {
			return new ResponseEntity<>(
					errorHandler.mapErrors(validations.getFieldErrors()), HttpStatus.BAD_REQUEST);
		}
		
		Song song = songService.findOneById(data.getCode());
		if (song == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		try {
			songService.update(data);
			return new ResponseEntity<>(new MessageDTO("Excellent! song updated"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/{code}")
	public ResponseEntity<?> deleteSong(@PathVariable String code) {
		Song song = songService.findOneById(code);
		if (song == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			
		try {
			songService.deleteOneById(code);
			return new ResponseEntity<>(new MessageDTO("Excellent! deleted song"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}

}
