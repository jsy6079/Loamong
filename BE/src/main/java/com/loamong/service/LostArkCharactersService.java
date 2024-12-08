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
	
    // 능력치 기본 정보
    public String getLostArkAbility(String searchCharacter){
    	String url = "https://developer-lostark.game.onstove.com/armories/characters/" + searchCharacter + "/profiles";

        HttpHeaders headers = new HttpHeaders();
        headers.set("accept", "application/json");
        headers.set("authorization", "bearer " + apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        return response.getBody();
        
    }
    
    // 내실 정보
    public String getLostArkCollect(String searchCharacter) {
    	String url = "https://developer-lostark.game.onstove.com/armories/characters/" + searchCharacter + "/collectibles";
    	
        HttpHeaders headers = new HttpHeaders();
        headers.set("accept", "application/json");
        headers.set("authorization", "bearer " + apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        return response.getBody();
    }

}
