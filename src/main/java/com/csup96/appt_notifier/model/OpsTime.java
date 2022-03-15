package com.csup96.appt_notifier.model;

import javax.persistence.Column;
import javax.persistence.Entity;
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
public class OpsTime {

	@Id // 기본 키
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 생성을 DB에 위임
	private int id; 
	
	@Column(nullable = false, length = 50)
	private String openTime; // 여는 시간 
	
	@Column(nullable = false, length = 50)
	private String closeTime; // 닫는 시간
	
	@Column(nullable = false, length = 50)
	private String startBreakTime; // 휴식 시작
	
	@Column(nullable = false, length = 50)
	private String endBreakTime; // 휴식 끝
	
	private int apptInterval; // interval가 MySQL 예약어라서 오류 발생!
}