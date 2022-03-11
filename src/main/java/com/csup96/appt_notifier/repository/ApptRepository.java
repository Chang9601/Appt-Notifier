package com.csup96.appt_notifier.repository;


import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.csup96.appt_notifier.model.Appointment;

// DAO, 자동으로 Bean 등록, @Repository 생략 가능
public interface ApptRepository extends JpaRepository<Appointment, Integer> {
	// 이름과 전화번호로 검색, TO-DO: 2개 이상일 경우 List 필요
	@Query(value = "SELECT * FROM appointment WHERE client_name = :name AND client_phone = :phone", nativeQuery = true)
	Appointment findByNameAndPhone(@Param("name") String name, @Param("phone") String phone);
	
	// 예약 취소
	@Modifying 	// executeUpdate 실행
	@Transactional
	@Query(value = "DELETE FROM appointment WHERE client_name = :name AND client_phone = :phone", nativeQuery = true)
	void deleteByNameAndPhone(@Param("name") String name, @Param("phone") String phone);

	// 날짜와 시간으로 검색, 1개만 검색
	@Query(value = "SELECT * FROM appointment WHERE appt_date = :date AND appt_time = :time", nativeQuery = true)
	List<Appointment> findAllByDateAndTime(@Param("date") Date date, @Param("time") String time);
}