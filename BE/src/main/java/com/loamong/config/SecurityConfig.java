package com.loamong.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.loamong.security.JWTFilter;
import com.loamong.security.JWTUtil;
import com.loamong.security.LoginFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
    //5. AuthenticationManager가 인자로 받을 AuthenticationConfiguraion 객체 생성자 주입
    private final AuthenticationConfiguration authenticationConfiguration;
    
    //7. JWTUtil 주입
  	private final JWTUtil jwtUtil;

  	// 6. AuthenticationConfiguration, JWTUtil 주입
    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, JWTUtil jwtUtil) {

        this.authenticationConfiguration = authenticationConfiguration;
		this.jwtUtil = jwtUtil;
    }

    //4. AuthenticationManager Bean 등록
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }
    
    
    // 2. 비밀번호 인코더 등록
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }
    
    
    // 1. 시큐리티 필터체인 메소드 -> 인자로 http 파라미터로 받음
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	
				//csrf disable -> 세션방식은 필수적으로 방어를 해야하지만 crsf 공격 방어 X
        http
                .csrf((auth) -> auth.disable());

				//From 로그인 방식 disable -> JWT 방식이라 disable
        http
                .formLogin((auth) -> auth.disable());

				//http basic 인증 방식 disable -> JWT 방식이라 disable
        http
                .httpBasic((auth) -> auth.disable());

				//경로별 인가 작업
        http
		        .csrf(csrf -> csrf.disable()) // 람다 스타일로 CSRF 비활성화
		        .cors(cors -> cors.configurationSource(new CorsConfig().corsConfigurationSource())) // CORS 설정
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/login", "/", "/api/join","/api/verify").permitAll() // 이에 해당하는 권한 허용
                        .requestMatchers("/api/loa/**").permitAll() // 이에 해당하는 권한 허용
						.requestMatchers("/admin").hasRole("ADMIN") // 이에 해당하는 어드민 권한 허용
                        .anyRequest().authenticated()); // 나머지는 로그인 사용자만 접근
        
		//8. JWTFilter 등록
        http
                .addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class);
        
		// 3. AuthenticationManager()와 JWTUtil 인수 전달
        http
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil), UsernamePasswordAuthenticationFilter.class);
        
				//세션 설정 -> JWT 사용 시 STATELESS 로 반드시 설정
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        


        return http.build();
    }

}
