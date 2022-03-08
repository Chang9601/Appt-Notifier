package com.csup96.appt_notifier.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csup96.appt_notifier.model.RoleType;
import com.csup96.appt_notifier.model.User;
import com.csup96.appt_notifier.repository.UserRepository;

@Transactional
@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	// 설정하기
	public void save(User user) {
		user.setRole(RoleType.ADMIN);
		userRepository.save(user);
	}
	
	// 찾기
	public User findById(int id) {
		return userRepository.findById(id).orElse(null); // 없을 경우 null 반환
	}
	
	// 업데이트
	public void update(User user) {
		User persistence = userRepository.findById(user.getId()).orElse(null);
		persistence.setPassword(user.getPassword());
	}
}