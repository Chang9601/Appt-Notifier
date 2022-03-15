package com.csup96.appt_notifier.repository;


import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.csup96.appt_notifier.model.Appointment;

// DAO, 자동으로 Bean 등록, @Repository 생략 가능
public interface ApptRepository extends JpaRepository<Appointment, Integer> {
	// 이름과 전화번호로 검색
	@Query(
		value = "SELECT * FROM appointment  WHERE client_name = :client_name AND client_phone = :client_phone", 
		countQuery = "SELECT count(*) FROM appintment WHERE client_name = :client_name AND client_phone = :client_phone", // native query의 경우 count 직접 추가
		nativeQuery = true
	)
	Page<Appointment> findByClientNameAndClientPhone(@Param("client_name") String clintName, @Param("client_phone") String clientPhone, Pageable pageable);
	
	// TO-DO: 오류 원인 파악
	//Page<Appointment> findByClientNameAndClientPhone(String clientName, String clientPhone, Pageable pageable);
	
	/*
	// 예약 취소
	@Modifying 	// executeUpdate 실행
	@Transactional
	@Query(value = "DELETE FROM appointment WHERE client_name = :client_name AND client_phone = :client_phone", nativeQuery = true)
	void deleteByNameAndPhone(@Param("client_name") String clientName, @Param("client_phone") String clientPhone);
	*/
	
	// 날짜와 시간으로 검색, derived의 경우 이름은 필드와 동일
	List<Appointment> findByApptDateAndApptTime(Date apptDate, String apptTime);
}