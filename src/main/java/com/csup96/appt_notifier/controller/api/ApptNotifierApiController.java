package com.csup96.appt_notifier.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csup96.appt_notifier.dto.ResponseDTO;
import com.csup96.appt_notifier.model.Appointment;
import com.csup96.appt_notifier.service.ApptService;

@RequestMapping("/api/appt/*")
@RestController
public class ApptNotifierApiController {

	@Autowired
	private ApptService apptService;
	
	@PostMapping("/save")
	public ResponseDTO<Integer> save(@RequestBody Appointment appointment) {
		apptService.save(appointment);
		
		return new ResponseDTO<Integer>(HttpStatus.OK.value(), 0);
	}
	
	@GetMapping("/find")
	public ResponseDTO<Appointment> find(String clientName, String clientPhone) {
		Appointment ret = apptService.find(clientName, clientPhone);
		
		return new ResponseDTO<Appointment>(HttpStatus.OK.value(), ret);
	}	
	
	@DeleteMapping("/delete")
	public ResponseDTO<Integer> delete(String clientName, String clientPhone) {
		apptService.delete(clientName, clientPhone);
		
		return new ResponseDTO<Integer>(HttpStatus.OK.value(), 0);
	}		
}