package com.loamong.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class FreeBoardEntity {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int num;
    
    private String title;
    private String content;
    private String writer;

}
