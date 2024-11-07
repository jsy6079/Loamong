package com.loamong.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.loamong.service.LostArkApiNewsService;

@RestController
@RequestMapping("/api")
public class lostArkNoticeController {
	
	@Autowired
	private LostArkApiNewsService ls;
	
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
	
	// 캘린더
	@GetMapping("calender")
	public ResponseEntity<List<JsonNode>> getCalender(){
		List<JsonNode> processedCalender = ls.getLostArkCalender();
		return ResponseEntity.ok(processedCalender);
	}

}
