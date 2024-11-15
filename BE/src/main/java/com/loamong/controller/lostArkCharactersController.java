package com.loamong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.loamong.service.LostArkCharactersService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/character")
public class lostArkCharactersController {
	
	@Autowired
	private LostArkCharactersService ls;
	
	@GetMapping("/profile/{searchCharacter}")
	public ResponseEntity<String> getLostArkProfile(@PathVariable(name = "searchCharacter") String searchCharacter){
		String processedProfile = ls.getLostArkEvent(searchCharacter);
		return ResponseEntity.ok(processedProfile);
	}
		

}
