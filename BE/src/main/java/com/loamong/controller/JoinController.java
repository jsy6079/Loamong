package com.loamong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loamong.dto.JoinDTO;
import com.loamong.service.JoinService;

@RestController
@RequestMapping("/api")
public class JoinController {
	
	@Autowired
	private JoinService js;
	
	@PostMapping("/join")
	public ResponseEntity<String> joinMember(@RequestBody JoinDTO joinDTO){
		js.joinMember(joinDTO);
		
		return ResponseEntity.ok("가입이 완료되었습니다.");
		
	}
	

}
