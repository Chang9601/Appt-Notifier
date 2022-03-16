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
import com.csup96.appt_notifier.model.OpsTime;
import com.csup96.appt_notifier.service.OpsTimeService;

@RequestMapping("/api/ops-time/*")
@RestController
public class OpsTimeApiController {
	
	@Autowired
	private OpsTimeService opsTimeService;
	
	// 영업시간은 1개, 따라서 고정
	private final int id = 1;	
	
	@PostMapping("/save")
	public ResponseDTO<Integer> save(@RequestBody OpsTime opsTime) {
		opsTimeService.save(opsTime);
		return new ResponseDTO<Integer>(HttpStatus.OK.value(), 0);
	}
	
	@GetMapping("/find")
	public ResponseDTO<OpsTime> findById(OpsTime opsTime) {
		OpsTime persistence = opsTimeService.findById(id);
		return new ResponseDTO<OpsTime>(HttpStatus.OK.value(), persistence);
	}
	
	@PutMapping("/update")
	public ResponseDTO<Integer> updateById(@RequestBody OpsTime opsTime) {
		opsTime.setId(id);
		opsTimeService.update(opsTime);
		return new ResponseDTO<Integer>(HttpStatus.OK.value(), 0);
	}
}