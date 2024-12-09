package com.loamong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.loamong.service.LostArkCharactersService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/loa/character")
public class lostArkCharactersController {
	
	@Autowired
	private LostArkCharactersService ls;
	
	// 능력치 기본 정보
	@GetMapping("/profile/{searchCharacter}")
	public ResponseEntity<String> getLostArkProfile(@PathVariable(name = "searchCharacter") String searchCharacter){
		return ResponseEntity.ok(ls.getLostArkAbility(searchCharacter));
	}
	
	// 내실 정보
	@GetMapping("/collection/{searchCharacter}")
	public ResponseEntity<String> getLostArkCollect(@PathVariable(name = "searchCharacter") String searchCharacter){
		return ResponseEntity.ok(ls.getLostArkCollect(searchCharacter));
	}
		
	
}
