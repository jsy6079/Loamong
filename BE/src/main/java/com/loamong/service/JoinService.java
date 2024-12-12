package com.loamong.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.loamong.dto.JoinDTO;
import com.loamong.entity.UserEntity;
import com.loamong.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JoinService {
	
	private final UserRepository ur;
	
	private final BCryptPasswordEncoder bpe;
	
	public void joinMember(JoinDTO joinDTO) {
		String username = joinDTO.getUsername();
		String password = joinDTO.getPassword();
		String nickname = joinDTO.getNickname();
		
		UserEntity data = new UserEntity();
		
		data.setUsername(username);
		data.setPassword(bpe.encode(password));
		data.setNickname(nickname);
		data.setRole("ROLE_ADMIN");
		
		ur.save(data);
	}
	
	
	public boolean existsByUsername(String username) {
		return ur.existsByUsername(username);
	}
	
	public boolean existsByNickname(String nickname) {
		return ur.existsByNickname(nickname);
	}

}
