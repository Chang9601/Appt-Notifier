package com.csup96.appt_notifier.api;

import lombok.Data;

//SMS 응답 바디를 표현하는 클래스
@Data
public class SmsResponseDto {
	public String requestId;
	public String requestTime;
	public String statusCode;
	public String statusName;
}