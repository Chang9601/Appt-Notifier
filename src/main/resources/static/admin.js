const validateUser = (password, cpassword) => {
	if (password === '') {
		alert('비밀번호를 입력하세요.');
		return false;
	}

	if (cpassword === '') {
		alert('비밀번호를 다시 입력하세요.');
		return false;
	}

	if (password !== cpassword) {
		alert('비밀번호가 일치하지 않습니다.');
		return false;
	}

	return true;
};

const validateOpsTime = (openTime, closeTime, apptInterval) => {
	const regex = /^([01][0-9]|2[0-3]):[0-5][0-9]$/;
	
	if (openTime === '') {
		alert('개점 시간을 입력하세요.');
		return false;
	}
	
	if (!openTime.match(regex)) { 
		alert('24시간제에 맞게 개점 시간을 입력하세요.');
		return false;		
	}
	
	if (closeTime === '') {
		alert('폐점 시간을 입력하세요.');
		return false;
	}
	
	if (!closeTime.match(regex)) { 
		alert('24시간제에 맞게 폐점 시간을 입력하세요.');
		return false;		
	}
	
	if (apptInterval === '') {
		alert('예약 시간 간격을 입력하세요');
		return false;			
	}
	
	if (!apptInterval.match(/^[1-6]0$/)) {
		alert('10, 20, 30, 40, 50, 60 중 하나를 입력하세요');
		return false;
	}

	return true;
};

let admin = {
	init: function() {
		document.querySelector('#btn-save-user').addEventListener('click', () => {
			this.saveUser();
		});

		document.querySelector('#btn-save-time').addEventListener('click', () => {
			this.saveTime();
		});
	},

	saveUser: function() {
		let username = document.querySelector('#username').value;
		let password = document.querySelector('#password').value;
		let cpassword = document.querySelector('#cpassword').value;

		if (!validateUser(password, cpassword)) {
			return;
		}

		let data = {
			username: username,
			password: password
		};

		$.ajax({
			type: 'GET',
			url: '/api/user/find',
			dataType: 'json' // 서버가 응답하는 자료형
		}).done(function(resp) {

			let url, type;
			if (resp.data === null) {
				type = 'POST';
				url = '/api/user/save';
			}
			else {
				type = 'PUT';
				url = '/api/user/update';
			}

			$.ajax({
				type: type,
				url: url,
				data: JSON.stringify(data),
				contentType: 'application/json; charset=UTF-8', // 서버에 요청하는 자료형
				dataType: 'json' // 서버가 응답하는 자료형				
			}).done(function(resp) {
				alert('비밀번호 설정이 완료됐습니다.');
			}).fail(function() {
				alert(JSON.stringify(error));
			});

		}).fail(function(error) {
			alert(JSON.stringify(error));
		});
	},

	saveTime: function() {
		let openTime = document.querySelector('#open-time').value;
		let closeTime = document.querySelector('#close-time').value;
		let apptInterval = document.querySelector('#appt-interval').value;

		if (!validateOpsTime(openTime, closeTime, apptInterval)) {
			return false;
		}
		
		let data = {
			openTime: openTime,
			closeTime: closeTime,
			apptInterval: apptInterval
		};
		
		$.ajax({
			type: 'GET',
			url: '/api/ops-time/find',
			dataType: 'json' // 서버가 응답하는 자료형
		}).done(function(resp) {
			
			let url, type;
			if (resp.data === null) {
				type = 'POST';
				url = '/api/ops-time/save';
			}
			else {
				type = 'PUT';
				url = '/api/ops-time/update';
			}
			
			$.ajax({
				type: type,
				url: url,
				data: JSON.stringify(data),
				contentType: 'application/json; charset=UTF-8', // 서버에 요청하는 자료형		
				dataType: 'json'		
			}).done(function(resp) {
			}).fail(function(error) {
				alert(JSON.stringify(error));
			});
		}).fail(function(error) {
			alert(JSON.stringify(error));
		});
		
		alert('시간 설정이 완료되었습니다.');
	},
}

admin.init();