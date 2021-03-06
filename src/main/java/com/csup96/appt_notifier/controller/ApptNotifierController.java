package com.csup96.appt_notifier.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.csup96.appt_notifier.model.Appointment;
import com.csup96.appt_notifier.service.ApptService;
import com.csup96.appt_notifier.service.OpsTimeService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
public class ApptNotifierController {
	
	@Autowired
	private ApptService apptService;
	
	@Autowired
	private OpsTimeService opsTimeService;
	
	// 영업시간은 1개, 따라서 고정
	private final int id = 1;
	
	// 예약표
	@GetMapping({"/index", "/", ""})
	public String index(Model model) {		
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create(); // 날짜 형식 설정
		String json = gson.toJson(apptService.findAll()); // 예약시간, Java 객체 <-> JSON <-> JavaScript 객체
		
		model.addAttribute("ops-time", opsTimeService.findById(id)); // 영업 시간
		model.addAttribute("appts", json); // 예약 시간
						// mustache 기본 폴더: src/main/resources/
		return "index"; // src/main/resources/templates/index.mustache 이동
	}
	
	// 예약명단
	@GetMapping("/admin/appt")
	public String appts(Model model, @PageableDefault(size = 5) 
		@SortDefault.SortDefaults({
			@SortDefault(sort = "apptDate", direction = Sort.Direction.ASC), // 날짜 기준 정렬, sort 자바 필드
			@SortDefault(sort = "apptTime", direction = Sort.Direction.ASC)  // 시간 기준 정렬, sort 자바 필드
		}) 
		Pageable pageable) {
		
		Page<Appointment> list = apptService.findAllByPage(pageable);
		
		model.addAttribute("appts", list);
		model.addAttribute("prev", pageable.previousOrFirst().getPageNumber());
	    model.addAttribute("next", pageable.next().getPageNumber());
	    model.addAttribute("is-prev", list.hasPrevious());
	    model.addAttribute("is-next", list.hasNext());
	    
		return "appt";
	}
	
	// 예약변경 및 예약취소 명단
	@GetMapping("/appt/update-delete")
	public String updateOrDelete(String clientName, String clientPhone, Model model, @PageableDefault(size = 5) 
	@SortDefault.SortDefaults({
		@SortDefault(sort = "appt_date", direction = Sort.Direction.ASC), // native Query 사용 시 DB 컬럼명
		@SortDefault(sort = "appt_time", direction = Sort.Direction.ASC)  // native Query 사용 시 DB 컬럼명
	}) 
	Pageable pageable) {
		Page<Appointment> list = apptService.findByClientNameAndClientPhone(clientName, clientPhone, pageable);
		
		model.addAttribute("appts", list);
		model.addAttribute("prev", pageable.previousOrFirst().getPageNumber());
		model.addAttribute("next", pageable.next().getPageNumber());
		model.addAttribute("is-prev", list.hasPrevious());
		model.addAttribute("is-next", list.hasNext());
		
		return "update_delete";
	}
	
	// 예약변경 예약표
	@GetMapping("/appt/update-index")
	public String updateIndex(Model model, int apptId, String clientName, String clientPhone) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create(); // 날짜 형식 설정
		String json = gson.toJson(apptService.findAll()); // 예약시간, Java 객체 <-> JSON <-> JavaScript 객체
		
		model.addAttribute("ops-time", opsTimeService.findById(id)); // 영업 시간
		model.addAttribute("appts", json); // 예약 시간
		model.addAttribute("appt-id", apptId); // 변경할 예약 아이디
		model.addAttribute("client-name", clientName); // 변경할 예약 이름
		model.addAttribute("client-phone", clientPhone); // 변경할 예약 번호
		
		return "update";
	}
}