package com.csup96.appt_notifier.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = {@UniqueConstraint( // 중복 방지
		name = "DATE_TIME_UNIQUE",
		columnNames = {"apptDate", "apptTime"} // 자바 필드 이름
	)
})
public class Appointment {
	
	@Id // 기본 키
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 생성을 DB에 위임
	private int id; 
	
	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date apptDate; // 예약 날짜
	
	// @Temporal(TemporalType.TIME) -> 시, 분만 필요하기 때문에 문자열 사용
	@Column(nullable = false)
	private String apptTime; // 예약 시간
	
	@Column(nullable = false, length = 50)
	private String clientName; // 이름
	
	@Column(nullable = false, length = 20)	
	private String clientPhone; // 전화변호
}