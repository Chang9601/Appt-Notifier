const validateLogin = (password) => {

	if (password === '') {
		alert('비밀번호를 입력하세요.');
		return false;
	}

	return true;
};

document.querySelector('#btn-login').addEventListener('click', () => {
	let password = document.querySelector('#password').value;

	if (!validateLogin(password)) {
		return;
	}

	$.ajax({
		type: 'GET',
		url: '/api/user/find',
		dataType: 'json' // 서버가 응답하는 자료형
	}).done(function(resp) {
		/*
		if (resp.data === null) {
			alert('계정이 존재하지 않습니다.'); // 이동, confirm 사용
			return;
		}*/
		
		if(resp.data.password !== password) {
			alert('비밀번호가 일치하지 않습니다.');
			return;
		}
		
		alert('로그인 성공! 예약명단 페이지로 이동합니다.');	
		location.replace('/appt');	
		
	}).fail(function(error) {
		alert(JSON.stringify(error));
	});
});