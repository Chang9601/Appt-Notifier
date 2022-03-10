package com.csup96.appt_notifier.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.csup96.appt_notifier.model.Appointment;

// DAO, 자동으로 Bean 등록, @Repository 생략 가능
public interface ApptRepository extends JpaRepository<Appointment, Integer> {
	@Query(value = "SELECT * FROM appointment WHERE client_name = :name AND client_phone = :phone", nativeQuery = true)
	Appointment findByNameAndPhone(@Param("name") String name, @Param("phone") String phone);
	
	@Modifying 	// executeUpdate 실행
	@Transactional
	@Query(value = "DELETE FROM appointment WHERE client_name = :name AND client_phone = :phone", nativeQuery = true)
	void deleteByNameAndPhone(@Param("name") String name, @Param("phone") String phone);
	
	@Query(value = "SELECT appt_date FROM appointment", nativeQuery = true)
	List<String> findAllByDate();
	
	@Query(value = "SELECT appt_time FROM appointment", nativeQuery = true)
	List<String> findAllByTime();
}