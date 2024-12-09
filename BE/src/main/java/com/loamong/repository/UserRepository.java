package com.loamong.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.loamong.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
	
	//username을 받아 DB 테이블에서 회원을 조회하는 메서드 작성
	UserEntity findByUsername(String username);

}
