package com.loamong.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loamong.entity.FreeBoardEntity;
import com.loamong.service.FreeBoardService;


import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/freeboard")
public class FreeBoardController {
	
	private final FreeBoardService fs;
	
	// 글 전체 조회
	@GetMapping()
	public ResponseEntity<List<FreeBoardEntity>> getfreeboard(){
		
		List<FreeBoardEntity> freeBoard = fs.getallfreeboard();
		
		return ResponseEntity.ok(freeBoard);
	}
	
	// 글 상세 조회
	@GetMapping("/{num}")
	public ResponseEntity<?> getfreeboarddetail(@PathVariable(name = "num") int num){
		FreeBoardEntity fbe = fs.getfreeboard(num);
		if(fbe == null) {
			return ResponseEntity.status(404).body("해당 게시글은 존재하지 않습니다.");
		}
		return ResponseEntity.ok(fbe);
	}
	
	// 글 작성
	
	// 글 수정
	
	// 글 삭제

}
