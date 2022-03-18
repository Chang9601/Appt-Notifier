package com.csup96.appt_notifier.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.csup96.appt_notifier.config.auth.PrincipalDetailsService;

@Configuration // Spring Container에 Bean으로 등록
@EnableWebSecurity // 시큐리티 필터 동록
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소로 접근하면 권한 및 인증 미리 확인
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private PrincipalDetailsService principalDetailsService;
	
	@Bean // Spring Container에 Bean으로 등록
	public BCryptPasswordEncoder encodePassword() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(principalDetailsService).passwordEncoder(encodePassword());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable() // csrf 토근 비활성화 (테스트 시 사용)
			.authorizeRequests()
				.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')") // 관리자 권한이 있어야만 접근 가능
				.anyRequest() // 나머지는 모두 허용
				.permitAll()
			.and()
				.formLogin()  
				.loginPage("/")// 기본 페이지
				.loginProcessingUrl("/login") // Spring Security가 해당 주소의 로그인 요청을 가로채서 대신 로그인
				.defaultSuccessUrl("/");
	}
}