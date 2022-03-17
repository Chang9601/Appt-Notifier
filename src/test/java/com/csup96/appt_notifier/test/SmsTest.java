package com.csup96.appt_notifier.test;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestClientException;

import com.csup96.appt_notifier.api.SmsApi;

@SpringBootTest
public class SmsTest {

	@Autowired
	private SmsApi smsApi;
	
	@Test
	public void testSms() throws InvalidKeyException, RestClientException, UnsupportedEncodingException, NoSuchAlgorithmException, URISyntaxException {
		smsApi.sendSmsMsg("", "");
	}
}