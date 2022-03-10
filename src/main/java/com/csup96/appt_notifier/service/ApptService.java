package com.csup96.appt_notifier.service;

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
		apptRepository.save(appointment);
	}
	
	// 예약명단 - 페이지 적용
	public Page<Appointment> listPage(Pageable pageable) { // Page count 쿼리 사용
		return apptRepository.findAll(pageable);
	}
	
	public List<Appointment> list() {
		return apptRepository.findAll();
	}
	
	// 예약명단 - 날짜
	public List<String> listDate() {
		return apptRepository.findAllByDate();
	}
	
	// 예약명단 - 시간
	public List<String> listTime() {
		return apptRepository.findAllByTime();
	}
	
	// 예약찾기
	public Appointment find(String clientName, String clientPhone) {
		return apptRepository.findByNameAndPhone(clientName, clientPhone);
	}
	
	// 예약취소
	public void delete(String clientName, String clientPhone) {
		apptRepository.deleteByNameAndPhone(clientName, clientPhone);
	}
}