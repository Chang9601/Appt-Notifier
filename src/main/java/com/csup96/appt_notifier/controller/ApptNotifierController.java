package com.csup96.appt_notifier.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
	
	private final int id = 1;
	
	@GetMapping("/index")
	public String index(Model model) {		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create(); // 날짜 형식 설정
		String json = gson.toJson(apptService.list()); // 예약시간, Java 객체 <-> JSON <-> JavaScript 객체
		
		model.addAttribute("ops-time", opsTimeService.findById(id)); // 영업 시간
		model.addAttribute("appts", json); // 예약 시간
		
		return "index"; // templates/index.mustache 이동
	}
	
	@GetMapping("/appt")
	public String appts(Model model, @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
		model.addAttribute("appts", apptService.listPage(pageable));
		model.addAttribute("prev", pageable.previousOrFirst().getPageNumber());
	    model.addAttribute("next", pageable.next().getPageNumber());
	    
		return "appt";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
}