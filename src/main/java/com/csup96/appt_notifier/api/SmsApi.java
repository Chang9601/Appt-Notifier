package com.csup96.appt_notifier.api;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

@Component // Spring Container에 해당 클래스 Bean으로 등록
public class SmsApi {
	
	private String myServiceId = "";
	private String myAccessKey = ""; // API Access Key Id
	private String mySecretKey = ""; // API Secret Key
	
	// SMS를 전달하는 메서드
	public SmsResponseDto sendSmsMsg(String to, String content) throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, RestClientException, URISyntaxException {
		Long time = new Date().getTime(); // 요청 헤더의 x-ncp-apigw-timestamp의 값
		List<Message> messages = new ArrayList<>();
		
		messages.add(new Message(to, content));
		
		SmsRequestDto reqBody = new SmsRequestDto();
		reqBody.setType("SMS");
		reqBody.setContentType("COMM");
		reqBody.setCountryCode("82");
		reqBody.setFrom("");
		reqBody.setContent("1번째 문자");
		reqBody.setMessages(messages);
		
		Gson gson = new Gson();
		String reqBodyJson = gson.toJson(reqBody); // 요청 바디를 JSON으로 변환
		
		HttpHeaders headers = new HttpHeaders(); // 요청 헤더 설정
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("x-ncp-apigw-timestamp", time.toString());
		headers.set("x-ncp-iam-access-key", myAccessKey);
		
		String signature = makeSignature(time); // signature 얻기
		headers.set("x-ncp-apigw-signature-v2", signature);
		
		HttpEntity<String> req = new HttpEntity<>(reqBodyJson, headers); // 요청 객체 
		
		System.out.println("요청 바디: " + reqBodyJson);
		
		RestTemplate restTemplate = new RestTemplate();
		SmsResponseDto resp = restTemplate.postForObject(new URI("https://sens.apigw.ntruss.com/sms/v2/services/" + myServiceId + "/messages"), req, SmsResponseDto.class); // POST 요청, 응답

		System.out.println("응답 상태: " + resp.getStatusCode());
		
		return resp;
	}
	
	// 헤더의 x-ncp-apigw-signature-v2의 값을 설정하는 signature 생성
	private String makeSignature(Long time) throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException {
		String space = " ";	 // one space
		String newLine = "\n"; // new line
		String method = "POST";	// method
		String url = "/sms/v2/services/" + myServiceId + "/messages";	// url (include query string)
		String timestamp = time.toString();	// current timestamp (epoch)
		String accessKey = myAccessKey;		// access key id (from portal or Sub Account)
		String secretKey = mySecretKey;

		String message = new StringBuilder()
			.append(method)
			.append(space)
			.append(url)
			.append(newLine)
			.append(timestamp)
			.append(newLine)
			.append(accessKey)
			.toString();

		SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
		Mac mac = Mac.getInstance("HmacSHA256");
		mac.init(signingKey);

		byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
		String encodeBase64String = Base64.encodeBase64String(rawHmac);

	  return encodeBase64String;
	}
}