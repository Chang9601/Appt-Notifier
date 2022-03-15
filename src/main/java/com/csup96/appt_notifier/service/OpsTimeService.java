package com.csup96.appt_notifier.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csup96.appt_notifier.model.OpsTime;
import com.csup96.appt_notifier.repository.OpsTimeRepository;

@Transactional
@Service
public class OpsTimeService {

	@Autowired
	private OpsTimeRepository opsTimeRepository;
	
	// 영업시간 저장
	public void save(OpsTime opsTime) {
		opsTimeRepository.save(opsTime);
	}
	
	// 영업시간 찾기
	public OpsTime findById(int id) {
		return opsTimeRepository.findById(id).orElse(null);
	}
	
	// 영업시간 갱신
	public void update(OpsTime opsTime) {
		OpsTime persistence = opsTimeRepository.findById(opsTime.getId()).orElse(null);
		persistence.setOpenTime(opsTime.getOpenTime());
		persistence.setCloseTime(opsTime.getCloseTime());
		persistence.setStartBreakTime(opsTime.getStartBreakTime());
		persistence.setEndBreakTime(opsTime.getEndBreakTime());		
		persistence.setApptInterval(opsTime.getApptInterval());
	}
}