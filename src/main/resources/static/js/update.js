// 예약 시간 전달
let appt = {
	apptTime: '0',
	setApptTime: function(apptTime) {
		this.apptTime = apptTime;
	},
	getApptTime: function() {
		return this.apptTime;
	}
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

// 휴식 시간
const isBetweenBreakTime = (startBreakTime, endBreakTime, startApptTime, endApptTime) => {	
	if(startApptTime >= endBreakTime || endApptTime <= startBreakTime)
		return false;
	
	return true;	
};

// 현재 시간 계산
const computeNow = (now) => {
	now = now.split(':');
	now = now[0] + ':' + now[1];
	now = decodeTime(now);	
	
	return now;
};

// 휴무일 계산
const computeDayOff = (date, dayOff, dayOffArr) => {
	let day = date.getDay();
	
	if(day === dayOff) {
		let strDate = jQuery.datepicker.formatDate('yy-mm-dd', date)
		dayOffArr.push(strDate);
	}
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

	let dayOffArr = [];
	let dayOff = parseInt(document.querySelector('#day-off').value);
	
	$('#appt-date').datepicker({
		minDate: '0', // 선택 가능한 날짜의 최솟값, 0은 오늘
		maxDate: '+1m',  // 선택 가능한 날짜의 최댓값, 오늘부터 한달

		beforeShowDay: function(date) {
			computeDayOff(date, dayOff, dayOffArr);
			let strDate = jQuery.datepicker.formatDate('yy-mm-dd', date);
        	return [dayOffArr.indexOf(strDate) == -1];
		},
		
		onSelect: function(date) {
			let dateArr = date.split('-');
			let year, month, day;

			let openTime = decodeTime(document.querySelector('#open-time').value);
			let closeTime = decodeTime(document.querySelector('#close-time').value);
			let startBreakTime = decodeTime(document.querySelector('#start-break-time').value);
			let endBreakTime = decodeTime(document.querySelector('#end-break-time').value);			
			let apptInterval = parseInt(document.querySelector('#appt-interval').value); // 문자열 -> 숫자 변환
			let appts = JSON.parse(document.querySelector("#appts").value); // 예약명단

			let html = '';
			let cnt = 0;
			
			// 오늘
			const today = new Date().toISOString().split('T')[0];
			
			// 현재 시간
			let now = new Date().toLocaleTimeString('it-IT');
			now = computeNow(now);
			
			for (initTime = openTime; initTime < closeTime; initTime += apptInterval) {
				time = encodeTime(initTime);
				let disable = '';
				if (isAlreadyAppt(appts, date, time) || ((today === date) && initTime < now) || isBetweenBreakTime(startBreakTime, endBreakTime, initTime, initTime + apptInterval)) // 이미 예약 or 오늘 현재 시간 전
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

document.querySelector('#btn-update').addEventListener('click', () => {
	
	let ok = confirm('예약을 변경하시겠습니까?');
	
	if(!ok) {
		return;
	}

	let id = document.querySelector('#appt-id').value;
	let apptDate = document.querySelector('#appt-date').value;
	let apptTime = appt.getApptTime();
	let clientName = document.querySelector('#client-name').value;
	let clientPhone = document.querySelector('#client-phone').value;	
	
	if(apptTime === '0') {
		alert('예약 시간을 선택해주세요.');
		return false;
	}
	
	let data = {
		apptDate: apptDate,
		apptTime: apptTime
	};
	
	$.ajax({
		type: 'PUT',
		url: `/appt/update/${id}`,
		data: JSON.stringify(data),
		contentType: 'application/json; charset=UTF-8', // 서버에 요청하는 자료형		
		dataType: 'json' // 서버가 응답하는 자료형
	}).done(function(resp) {
		alert('예약이 변경되었습니다.');
		location.replace(`/appt/update-delete?clientName=${clientName}&clientPhone=${clientPhone}`);
	}).fail(function(error) {
		alert(JSON.stringify(error));
	});
});

document.querySelector('#btn-cancel').addEventListener('click', () => {
	
	let ok = confirm('예약목록으로 돌아가시겠습니까?');
	let clientName = document.querySelector('#client-name').value;
	let clientPhone = document.querySelector('#client-phone').value;	
	
	if(!ok) {
		return;
	}
	
	location.replace(`/appt/update-delete?clientName=${clientName}&clientPhone=${clientPhone}`);
});