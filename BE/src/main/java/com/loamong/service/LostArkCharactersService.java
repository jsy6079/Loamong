package com.loamong.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LostArkCharactersService {
	
    @Value("${lostark.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();
	
    // 이벤트
    public String getLostArkEvent(String searchCharacter){
    	String url = "https://developer-lostark.game.onstove.com/armories/characters/" + searchCharacter + "/profiles";

        HttpHeaders headers = new HttpHeaders();
        headers.set("accept", "application/json");
        headers.set("authorization", "bearer " + apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        return response.getBody();
        
    }

}
