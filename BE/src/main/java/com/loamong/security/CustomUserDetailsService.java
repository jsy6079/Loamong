package com.loamong.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.loamong.entity.UserEntity;
import com.loamong.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository ur;
    
    
    // 객체 초기화를 위한 생성자 생성
    public CustomUserDetailsService(UserRepository userRepository) {
        this.ur = userRepository;
    }
    
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				
		//DB에서 조회
        UserEntity userData = ur.findByUsername(username);

        if (userData != null) {
						
			//UserDetails에 담아서 return하면 AutneticationManager가 검증 함

            return new CustomUserDetails(userData);
        }
        System.out.println("일치하는 유저정보가 없습니다 " + username);
        throw new UsernameNotFoundException("User not found with username: " + username);
    
    }
}
