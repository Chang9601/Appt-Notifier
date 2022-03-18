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
});