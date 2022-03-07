package com.csup96.appt_notifier.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
	
	@Id // 기본 키
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 생성을 DB에 위임
	private int id; 

	private int interval; // 예약 단위 -> 오류
	
	@Column(nullable = false, length = 100)
	private String username; // 이름
	
	@Column(nullable = false, length = 100)	
	private String password; // 비밀번호
	
	@Enumerated(EnumType.STRING)
	private RoleType role; // 관리자(ADMIN)
	
	@Column(nullable = false, length = 50)
	private String openTime; // 여는 시간 
	
	@Column(nullable = false, length = 50)
	private String closeTime; // 닫는 시간
}