const appt = {
	apptTime: 0,
	getApptTime: function(apptTime) {
		this.apptTime = apptTime;
	},
	passApptTime: function() {
		return this.apptTime;
	}
};

const validateClient = (clientName, clientPhone) => {

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
			let y, m, d;

			y = dateArr[0];

			if (dateArr[1][0] === '0') m = dateArr[1][1];
			else m = dateArr[1];

			if (dateArr[2][0] === '0') d = dateArr[2][1];
			else d = dateArr[2];

			$('#appt-popover-title').html('<strong>' + y + '년 ' + m + '월 ' + d + '일 ' + '</strong><br>');
			const html = '<button type="button" class="btn btn-info" name="appt-time" value="1000" onclick="appt.getApptTime(this.value)">8:00 am – 9:00 am</button><br>\
	        <button type="button" class="btn btn-info" name="appt-time" value="2000" onclick="appt.getApptTime(this.value)">10:00 am – 12:00 pm</button><br>\
	        <button type="button" class="btn btn-info" name="appt-time" value="3000" onclick="appt.getApptTime(this.value)">12:00 pm – 2:00 pm</button>';
			$('#appt-popover-content').html('예약 가능한 시간' + html);
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
		let apptTime = appt.passApptTime();
		let clientName = document.querySelector('#client-name').value;
		let clientPhone = document.querySelector('#client-phone').value;

		console.log("날짜: " + apptDate);
		console.log("시간: " + apptTime);

		if (!validateClient(clientName, clientPhone)) return;

		let data = {
			apptDate: apptDate,
			apptTime: apptTime,
			clientName: clientName,
			clientPhone: clientPhone,
		};

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