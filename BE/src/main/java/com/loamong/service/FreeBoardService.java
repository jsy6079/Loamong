package com.loamong.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.loamong.entity.FreeBoardEntity;
import com.loamong.repository.FreeBoardRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class FreeBoardService {
	
	private final FreeBoardRepository fbe;
	
	// 글 전체 조회
	
	public List<FreeBoardEntity> getallfreeboard() {
		return fbe.findAll();
	}

	// 글 상세조회
	public FreeBoardEntity getfreeboard(int num) {
		FreeBoardEntity data = fbe.findById(num).orElse(null);
		return data;
	}

	
	// 글 작성
	
	// 글 수정
	
	// 글 삭제

}
