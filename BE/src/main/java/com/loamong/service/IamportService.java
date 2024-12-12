package com.loamong.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.loamong.entity.UserEntity;
import com.loamong.repository.UserRepository;

@Service
public class IamportService {
	
	@Value("${iamport.api.key}")
	private String apiKey;
	
	@Value("${iamport.api.secret}")
	private String apiSecret;
	

	@Autowired
	private UserRepository ur;
	
	
    private final RestTemplate restTemplate;

    public IamportService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }
	
    
	
	   // 토큰 발급
    public String getToken() {
        String url = "https://api.iamport.kr/users/getToken";

        Map<String, String> request = Map.of(
            "imp_key", apiKey,
            "imp_secret", apiSecret
        );
        
        System.out.println("imp_key1" + apiKey);
        System.out.println("imp_secret1" + apiSecret);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(request, headers);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);

        Map<String, Object> body = (Map<String, Object>) response.getBody().get("response");
        
        System.out.println("imp_key2" + apiKey);
        System.out.println("imp_secret2" + apiSecret);
        
    	
        return (String) body.get("access_token");
        
        
    }

    // 결제 검증
    public Map<String, Object> verifyPayment(String impUid) {
        String token = getToken();
        String url = "https://api.iamport.kr/payments/" + impUid;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);

        return response.getBody(); // 검증 결과 반환
    }



    // 결제 후 등급 변경
	public void updateRole(String username) {
		
		UserEntity data = ur.findByUsername(username);
		
	    if (data == null) {
	        throw new IllegalArgumentException("해당 username의 사용자가 존재하지 않습니다: " + username);
	    }
	    
		data.setRole("ROLE_PRIMIUM");
		
		ur.save(data);
		
	}
	

}
