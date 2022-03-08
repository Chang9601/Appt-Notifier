package com.csup96.appt_notifier.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csup96.appt_notifier.dto.ResponseDTO;
import com.csup96.appt_notifier.model.User;
import com.csup96.appt_notifier.service.UserService;

@RequestMapping("/api/admin/*")
@RestController
public class UserApiController {
	
	@Autowired
	private UserService userService;
	
	// 관리자는 1개, 따라서 고정
	private final int id = 1;

	// 관리자 저장
	@PostMapping("/save-user")
	public ResponseDTO<Integer> save(@RequestBody User user) {
		userService.save(user);
		return new ResponseDTO<Integer>(HttpStatus.OK, 0);
	}
	
	// 관리자 찾기
	@GetMapping("/find-user")
	public ResponseDTO<User> find(User user) {
		User persistence = userService.findById(id);
		return new ResponseDTO<User>(HttpStatus.OK, persistence);
	}
	
	// 관리자 갱신
	@PutMapping("/update-user")
	public ResponseDTO<Integer> update(@RequestBody User user) {
		user.setId(id); // 아이디 설정
		userService.update(user);
		return new ResponseDTO<Integer>(HttpStatus.OK, 0);
	}
}