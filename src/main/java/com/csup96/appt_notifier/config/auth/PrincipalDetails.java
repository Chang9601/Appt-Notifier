package com.csup96.appt_notifier.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.csup96.appt_notifier.model.User;

// UserDetails 타입의 객체를 Spring Security의 세션에 저장
// Security Session => Authentication 객체 => UserDetails 객체
public class PrincipalDetails implements UserDetails {
	private User user;

	public PrincipalDetails(User user) {
		this.user = user;
	}
	
	// User의 권한 반환
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collector = new ArrayList<>();
		collector.add(() -> {
			return "ROLE_" + user.getRole(); // 규칙
		});
		
		return collector;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	} 
}
