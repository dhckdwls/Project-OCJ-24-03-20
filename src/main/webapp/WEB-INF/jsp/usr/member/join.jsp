<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="회원가입"></c:set>
<%@ include file="../common/head2.jspf"%>
<!-- lodash debounce -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/lodash.js/4.17.21/lodash.min.js"></script>
<script>
	let submitJoinFormDone = false;
	let validLoginId = "";

	function submitJoinForm(form) {
		if (submitJoinFormDone) {
			alert('처리중입니다');
			return;
		}
		form.loginId.value = form.loginId.value.trim();
		if (form.loginId.value == 0) {
			alert('아이디를 입력해주세요 jsp');
			form.loginId.focus();
			return;
		}

		if (form.loginId.value != validLoginId) {
			alert('사용할 수 없는 아이디야 jsp');
			form.loginId.focus();
			return;
		}
		if (validLoginId == form.loginId.value) {
			return;
		}

		form.loginPw.value = form.loginPw.value.trim();
		if (form.loginPw.value == 0) {
			alert('비밀번호를 입력해주세요 jsp');
			return;
		}
		form.loginPwConfirm.value = form.loginPwConfirm.value.trim();
		if (form.loginPwConfirm.value == 0) {
			alert('비밀번호 확인을 입력해주세요 jsp');
			return;
		}
		if (form.loginPwConfirm.value != form.loginPw.value) {
			alert('비밀번호가 일치하지 않습니다 jsp');
			form.loginPw.focus();
			return;
		}
		form.name.value = form.name.value.trim();
		if (form.name.value == 0) {
			alert('이름을 입력해주세요 jsp');
			return;
		}
		form.nickname.value = form.nickname.value.trim();
		if (form.nickname.value == 0) {
			alert('닉네임을 입력해주세요 jsp');
			return;
		}
		form.email.value = form.email.value.trim();
		if (form.email.value == 0) {
			alert('이메일을 입력해주세요 jsp');
			return;
		}
		form.cellphoneNum.value = form.cellphoneNum.value.trim();
		if (form.cellphoneNum.value == 0) {
			alert('전화번호를 입력해주세요 jsp');
			return;
		}
		submitJoinFormDone = true;
		form.submit();
	}

	function checkLoginIdDup(el) {
		$('.checkDup-msg').empty();
		const form = $(el).closest('form').get(0);
		if (form.loginId.value.length == 0) {
			validLoginId = '';
			return;
		}
		$.get('../member/getLoginIdDup', {
			isAjax : 'Y',
			loginId : form.loginId.value
		}, function(data) {
			$('.checkDup-msg').html('<div class="mt-2">' + data.msg + '</div>')
			if (data.success) {
				validLoginId = data.data1;
			} else {
				validLoginId = '';
			}
		}, 'json');
	}
	
	function checkLoginPw(el) {
		$('.checkPw-msg').empty();
		const form = $(el).closest('form').get(0);
		if (form.loginId.value.length == 0) {
			validLoginId = '';
			return;
		}
		$.get('../member/getLoginIdDup', {
			isAjax : 'Y',
			loginId : form.loginId.value
		}, function(data) {
			$('.checkDup-msg').html('<div class="mt-2">' + data.msg + '</div>')
			if (data.success) {
				validLoginId = data.data1;
			} else {
				validLoginId = '';
			}
		}, 'json');
	}

	const checkLoginIdDupDebounced = _.debounce(checkLoginIdDup, 600);
</script>

<script>
	$(document)
			.ready(
					function() {
						$('.toggle-password-visibility')
								.click(
										function() {
											var passwordField = $(this)
													.closest('.group').find(
															'.password-input');
											var fieldType = passwordField
													.attr('type');

											if (fieldType === 'password') {
												passwordField.attr('type',
														'text');
												$(this)
														.html(
																'<i class="fa-solid fa-toggle-on"></i>');
											} else {
												passwordField.attr('type',
														'password');
												$(this)
														.html(
																'<i class="fa-solid fa-toggle-off"></i>');
											}
										});
					});
</script>

<style>
.group {
	position: relative;
}

.password-toggle {
	position: absolute;
	top: 50%;
	transform: translateY(-50%);
	right: 10px; /* 입력 필드 오른쪽에 버튼을 배치할 거에요.*/
}


</style>

<!-- 비밀번호체크 -->
<script>


</script>

<main style="text-align: center;">
	<div>
		<h1 style="font-size: 3rem;">회원가입</h1>
	</div>
	<div class="line"></div>
	<div class="login_box" style="display: inline-block; border: 2px solid black; border-radius: 30px;">
		<form action="/usr/member/doJoin" method="POST">
			<div class="group">
				<input type="text" name="loginId" onkeyup="checkLoginIdDupDebounced(this);"><span class="highlight"
					autocomplete="off"></span><span class="bar"></span> <label>아이디</label>
				<div class="checkDup-msg" style="font-size: 20px;"></div>
			</div>

			<div class="group">
				<input type="password" name="loginPw" class="password-input"> <span class="highlight" autocomplete="off"></span>
				<span class="bar"></span> <label>비밀번호</label>
				<div class="password-toggle">
					<button type="button" class="toggle-password-visibility" style="">
						<i class="fa-solid fa-toggle-off"></i>
					</button>
				</div>
				<div></div>
			</div>
			
			<div class="group">
				<input type="password" name="loginPwConfirm" class="password-input"> <span class="highlight"
					autocomplete="off"></span> <span class="bar"></span> <label>비밀번호 확인</label>
				<div class="password-toggle">
					<button type="button" class="toggle-password-visibility">
						<i class="fa-solid fa-toggle-off"></i>
					</button>
				</div>
			</div>
			<div class="checkPw-msg" style="font-size:20px; margin-bottom:15px;">영어,숫자,!@#$%&* 포함 16글자</div>

			<div class="group">
				<input type="text" name="name"><span class="highlight" autocomplete="off"></span><span class="bar"></span> <label>이름</label>
				<div></div>
			</div>

			<div class="group">
				<input type="text" name="nickname"><span class="highlight" autocomplete="off"></span><span class="bar"></span>
				<label>닉네임</label>
				<div></div>
			</div>

			<div class="group">
				<input type="text" name="email"><span class="highlight" autocomplete="off"></span><span class="bar"></span>
				<label>이메일</label>
				<div></div>
			</div>

			<div class="group">
				<input type="text" name="cellphoneNum"><span class="highlight" autocomplete="off"></span><span class="bar"></span>
				<label>전화번호</label>
				<div></div>
			</div>
			
			<button type="submit" class="button buttonBlue">
				회원가입
				<div class="ripples buttonRipples">
					<span class="ripplesCircle"></span>
				</div>
			</button>
		</form>
	</div>
	<div class="line"></div>

</main>


<style>
* {
	box-sizing: border-box;
}

/* body {
   -webkit-font-smoothing: antialiased;
}
 */
form {
	width: 380px;
	padding: 3em 2em 2em 2em;
	/* background: red; */
	/* box-shadow: rgba(0,0,0,0.14902) 0px 1px 1px 0px,rgba(0,0,0,0.09804) 0px 1px 2px 0px; */
}

.group {
	position: relative;
	margin-bottom: 20px; /* 임시처리  */
}

input {
	font-size: 18px;
	padding: 10px 10px 10px 5px;
	-webkit-appearance: none;
	display: block;
	background: #fafafa;
	color: #636363;
	width: 100%;
	border: none;
	border-radius: 0;
	border-bottom: 1px solid #757575;
}

input:focus {
	outline: none;
}

/* Label */
label {
	color: #999; /* 아이디,비밀번호 글자색 */
	font-size: 18px;
	font-weight: normal;
	position: absolute;
	pointer-events: none;
	left: 5px;
	top: 10px;
	transition: all 0.2s ease;
}
/* active */
input:focus ~ label, input.used ~ label {
	top: -20px;
	transform: scale(.75);
	left: -2px;
	/* font-size: 14px; */
	color: #4a89dc;
}
/* Underline */
.bar {
	position: relative;
	display: block;
	width: 100%;
}
.bar:before, .bar:after {
	content: '';
	height: 2px;
	width: 0;
	bottom: 1px;
	position: absolute;
	background: #4a89dc;
	transition: all 0.2s ease;
}
.bar:before {
	left: 50%;
}
.bar:after {
	right: 50%;
}
/* active */
input:focus ~ .bar:before, input:focus ~ .bar:after {
	width: 50%;
}
/* Highlight */
.highlight {
	position: absolute;
	height: 60%;
	width: 100px;
	top: 25%;
	left: 0;
	pointer-events: none;
	opacity: 0.5;
}
/* active */
input:focus ~ .highlight {
	animation: inputHighlighter 0.3s ease;
}
/* Animations */
@
keyframes inputHighlighter {from { background:#4a89dc;
}
to {
	width: 0;
	background: transparent;
}
}
/* Button */
.button {
	position: relative;
	display: inline-block;
	padding: 12px 24px;
	margin: .3em 0 1em 0;
	width: 100%;
	vertical-align: middle;
	color: #fff;
	font-size: 16px;
	line-height: 20px;
	-webkit-font-smoothing: antialiased;
	text-align: center;
	letter-spacing: 1px;
	background: transparent;
	border: 0;
	border-bottom: 2px solid #3160B6;
	cursor: pointer;
	transition: all 0.15s ease;
}

.button:focus {
	outline: 0;
}

/* Button modifiers */
.buttonBlue {
	background: black;
	text-shadow: 1px 1px 0 rgba(39, 110, 204, .5);
}

.buttonBlue:hover {
	background: #357bd8;
}

/* Ripples container */
.ripples {
	position: absolute;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	overflow: hidden;
	background: transparent;
}

/* Ripples circle */
.ripplesCircle {
	position: absolute;
	top: 50%;
	left: 50%;
	transform: translate(-50%, -50%);
	opacity: 0;
	width: 0;
	height: 0;
	border-radius: 50%;
	background: rgba(255, 255, 255, 0.25);
}

.ripples.is-active .ripplesCircle {
	animation: ripples .4s ease-in;
}

/* Ripples animation */
@
keyframes ripples { 0% {opacity: 0;}25%{opacity:1;}100%{width:200%;padding-bottom:200%;opacity:0;}}
</style>
<script>
	$(window, document, undefined)
			.ready(
					function() {

						$('input').blur(function() {
							var $this = $(this);
							if ($this.val())
								$this.addClass('used');
							else
								$this.removeClass('used');
						});

						var $ripples = $('.ripples');

						$ripples.on('click.Ripples', function(e) {

							var $this = $(this);
							var $offset = $this.parent().offset();
							var $circle = $this.find('.ripplesCircle');

							var x = e.pageX - $offset.left;
							var y = e.pageY - $offset.top;

							$circle.css({
								top : y + 'px',
								left : x + 'px'
							});

							$this.addClass('is-active');

						});

						$ripples
								.on(
										'animationend webkitAnimationEnd mozAnimationEnd oanimationend MSAnimationEnd',
										function(e) {
											$(this).removeClass('is-active');
										});

					});
</script>

<%@ include file="../common/foot2.jspf"%>