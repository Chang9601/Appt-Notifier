package com.csup96.appt_notifier.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.csup96.appt_notifier.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	User findByUsername(String username);
}