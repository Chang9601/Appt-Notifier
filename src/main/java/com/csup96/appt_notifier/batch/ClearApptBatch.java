package com.csup96.appt_notifier.batch;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.csup96.appt_notifier.service.ApptService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component // Spring Container에 해당 클래스 Bean으로 등록
public class ClearApptBatch {
	
	@Autowired
	private final ApptService apptService;
	
	private final Date today = new Date(new java.util.Date().getTime()); // 오늘 날짜
	
	// 초 분 시 일 월 주
	// 하루가 지나면 이전 예약 모두 자동 삭제 0 0 0 * * * 
	@Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
	public void clearApptDB() {
		apptService.deletebyApptDateBefore(today);
	}
}