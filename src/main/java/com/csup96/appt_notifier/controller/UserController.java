package com.csup96.appt_notifier.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
	
	/*
	 * 운영 시간: open - close
	 * 예약 단위: 10, 20, 30, 40, 50, 60
	 * 아이디: admin 고정, 비밀번호: 수정 가능
	 * 
	 * */
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/admin")
	public String admin() {
		return "admin";
	}
}