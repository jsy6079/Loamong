package com.loamong.service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;


@Service
public class LostArkApiNewsService {

    @Value("${lostark.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper  objectMapper = new ObjectMapper();

    // 공지사항
    public List<JsonNode> getLostArkNotice(int count) {
        String url = "https://developer-lostark.game.onstove.com/news/notices";

        HttpHeaders headers = new HttpHeaders();
        headers.set("accept", "application/json");
        headers.set("authorization", "bearer " + apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        String responseBody = response.getBody();
        
        try {
        	JsonNode data = objectMapper.readTree(responseBody);
        	
            return StreamSupport.stream(data.spliterator(), false)
                    .sorted((a, b) -> b.get("Date").asText().compareTo(a.get("Date").asText()))
                    .limit(count)
                    .collect(Collectors.toList());
        	
        } catch (Exception e) {
        	e.printStackTrace();
        	return null;
        }
    }
    
    
    // 이벤트
    public String getLostArkEvent(){
        String url = "https://developer-lostark.game.onstove.com/news/events";

        HttpHeaders headers = new HttpHeaders();
        headers.set("accept", "application/json");
        headers.set("authorization", "bearer " + apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        return response.getBody();
        
    }
    
    // 캘린더 [카오스게이트]
    public List<JsonNode> getLostArkChaosgate() {
        String url = "https://developer-lostark.game.onstove.com/gamecontents/calendar";

        HttpHeaders headers = new HttpHeaders();
        headers.set("accept", "application/json");
        headers.set("authorization", "bearer " + apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        String responseBody = response.getBody();
        
        List<JsonNode> todayEvents = new ArrayList<>();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String today = LocalDate.now().format(dateFormatter);
        
        try {
        	// responseBody, api 데이터를 JsonNode 에 넣기
        	JsonNode data = objectMapper.readTree(responseBody);

        	// JsonNode 에 반복문 돌려서 startTimesToday ?
            for (JsonNode event : data) {
            	
            	if(event.get("ContentsName").asText().equals("일렁이는 악마군단 (쿠르잔 북부)")) {
            		ArrayNode startTimesToday = objectMapper.createArrayNode();
            	
            	 
            	// 해당 이벤트의 StartTimes 배열을 순회하여 개별 시간을 검사
                for (JsonNode startTimeNode : event.get("StartTimes")) {
                    String startTime = startTimeNode.asText();
                    String eventDate = startTime.substring(0, 10);  // "yyyy-MM-dd" 형식으로 추출

                    // 오늘 날짜와 같은게 있다면 startTimesToday에 추가를 해
                    if (eventDate.equals(today)) {
                        startTimesToday.add(startTime);
                    }
                }
            	
                // startTimesToday가 비어 있지 않다면 (오늘 날짜에 해당하는 StartTimes가 있는 경우)
                if (!startTimesToday.isEmpty()) {
                	 // TodayStartTimes 필드를 event에 추가하여 오늘 날짜의 StartTimes만 포함시킴
                    ((ObjectNode) event).set("TodayStartTimes", startTimesToday); // TodayStartTimes 필드 추가
                    // 오늘 날짜의 StartTimes가 포함된 event를 todayEvents 리스트에 추가
                    todayEvents.add(event);
                }
            }
          }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
     // 오늘 날짜의 StartTimes가 포함된 이벤트들만 반환
        return todayEvents;
    }
    
    
    // 캘린더 [필드보스]
    public List<JsonNode> getLostArkFildBoss() {
        String url = "https://developer-lostark.game.onstove.com/gamecontents/calendar";

        HttpHeaders headers = new HttpHeaders();
        headers.set("accept", "application/json");
        headers.set("authorization", "bearer " + apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        String responseBody = response.getBody();
        
        List<JsonNode> todayEvents = new ArrayList<>();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String today = LocalDate.now().format(dateFormatter);
        
        try {
        	// responseBody, api 데이터를 JsonNode 에 넣기
        	JsonNode data = objectMapper.readTree(responseBody);

        	// JsonNode 에 반복문 돌려서 startTimesToday ?
            for (JsonNode event : data) {
            	
            	if(event.get("ContentsName").asText().equals("세베크 아툰")) {
            		ArrayNode startTimesToday = objectMapper.createArrayNode();
            	
            	 
            	// 해당 이벤트의 StartTimes 배열을 순회하여 개별 시간을 검사
                for (JsonNode startTimeNode : event.get("StartTimes")) {
                    String startTime = startTimeNode.asText();
                    String eventDate = startTime.substring(0, 10);  // "yyyy-MM-dd" 형식으로 추출

                    // 오늘 날짜와 같은게 있다면 startTimesToday에 추가를 해
                    if (eventDate.equals(today)) {
                        startTimesToday.add(startTime);
                    }
                }
            	
                // startTimesToday가 비어 있지 않다면 (오늘 날짜에 해당하는 StartTimes가 있는 경우)
                if (!startTimesToday.isEmpty()) {
                	 // TodayStartTimes 필드를 event에 추가하여 오늘 날짜의 StartTimes만 포함시킴
                    ((ObjectNode) event).set("TodayStartTimes", startTimesToday); // TodayStartTimes 필드 추가
                    // 오늘 날짜의 StartTimes가 포함된 event를 todayEvents 리스트에 추가
                    todayEvents.add(event);
                }
            }
          }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
     // 오늘 날짜의 StartTimes가 포함된 이벤트들만 반환
        return todayEvents;
    }
    
    
    // 캘린더 [모험섬]
    public List<JsonNode> getLostArkIsland() {
        String url = "https://developer-lostark.game.onstove.com/gamecontents/calendar";

        HttpHeaders headers = new HttpHeaders();
        headers.set("accept", "application/json");
        headers.set("authorization", "bearer " + apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        String responseBody = response.getBody();
        
        List<JsonNode> todayEvents = new ArrayList<>();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String today = LocalDate.now().format(dateFormatter);
        
        try {
        	// responseBody, api 데이터를 JsonNode 에 넣기
        	JsonNode data = objectMapper.readTree(responseBody);

        	// JsonNode 에 반복문 돌려서 startTimesToday ?
            for (JsonNode event : data) {
            	
            	if(event.get("CategoryName").asText().equals("모험 섬")) {
            		ArrayNode startTimesToday = objectMapper.createArrayNode();
            	
            	 
            	// 해당 이벤트의 StartTimes 배열을 순회하여 개별 시간을 검사
                for (JsonNode startTimeNode : event.get("StartTimes")) {
                    String startTime = startTimeNode.asText();
                    String eventDate = startTime.substring(0, 10);  // "yyyy-MM-dd" 형식으로 추출

                    // 오늘 날짜와 같은게 있다면 startTimesToday에 추가를 해
                    if (eventDate.equals(today)) {
                        startTimesToday.add(startTime);
                    }
                }
            	
                // startTimesToday가 비어 있지 않다면 (오늘 날짜에 해당하는 StartTimes가 있는 경우)
                if (!startTimesToday.isEmpty()) {
                	 // TodayStartTimes 필드를 event에 추가하여 오늘 날짜의 StartTimes만 포함시킴
                    ((ObjectNode) event).set("TodayStartTimes", startTimesToday); // TodayStartTimes 필드 추가
                    // 오늘 날짜의 StartTimes가 포함된 event를 todayEvents 리스트에 추가
                    todayEvents.add(event);
                }
            }
          }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
     // 오늘 날짜의 StartTimes가 포함된 이벤트들만 반환
        return todayEvents;
    }
}