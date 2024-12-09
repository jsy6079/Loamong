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
	
    //AuthenticationManager가 인자로 받을 AuthenticationConfiguraion 객체 생성자 주입
    private final AuthenticationConfiguration authenticationConfiguration;
    
    //JWTUtil 주입
  	private final JWTUtil jwtUtil;

    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, JWTUtil jwtUtil) {

        this.authenticationConfiguration = authenticationConfiguration;
				this.jwtUtil = jwtUtil;
    }

    //AuthenticationManager Bean 등록
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }
    
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	


				//csrf disable
        http
                .csrf((auth) -> auth.disable());

				//From 로그인 방식 disable
        http
                .formLogin((auth) -> auth.disable());

				//http basic 인증 방식 disable
        http
                .httpBasic((auth) -> auth.disable());

				//경로별 인가 작업
        http
		        .csrf(csrf -> csrf.disable()) // 람다 스타일로 CSRF 비활성화
		        .cors(cors -> cors.configurationSource(new CorsConfig().corsConfigurationSource())) // CORS 설정
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/login", "/", "/api/join").permitAll()
                        .requestMatchers("/api/loa/**").permitAll()
						.requestMatchers("/admin").hasRole("ADMIN")
                        .anyRequest().authenticated());
        
		//JWTFilter 등록
        http
                .addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class);
        
		//AuthenticationManager()와 JWTUtil 인수 전달
        http
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil), UsernamePasswordAuthenticationFilter.class);
        
				//세션 설정
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        


        return http.build();
    }

}
