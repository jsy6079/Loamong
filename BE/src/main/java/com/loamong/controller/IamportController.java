package com.loamong.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.loamong.service.IamportService;

@RestController
@RequestMapping("/api")
public class IamportController {
	
	@Autowired
	private IamportService iamportService;
	
	
	   @PostMapping("/verify")
	    public ResponseEntity<?> verifyPayment(@RequestBody Map<String, String> request) {
	        String impUid = request.get("imp_uid");
	        Map<String, Object> body = iamportService.verifyPayment(impUid);

	        if (body != null && body.get("response") != null) {
	            Map<String, Object> paymentData = (Map<String, Object>) body.get("response");

	            // 금액 및 상태 검증
	            if ("paid".equals(paymentData.get("status"))) {
	                System.out.println("결제가 성공적으로 검증되었습니다." + impUid);
	                return ResponseEntity.ok("결제가 성공적으로 검증되었습니다.");
	            }
	        }

	        System.out.println("결제 검증 실패.");
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("결제 검증 실패");
	    }


}
