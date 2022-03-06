package com.csup96.appt_notifier.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csup96.appt_notifier.repository.UserRepository;

@Transactional
@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	
}