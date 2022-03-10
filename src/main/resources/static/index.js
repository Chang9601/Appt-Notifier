// 예약 시간 전달
const appt = {
	apptTime: '0',
	setApptTime: function(apptTime) {
		this.apptTime = apptTime;
	},
	getApptTime: function() {
		return this.apptTime;
	}
};

// 사용자 유효성
const validateAppt = (clientName, clientPhone) => {

	if (clientName === '') {
		alert('이름을 입력하세요.');
		return false;
	}

	if (!clientName.match(/^[\uAC00-\uD7A3]+$/)) { // 한글 유니코드
		alert('한글만 입력하세요.');
		return false;
	}

	if (clientPhone === '') {
		alert('전화번호를 입력하세요.');
		return false;
	}

	if (clientPhone !== '' && !clientPhone.match(/^010-\d{4}-\d{4}$/)) { // 010-1234-5678
		alert('전화번호를 형식에 맞게 입력하세요.');
		return false;
	}

	return true;
};

// 문자열 숫자 -> 정수 숫자
const decodeTime = (apptTime) => {
	apptTime = apptTime.split(':');

	let hour = parseInt(apptTime[0], 10);
	let minute = parseInt(apptTime[1], 10);

	return hour * 60 + minute;
};

// 정수 숫자 -> 문자열 숫자
const encodeTime = (apptTime) => {
	let hour = Math.floor(apptTime / 60); // 시
	let minute = apptTime % 60; // 분

	if (hour < 10) hour = '0' + hour; // 앞에 0 추가
	if (minute < 10) minute = '0' + minute; // 앞에 0 추기
	apptTime = hour + ':' + minute; // 시:분 형식의 문자열

	return apptTime;
};

// 시 포맷
const formatHour = (apptTime) => {
	let hour = Math.floor(apptTime / 60); // 시	
	if (hour < 10) hour = '0' + hour; // 앞에 0 추가

	return hour;
};

// 분 포맷
const formatMinute = (apptTime) => {
	let minute = apptTime % 60; // 분	
	if (minute < 10) minute = '0' + minute; // 앞에 0 추가

	return minute;
};

// 이미 예약
const isAlreadyAppt = (appts, date, time) => {
	for (let i = 0; i < appts.length; i++) {
		if (appts[i].apptDate === date && appts[i].apptTime === time)
			return true;
	}
	return false;
};

$(function() {
	// 모든 date picker의 기본값 설정
	$.datepicker.setDefaults({
		dateFormat: 'yy-mm-dd',
		prevText: '이전 달',
		nextText: '다음 달',
		monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
		monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
		dayNames: ['일', '월', '화', '수', '목', '금', '토'],
		dayNamesShort: ['일', '월', '화', '수', '목', '금', '토'],
		dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
		showMonthAfterYear: true,
		weekHeader: "주",
		yearSuffix: '년'
	});

	$('#appt-date').popover({
		html: true,
		content: function() {
			return $("#appt-popover-content").html();
		},
		title: function() {
			return $("#appt-popover-title").html();
		}
	});

	$('#appt-date').datepicker({
		minDate: '0', // 선택 가능한 날짜의 최솟값, 0은 오늘
		maxDate: '+1m',  // 선택 가능한 날짜의 최댓값, 오늘부터 한달

		onSelect: function(date) {
			let dateArr = date.split('-');
			let year, month, day;

			let openTime = decodeTime(document.querySelector('#open-time').value);
			let closeTime = decodeTime(document.querySelector('#close-time').value);
			let apptInterval = parseInt(document.querySelector('#appt-interval').value); // 문자열 -> 숫자 변환
			let appts = JSON.parse(document.querySelector("#appts").value); // 예약명단

			let html = '';
			let cnt = 0;
			
			console.log('지금: ' + new Date().toISOString());

			for (initTime = openTime; initTime < closeTime; initTime += apptInterval) {
				time = encodeTime(initTime);
				let disable = '';
				if (isAlreadyAppt(appts, date, time))
					disable = 'disabled';
				html += `<button type="button" ${disable} class="btn btn-info" name="appt-time" value=${time} onclick="appt.setApptTime(this.value)">${formatHour(initTime)}:${formatMinute(initTime)}</button>`;

				cnt++;
				if (cnt % 6 === 0) // TO-DO: 한 줄당 예약 시간의 개수
					html += '<br><br>';
			}

			year = dateArr[0];

			if (dateArr[1][0] === '0') month = dateArr[1][1];
			else month = dateArr[1];

			if (dateArr[2][0] === '0') day = dateArr[2][1];
			else day = dateArr[2];

			$('#appt-popover-title').html('<strong>' + year + '년 ' + month + '월 ' + day + '일 ' + '</strong><br>');
			const apptTable = html;
			$('#appt-popover-content').html('예약 가능한 시간<br>' + apptTable);
			$('#appt-date').popover('show');
		}
	});
});

const index = {
	// 중첩 함수를 화살표 함수가 아니라 function으로 정의할 경우
	// 중첩 함수가 메서드로 호출: this 값은 호출한 객체
	// 중첩 함수가 함수로 호출: this 값은 전역 객체 혹은 undefined
	// 1. this 값을 변수에 저장, 2. 화살표 함수(this 값을 상속받는다.) 3. bind() 메서드 호출
	init: function() {
		document.querySelector('#btn-save').addEventListener('click', () => {
			this.save();
		});

		document.querySelector('#btn-delete').addEventListener('click', () => {
			this.delete();
		});

	},

	save: function() {
		let apptDate = document.querySelector('#appt-date').value;
		let apptTime = appt.getApptTime();
		let clientName = document.querySelector('#client-name').value;
		let clientPhone = document.querySelector('#client-phone').value;

		if (!validateAppt(clientName, clientPhone)) return;

		let data = {
			apptDate: apptDate,
			apptTime: apptTime,
			clientName: clientName,
			clientPhone: clientPhone,
		};

		console.log('데이터: ' + JSON.stringify(data));

		$.ajax({
			type: 'POST',
			url: '/api/appt/save',
			data: JSON.stringify(data),
			contentType: 'application/json; charset=UTF-8', // 서버에 요청하는 자료형
			dataType: 'json' // 서버가 응답하는 자료형
		}).done(function(resp) {
			console.log(resp);
		}).fail(function(error) {
			alert(JSON.stringify(error));
		});

		alert('예약이 완료되었습니다.');
	},

	delete: function() {
		let ok = confirm('예약을 취소하시겠습니까?');

		if (!ok) return;

		let clientName = prompt('이름');
		let clientPhone = prompt('전화번호');

		$.ajax({
			type: 'GET',
			url: '/api/appt/find',
			data: { clientName: clientName, clientPhone: clientPhone },
			contentType: 'application/x-www-form-urlencoded; charset=UTF-8', // 서버에 요청하는 자료형
			dataType: "json" // 서버가 응답하는 자료형
		}).done(function(resp) {
			if (resp.data === null) {
				alert('정보와 일치하는 예약이 없습니다.');
				return;
			}

			$.ajax({
				type: 'DELETE',
				url: '/api/appt/delete',
				data: { clientName: clientName, clientPhone: clientPhone },
				contentType: 'application/x-www-form-urlencoded; charset=UTF-8', // 서버에 요청하는 자료형				
			}).done(function() {
				alert('예약이 취소되었습니다.');
			}).fail(function(error) {
				alert(error);
			});
		}).fail(function(error) {
			alert(JSON.stringify(error));
		});
	}
}

index.init();