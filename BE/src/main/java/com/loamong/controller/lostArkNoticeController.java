package com.loamong.controller;


import java.util.List;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.loamong.service.LostArkApiNewsService;
import com.loamong.service.LostArkCharactersService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/loa")
public class lostArkNoticeController {
	
	private final LostArkApiNewsService ls;
	
	// 공지사항
	@GetMapping("/news")
	public ResponseEntity<List<JsonNode>> getNews(){
		List<JsonNode> processedNews = ls.getLostArkNotice(5);
		return ResponseEntity.ok(processedNews);
				
	}
	
	// 이벤트
	@GetMapping("/events")
	public ResponseEntity<String> getEvents(){
		String processedEvents = ls.getLostArkEvent();
		return ResponseEntity.ok(processedEvents);
	}
	
	// 캘린더 [카오스게이트]
	@GetMapping("/calender/chaosGate")
	public ResponseEntity<List<JsonNode>> getChaos(){
		List<JsonNode> processedChaos = ls.getLostArkChaosgate();
		return ResponseEntity.ok(processedChaos);
	}
	
	// 캘린더 [필드보스]
	@GetMapping("/calender/fildBoss")
	public ResponseEntity<List<JsonNode>> getFild(){
		List<JsonNode> processedFild = ls.getLostArkFildBoss();
		return ResponseEntity.ok(processedFild);
	}
	
	// 캘린더 [모험섬]
	@GetMapping("/calender/island")
	public ResponseEntity<List<JsonNode>> getIsland(){
		List<JsonNode> processedFild = ls.getLostArkIsland();
		return ResponseEntity.ok(processedFild);
	}

}
