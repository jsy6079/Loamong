package com.loamong.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.loamong.dto.JoinDTO;
import com.loamong.service.JoinService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class JoinController {
	
	private final JoinService js;
	
	@PostMapping("/join")
	public ResponseEntity<Map<String, String>> joinMember(@Valid @RequestBody JoinDTO joinDTO){
		 Map<String, String> errors = new HashMap<>();
		
        // 아이디 중복 확인
        if (js.existsByUsername(joinDTO.getUsername())) {
            errors.put("username", "이미 존재하는 아이디입니다.");
        }

        // 닉네임 중복 확인
        if (js.existsByNickname(joinDTO.getNickname())) {
            errors.put("nickname", "이미 존재하는 닉네임입니다.");
        }

        // 비밀번호 유효성 검사
        if (joinDTO.getPassword().length() < 5) {
            errors.put("password", "비밀번호는 5자 이상이어야 합니다.");
        }
        
        // 에러가 있다면 400 Bad Request와 함께 반환
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(errors);
        }
        
        // 회원가입 로직 실행 (유효성 검사 통과)
        js.joinMember(joinDTO);
        return ResponseEntity.ok(Map.of("message", "가입이 완료되었습니다."));
		
	}
	

}
