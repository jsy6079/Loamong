package com.loamong.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

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
    
    // 내실 필터
    public Map<String, Map<String, List<Map<String, Object>>>> getLostArkCollectFilter(String searchCharacter) {
        String url = "https://developer-lostark.game.onstove.com/armories/characters/" + searchCharacter + "/collectibles";

        HttpHeaders headers = new HttpHeaders();
        headers.set("accept", "application/json");
        headers.set("authorization", "bearer " + apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        // JSON 파싱 시작
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Map<String, Object>> collectibles = objectMapper.readValue(response.getBody(), new TypeReference<>() {});

            // 분류 결과 저장
            Map<String, Map<String, List<Map<String, Object>>>> result = new HashMap<>();

            // 각 타입별로 분류
            for (Map<String, Object> collectible : collectibles) {
                String type = (String) collectible.get("Type");
                List<Map<String, Object>> points = (List<Map<String, Object>>) collectible.get("CollectiblePoints");

                // Point 기준으로 그룹화
                Map<String, List<Map<String, Object>>> categorized = points.stream()
                        .collect(Collectors.groupingBy(point -> (int) point.get("Point") == 1 ? "achieved" : "notAchieved"));

                result.put(type, categorized);
            }

            return result;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to process collectibles data.");
        }
    }
    

}
