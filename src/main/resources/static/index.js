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
	
	$('#appt').popover({
	    html : true, 
	    content: function() {
	      return $("#appt-popover-content").html();
	    },
	    title: function() {
	      return $("#appt-popover-title").html();
	    }
	});
	
	$('#appt').datepicker({
		minDate: '0', // 선택 가능한 날짜의 최솟값, 0은 오늘
		maxDate: '+1m',  // 선택 가능한 날짜의 최댓값, 오늘부터 한달
		
	    onSelect: function(date) { 
	    	let dateArr = date.split('-');
	    	let y, m, d;
	    	
	    	y = dateArr[0];
	    	
	    	if(dateArr[1][0] === '0') m = dateArr[1][1];
	    	else m = dateArr[1];
	    	
	    	if(dateArr[2][0] === '0') d = dateArr[2][1];
	    	else d = dateArr[2];
	    	
	        $('#appt-popover-title').html('<strong>' + y + '년 ' + m + '월 ' + d + '일 ' + '</strong><br>');
	        const html = '<button class="btn btn-success">8:00 am – 9:00 am</button><br><button  class="btn btn-success">10:00 am – 12:00 pm</button><br><button  class="btn btn-success">12:00 pm – 2:00 pm</button>';
	        $('#appt-popover-content').html('예약 가능한 시간' + html);
	        $('#appt').popover('show');
	    }
	});
});

const validator = (clientName, clientPhone) => {
	
	if(clientName === '') {
		alert('이름을 입력하세요.');
		return false;
	}

	if(!clientName.match(/^[\uAC00-\uD7A3]+$/)) { // 한글 유니코드
		alert('한글만 입력하세요.');
		return false;
	}

	if(clientPhone === '') {
		alert('전화번호를 입력하세요.');
		return false;
	}

	if(clientPhone !== '' && !clientPhone.match(/^010-\d{4}-\d{4}$/)) { // 010-1234-5678
		alert('전화번호를 형식에 맞게 입력하세요.');
		return false;
	}
	return true;
};

const index = {
	
	init: function() {
		document.querySelector('#btn-save').addEventListener('click', () => {
			this.save();
		});

		document.querySelector('#btn-delete').addEventListener('click', () => {
			this.delete();
		});

	},

	save: function() {
		let apptDate = document.querySelector('#appt').value;
		let apptTime = document.querySelector('#appt-time').value;
		let clientName = document.querySelector('#client-name').value;
		let clientPhone = document.querySelector('#client-phone').value;

		console.log("날짜: " + apptDate);
		console.log("시간: " + apptTime);

		if (!validator(clientName, clientPhone)) {
			return;
		}

		let data = {
			apptDate: apptDate,
			apptTime: apptTime,
			clientName: clientName,
			clientPhone: clientPhone,
		}
		console.log(data);

		$.ajax({
			type: 'POST',
			url: '/api/join',
			data: JSON.stringify(data),
			contentType: 'application/json; charset=UTF-8', // 서버에 요청하는 자료형
			dataType: 'json' // 서버가 응답하는 자료형
		});
		
		alert('예약이 완료되었습니다.');
	},

	delete: function() {
		let ok = confirm('예약을 취소하시겠습니까?');
		
		if(!ok) {
			return;
		}
		
		let clientName = prompt('이름'); 
		let clientPhone = prompt('전화번호');

		$.ajax({
			type: 'GET',
			url: '/api/find',
			data: { clientName: clientName, clientPhone: clientPhone },
			contentType: 'application/x-www-form-urlencoded; charset=UTF-8', // 서버에 요청하는 자료형
			dataType: "json" // 서버가 응답하는 자료형
		}).done(function(resp){
			console.log(resp);
			if(resp.data === null) {
				alert('정보와 일치하는 예약이 없습니다.');
				return;
			}
			
			$.ajax({
				type: 'DELETE',
				url: '/api/delete',
				data: { clientName: clientName, clientPhone: clientPhone },
				contentType: 'application/x-www-form-urlencoded; charset=UTF-8', // 서버에 요청하는 자료형				
			}).done(function(resp){
				alert('예약이 취소되었습니다.');
			});
		});
	}
}

index.init();