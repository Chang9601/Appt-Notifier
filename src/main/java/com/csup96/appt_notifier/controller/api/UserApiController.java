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

@RequestMapping("/api/user/*")
@RestController
public class UserApiController {
	
	@Autowired
	private UserService userService;
	
	// 관리자는 1개, 따라서 고정
	private final int id = 1;

	@PostMapping("/save")
	public ResponseDTO<Integer> save(@RequestBody User user) {
		userService.save(user);
		return new ResponseDTO<Integer>(HttpStatus.OK.value(), 0);
	}
	
	@GetMapping("/find")
	public ResponseDTO<User> findById() {
		User persistence = userService.findById(id);
		return new ResponseDTO<User>(HttpStatus.OK.value(), persistence);
	}
	
	@PutMapping("/update")
	public ResponseDTO<Integer> updateById(@RequestBody User user) {
		user.setId(id); // 아이디 설정
		userService.updateById(user);
		return new ResponseDTO<Integer>(HttpStatus.OK.value(), 0);
	}
}