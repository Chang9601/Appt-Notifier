package com.csup96.appt_notifier.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.csup96.appt_notifier.model.User;
import com.csup96.appt_notifier.repository.UserRepository;

// loginProcessingUrl("/login")
// 로그인 요청이 오면 자동으로 UserDetailsService 타입으로 IoC 등록되어 있는 loadUserByUsername 함수 실행
@Service
public class PrincipalDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	// Security Session(Authentication(UserDetails))
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User principal = userRepository.findByUsername(username);
		System.out.println("User: " + principal);
		
		return principal != null ? new PrincipalDetails(principal) : null;
	}
	
}