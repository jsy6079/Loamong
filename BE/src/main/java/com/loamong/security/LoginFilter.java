package com.loamong.security;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// 1. 상속받아서 커스터마이징 할것
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

	
	// 3. AuthenticationManager 주입
    private final AuthenticationManager authenticationManager;
    
	//4. JWTUtil 주입
	private final JWTUtil jwtUtil;

    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {

        this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
    }

    // 2. 인증을 하는 Authentication, 아이디 및 비밀번호를 JSON 객체로 클라이언트에서 받을 거라서 JSON으로 받을수있게 파싱
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            // 요청 본문에서 JSON 데이터를 읽음
            String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

            // JSON 파싱 (라이브러리: Jackson 사용)
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> requestMap = objectMapper.readValue(requestBody, Map.class);

            String username = requestMap.get("username");
            String password = requestMap.get("password");


            // 인증 토큰 생성 -> DTO 처럼 바구니를 생성
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);

            // 인증 시도 -> authenticationManager 한테 전달
            return authenticationManager.authenticate(authToken);

        } catch (IOException e) {
            throw new AuthenticationServiceException("JSON 데이터 처리 중 오류 발생", e);
        }
    }


	//로그인 성공시 실행하는 메소드 (여기서 JWT를 발급하면 됨)
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        String username = customUserDetails.getUsername();
        String nickname = customUserDetails.getNickname();
        String encodedUsername = URLEncoder.encode(nickname, StandardCharsets.UTF_8); // URL 인코딩
        
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        String role = auth.getAuthority();

        String token = jwtUtil.createJwt(username, role, 60*60*10L);
        System.out.println("발급된 JWT: " + token);
        
        Date expirationDate = new Date(System.currentTimeMillis() + (60 * 60 * 10L * 1000));
        System.out.println("JWT 만료 시간: " + expirationDate);
        System.out.println("JWT 검증 중 서버 시간: " + new Date(System.currentTimeMillis()));


        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("Username", encodedUsername); // 닉네임 가져오기
        
       
    }
    

    //로그인 실패시 실행하는 메소드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
		//로그인 실패시 401 응답 코드 반환
    	 System.out.println("실패 사유 : " + failed.getMessage());
        response.setStatus(401);
    }
}