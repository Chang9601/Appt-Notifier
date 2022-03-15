package com.csup96.appt_notifier.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	
	@GetMapping("/find-all-by-name")
	public ResponseDTO<Page<Appointment>> findByNameAndPhone(String clientName, String clientPhone) {
		Page<Appointment> ret = apptService.findByNameAndPhone(clientName, clientPhone, null); // Pageable 없으니까 null
		ret = ret.isEmpty() ? null : ret;
		
		return new ResponseDTO<Page<Appointment>>(HttpStatus.OK.value(), ret);
	}	
	
	@GetMapping("/find-by-date")
	public ResponseDTO<Appointment> findByDateAndTime(String apptDate, String apptTime) {
		Appointment ret = apptService.findByDateAndTime(apptDate, apptTime);
		
		return new ResponseDTO<Appointment>(HttpStatus.OK.value(), ret);
	}	
	
	@DeleteMapping("/delete/{id}")
	public ResponseDTO<Integer> deleteById(@PathVariable int id) {
		apptService.delete(id);
		
		return new ResponseDTO<Integer>(HttpStatus.OK.value(), 0);
	}		
	
	@PutMapping("/update/{id}")
	public ResponseDTO<Integer> updateById(@PathVariable int id, @RequestBody Appointment appointment) {
		apptService.update(id, appointment);
		
		return new ResponseDTO<Integer>(HttpStatus.OK.value(), 0);
	}		
}