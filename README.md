# 예약 사이트

## 소개
스프링 부트, 스프링 시큐리티, JPA와 AWS를 사용한 예약 사이트

## 목적
* 스프링 시큐리티의 작동원리를 이해하고 이를 통해서 권한에 따라서 접속 가능한 페이지를 제어한다.
* 자바 ORM 기술 표준인 JPA를 사용해서 객체와 테이블 간의 매핑원리를 이해하고 객체 중심 설계를 구현한다.
* AWS의 EC2를 사용해서 웹 사이트를 서비스가 가능하도록 실제로 배포한다.

## 개발 환경
* IDE
  * Spring Tool Suite 4.11.1.RELEASE
* Framework
  * Spring Boot 2.6.4
  * Spring Security
  * Bootstrap 5.1.3
* ORM
  * JPA
* Language
  * HTML5/CSS3
  * JavaScript
  * Java
* Template
  * Mustache
* OS
  * Windows 10 64-bit
* DB
  * MySQL DB
* Cloud
  * AWS

## 제작 기간
2022.03.06 - 2022.03.22

## 개발 인원
1명

## 기능

1. 예약표
   * 계점 시간, 폐점 시간, 예약 시간 간격에 맞추어서 예약 가능한 시간 표시
   * 휴식 시간, 휴무일, 이미 예약이 된 경우 예약 선택 불가능
   * 매일 자정에 DB에서 이전 예약 모두 자동 삭제
   * 오늘부터 1달 앞까지 날짜 선택 가능

2. 예약하기
   * 이름, 전화번호 유효성 검사

3. 예약변경 및 예약취소
   * 예약이 가능한 날짜와 시간으로만 변경 가능
   * 예약 취소 전 확인 메시지

4. 예약하기, 예약변경, 예약취소 시 문자 메시지 발송
   * NAVERE Simple & Easy Notification Service API 사용

5. 오류
   * 맞춤 오류 페이지를 통해서 4xx, 5xx 오류 제어

6. 관리자
   * 계점 시간, 폐점 시간, 휴식 시간, 예약 시간 간격, 휴무일 설정
   * 시간 형식 유효성 검사
   * 예약 명단 관리
   * 비밀번호 설정

## 사진

### 예약표
![index1](https://user-images.githubusercontent.com/79137839/159412773-0ba00b45-bb74-49f9-92fe-46d5e3f424f0.PNG)
![index2](https://user-images.githubusercontent.com/79137839/159412780-8e0eb9cc-8b59-4187-ad59-f1f74f405959.PNG)

### 예약 변경 및 예약 취소
![update   delete](https://user-images.githubusercontent.com/79137839/159412930-d8cfeaa9-00d1-488c-933e-6effbb6885c9.PNG)

### 예약 SMS
![SMS](https://user-images.githubusercontent.com/79137839/159414160-5c855a40-ca03-450c-8cbb-37e5817d76e9.jpg)

### 관리자
![appt](https://user-images.githubusercontent.com/79137839/159413015-a7b2ab93-0d51-490e-98c4-88fb72f0b55f.PNG)
![setting](https://user-images.githubusercontent.com/79137839/159413021-532416c7-2fc8-4ff3-9949-19df3ad92e17.PNG)

### 맞춤 오류 페이지
![error](https://user-images.githubusercontent.com/79137839/159413093-42b3aa8c-48d0-4b2b-b58b-ff109eb3cb5c.PNG)


