package com.csup96.appt_notifier.api;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

//SMS 요청 바디의 메시지를 표현하는 클래스
@Data
@AllArgsConstructor
class Message {
	public String to;
	public String content;
}

//SMS 요청 바디를 표현하는 클래스
@Data
public class SmsRequestDto {

	public String type;
	public String contentType;
	public String countryCode;
	public String from;
	public String content;
	public List<Message> messages = null;
}