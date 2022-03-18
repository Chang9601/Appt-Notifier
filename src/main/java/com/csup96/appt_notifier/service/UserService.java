package com.csup96.appt_notifier.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csup96.appt_notifier.model.RoleType;
import com.csup96.appt_notifier.model.User;
import com.csup96.appt_notifier.repository.UserRepository;

@Transactional
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	// 관리자 저장
	public void save(User user) {
		String rawPassword  = user.getPassword();
		String encPassword = encoder.encode(rawPassword);
		
		user.setRole(RoleType.ADMIN); // 역할 설정
		user.setPassword(encPassword); // 해시 비밀번호
		userRepository.save(user);
	}
	
	// 관리자 찾기
	public User findById(int id) {
		return userRepository.findById(id).orElse(null); // 없을 경우 null 반환
	}
	
	// 관리자 갱신
	public void updateById(User user) {
		User persistence = userRepository.findById(user.getId()).orElse(null);
		String rawPassword = user.getPassword();
		String encPassword = encoder.encode(rawPassword);
		
		persistence.setPassword(encPassword);
	}
}