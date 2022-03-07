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
		
		if(password === ''){
			alert('비밀번호를 입력하세요.');
			return;
		}
		
		if(cpassword === ''){
			alert('비밀번호를 다시 입력하세요.');
			return;			
		}
		
		if(password !== cpassword){
			alert('비밀번호가 일치하지 않습니다.');
			return;
		}
		
		let data = {
			username: username,
			password: password
		};
		
		$.ajax({
			type: 'POST',
			url: '/api/admin/save-user',
			data: JSON.stringify(data),
			contentType: 'application/json; charset=UTF-8', // 서버에 요청하는 자료형
			dataType: 'json' // 서버가 응답하는 자료형
		}).done(function(resp){
			console.log(resp);
		}).fail(function(error){
			alert(JSON.stringify(error));
		});
		
		alert('비밀번호 설정이 완료됐습니다.');
	},

	saveTime: function() {

		$.ajax({
			type: 'GET',
			url: '/api/find',
			data: { clientName: clientName, clientPhone: clientPhone },
			contentType: 'application/x-www-form-urlencoded; charset=UTF-8', // 서버에 요청하는 자료형
			dataType: "json" // 서버가 응답하는 자료형
		}).done(function(resp){
			if(resp.data === null) {
				alert('정보와 일치하는 예약이 없습니다.');
				return;
			}
			
			$.ajax({
				type: 'DELETE',
				url: '/api/delete',
				data: { clientName: clientName, clientPhone: clientPhone },
				contentType: 'application/x-www-form-urlencoded; charset=UTF-8', // 서버에 요청하는 자료형				
			}).done(function(){
				alert('예약이 취소되었습니다.');
			}).fail(function(error){
				alert(error);
			});
		}).fail(function(error){
			alert(JSON.stringify(error));
		});
	}
}

admin.init();