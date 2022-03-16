package com.csup96.appt_notifier.service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csup96.appt_notifier.model.Appointment;
import com.csup96.appt_notifier.repository.ApptRepository;

@Transactional
@Service
public class ApptService {

	@Autowired
	ApptRepository apptRepository;
	
	// 예약하기
	public void save(Appointment appointment) {
		Date sqlDate = new Date(appointment.getApptDate().getTime());
		// 중복확인
		validateDuplicate(sqlDate, appointment.getApptTime());
		apptRepository.save(appointment);
	}
	
	// 예약명단 - 페이지 적용
	public Page<Appointment> findAllByPage(Pageable pageable) { // Page count 쿼리 사용
		return apptRepository.findAll(pageable);
	}
	
	// 예약명단 - 페이지 적용 X
	public List<Appointment> findAll() {
		return apptRepository.findAll();
	}
	
	// 예약찾기 - 이름과 전화번호, 2개 이상 가능
	public Page<Appointment> findByNameAndPhone(String clientName, String clientPhone, Pageable pageable) { // Page count 쿼리 사용 X
		return apptRepository.findByClientNameAndClientPhone(clientName, clientPhone, pageable);//apptRepository.findAllByNameAndPhone(clientName, clientPhone, pageable);
	}
	
	// 예약찾기 - 날짜와 시간
	public Appointment findByDateAndTime(String apptDate, String apptTime) {
		Date sqlDate = Date.valueOf(apptDate);
		List<Appointment> list = apptRepository.findByApptDateAndApptTime(sqlDate, apptTime);
		
		return !list.isEmpty() ? list.get(0) : null; // 빈 리스트 null 처리
	}
	
	// 예약취소
	public void deleteById(int id) {
		apptRepository.deleteById(id);
	}
	
	// 예약변경
	public void updateById(int id, Appointment appointment) {
		Appointment persistence = apptRepository.findById(id).orElse(null);
		
		persistence.setApptDate(appointment.getApptDate());
		persistence.setApptTime(appointment.getApptTime());
	}
	
	public void deletebyApptDateBefore(Date today) {
		apptRepository.deleteByApptDateBefore(today);
	}
 	
	// 중복 확인, 내부 함수라서 private 지시어
	private void validateDuplicate(Date apptDate, String apptTime) {
		List<Appointment> list = apptRepository.findByApptDateAndApptTime(apptDate, apptTime);
		if(!list.isEmpty())
			throw new IllegalArgumentException("이미 예약이 되었습니다.");
	}
}