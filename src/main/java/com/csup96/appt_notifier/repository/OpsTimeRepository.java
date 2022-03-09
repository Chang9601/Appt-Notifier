package com.csup96.appt_notifier.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.csup96.appt_notifier.model.OpsTime;

public interface OpsTimeRepository extends JpaRepository<OpsTime, Integer> {

}
