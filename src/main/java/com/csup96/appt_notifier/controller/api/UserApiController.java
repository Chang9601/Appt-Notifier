package com.csup96.appt_notifier.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
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

	@PostMapping("/save-user")
	public ResponseDTO<Integer> save(@RequestBody User user) {
		userService.save(user);
		return new ResponseDTO<Integer>(HttpStatus.OK, 0);
	}
}