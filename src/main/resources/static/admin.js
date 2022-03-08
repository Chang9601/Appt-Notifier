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

const validateOpsTime = (openTime, closeTime, breakTime) => {
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

	return true;
}

const admin = {
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
			url: '/api/admin/find-user',
			data: { username: username, password: password },
			contentType: 'application/x-www-form-urlencoded; charset=UTF-8', // 서버에 요청하는 자료형
			dataType: 'json' // 서버가 응답하는 자료형
		}).done(function(resp) {
			console.log(resp);

			let url, type;
			if (resp.data == null) {
				type = 'POST';
				url = '/api/admin/save-user';
			}
			else {
				type = 'PUT';
				url = '/api/admin/update-user';
			}

			$.ajax({
				type: type,
				url: url,
				data: JSON.stringify(data),
				contentType: 'application/json; charset=UTF-8', // 서버에 요청하는 자료형
				dataType: 'json' // 서버가 응답하는 자료형				
			}).done(function(resp) {
				console.log(resp);
			}).fail(function() {
				alert(JSON.stringify(error));
			});

		}).fail(function(error) {
			alert(JSON.stringify(error));
		});

		alert('비밀번호 설정이 완료됐습니다.');
	},

	saveTime: function() {
		let openTime = document.querySelector('#open-time').value;
		let closeTime = document.querySelector('#close-time').value;
		let breakTime = document.querySelector('#break-time').value;

		if (!validateOpsTime(openTime, closeTime, breakTime)) {
			return false;
		}

		$.ajax({
			type: 'GET',
			url: '/api/find',
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
				url: '/api/delete',
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

admin.init();