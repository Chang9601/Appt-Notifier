package com.csup96.appt_notifier.service;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;

import com.csup96.appt_notifier.api.SmsApi;
import com.csup96.appt_notifier.model.Appointment;
import com.csup96.appt_notifier.repository.ApptRepository;

@Transactional
@Service
public class ApptService {

	@Autowired
	private ApptRepository apptRepository;
	
	@Autowired
	private SmsApi smsApi;
	
	// 예약하기 - SMS
	public void save(Appointment appointment) throws InvalidKeyException, RestClientException, UnsupportedEncodingException, NoSuchAlgorithmException, URISyntaxException {
		Date sqlDate = new Date(appointment.getApptDate().getTime());
		
		System.out.println(appointment.getApptDate());
		System.out.println(appointment.getApptTime());
		System.out.println(appointment.getClientName());
		System.out.println(appointment.getClientPhone());

		// 중복확인
		validateDuplicate(sqlDate, appointment.getApptTime());
		// SMS 전송
		apptRepository.save(appointment);
		System.out.println("번호: " + smsNumber(appointment.getClientPhone()));
		System.out.println("내용: " + smsContent(appointment, " 예약이 완료되었습니다. "));
		//smsApi.sendSmsMsg(smsNumber(appointment.getClientPhone()), smsContent(appointment,  " 예약이 완료되었습니다. "));
	}
	
	// 예약명단 - 페이지 적용
	public Page<Appointment> findAllByPage(Pageable pageable) { // Page count 쿼리 사용
		return apptRepository.findAll(pageable);
	}
	
	// 예약명단 - 페이지 적용 X
	public List<Appointment> findAll() {
		return apptRepository.findAll();
	}
	
	// 예약찾기 - 이름과 전화번호
	public Page<Appointment> findByNameAndPhone(String clientName, String clientPhone, Pageable pageable) { // Page count 쿼리 사용 X
		return apptRepository.findByClientNameAndClientPhone(clientName, clientPhone, pageable);//apptRepository.findAllByNameAndPhone(clientName, clientPhone, pageable);
	}
	
	// 예약찾기 - 날짜와 시간
	public Appointment findByDateAndTime(String apptDate, String apptTime) {
		Date sqlDate = Date.valueOf(apptDate);
		List<Appointment> list = apptRepository.findByApptDateAndApptTime(sqlDate, apptTime);
		
		return !list.isEmpty() ? list.get(0) : null; // 빈 리스트 null 처리
	}
	
	// 예약취소 - SMS
	public void deleteById(int id) throws InvalidKeyException, RestClientException, UnsupportedEncodingException, NoSuchAlgorithmException, URISyntaxException {
		Appointment persistence = apptRepository.findById(id).orElse(null);
		System.out.println("내용: " + smsContent(persistence, " 예약이 취소되었습니다. "));		
		
		apptRepository.deleteById(id);
		//smsApi.sendSmsMsg(smsNumber(persistence.getClientPhone()), smsContent(persistence,  " 예약이 취소되었습니다. "));
	}
	
	// 예약변경 - SMS
	public void updateById(int id, Appointment appointment) throws InvalidKeyException, RestClientException, UnsupportedEncodingException, NoSuchAlgorithmException, URISyntaxException {
		Appointment persistence = apptRepository.findById(id).orElse(null);

		persistence.setApptDate(appointment.getApptDate());
		persistence.setApptTime(appointment.getApptTime());
		
		System.out.println("내용: " + smsContent(persistence, "로 예약이 변경되었습니다. "));		
		
		//smsApi.sendSmsMsg(smsNumber(persistence.getClientPhone()), smsContent(persistence,  "로 예약이 변경되었습니다. "));
	}
	
	// 이전 예약 모두 삭제
	public void deletebyApptDateBefore(Date today) {
		apptRepository.deleteByApptDateBefore(today);
	}
 	
	
	// 중복 확인, 내부 함수라서 private 지시어
	private void validateDuplicate(Date apptDate, String apptTime) {
		List<Appointment> list = apptRepository.findByApptDateAndApptTime(apptDate, apptTime);
		if(!list.isEmpty())
			throw new IllegalArgumentException("이미 예약이 되었습니다.");
	}
	
	// SMS 수신번호
	private String smsNumber(String clientPhone) {
		String[] nums = clientPhone.split("-");
		
		StringBuffer buf = new StringBuffer()
				.append(nums[0])
				.append(nums[1])
				.append(nums[2]);
		
		return buf.toString();
	}
	
	// SMS 수신내용
	private String smsContent(Appointment appointment, String status) {
		String clientName = appointment.getClientName();
		java.util.Date apptDate = appointment.getApptDate();
		String apptTime = appointment.getApptTime();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일");
		String formattedDate = sdf.format(apptDate);
		
		StringBuffer buf = new StringBuffer()
				.append(clientName + "님 ")
				.append(formattedDate + " ")
				.append(apptTime + "시" + status)
				.append("https://www.youtube.com/를 통해서 예약 변경 및 취소를 하실 수 있습니다.");
		 
		return buf.toString();
	}
}