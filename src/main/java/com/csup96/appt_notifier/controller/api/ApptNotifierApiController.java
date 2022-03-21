package com.csup96.appt_notifier.controller.api;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

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
import org.springframework.web.client.RestClientException;

import com.csup96.appt_notifier.dto.ResponseDto;
import com.csup96.appt_notifier.model.Appointment;
import com.csup96.appt_notifier.service.ApptService;

@RequestMapping("/appt/*")
@RestController
public class ApptNotifierApiController {

	@Autowired
	private ApptService apptService;
	
	@PostMapping("/save")
	public ResponseDto<Integer> save(@RequestBody Appointment appointment) throws InvalidKeyException, RestClientException, UnsupportedEncodingException, NoSuchAlgorithmException, URISyntaxException {
		apptService.save(appointment);
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 0);
	}
	
	@GetMapping("/find-all-by-name")
	public ResponseDto<Page<Appointment>> findByNameAndPhone(String clientName, String clientPhone) {
		Page<Appointment> ret = apptService.findByClientNameAndClientPhone(clientName, clientPhone, null); // Pageable 없으니까 null
		ret = ret.isEmpty() ? null : ret;
		
		return new ResponseDto<Page<Appointment>>(HttpStatus.OK.value(), ret);
	}	
	
	@GetMapping("/find-by-date")
	public ResponseDto<Appointment> findByDateAndTime(String apptDate, String apptTime) {
		Appointment ret = apptService.findByApptDateAndApptTime(apptDate, apptTime);
		
		return new ResponseDto<Appointment>(HttpStatus.OK.value(), ret);
	}	
	
	@DeleteMapping("/delete/{id}")
	public ResponseDto<Integer> deleteById(@PathVariable int id) throws InvalidKeyException, RestClientException, UnsupportedEncodingException, NoSuchAlgorithmException, URISyntaxException {
		apptService.deleteById(id);
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 0);
	}		
	
	@PutMapping("/update/{id}")
	public ResponseDto<Integer> updateById(@PathVariable int id, @RequestBody Appointment appointment) throws InvalidKeyException, RestClientException, UnsupportedEncodingException, NoSuchAlgorithmException, URISyntaxException {
		apptService.updateById(id, appointment);
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 0);
	}		
}