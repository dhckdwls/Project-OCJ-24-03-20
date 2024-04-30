<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="ARTICLE DETAIL"></c:set>
<%@ include file="../common/head2.jspf"%>


<main style="text-align: center;">
	<div>
		<h1 style="font-size: 3rem;">로그인</h1>
	</div>
	<div class="line"></div>
	<div class="login_box" style="display: inline-block;">
		<form action="../member/doLogin" method="POST">
			<input type="hidden" name="afterLoginUri" value="/usr/article/list" />
			<div class="group">
				<input type="text" name="loginId"><span class="highlight"></span><span class="bar"></span> <label>아이디</label>
			</div>
			<div class="group">
				<input type="password" name="loginPw"><span class="highlight"></span><span class="bar"></span> <label>비밀번호</label>
			</div>
			<button type="submit" class="button buttonBlue">
				로그인
				<div class="ripples buttonRipples">
					<span class="ripplesCircle"></span>
				</div>
			</button>
			<div class="select-box" style="font-size:1rem;">
				<a href="/usr/member/findLoginId">아이디찾기</a> <a href="/usr/member/findLoginPw">비밀번호찾기</a> <a href="/usr/member/join">회원가입</a>
			</div>
		</form>
		
	</div>

</main>


<style>
* {
	box-sizing: border-box;
}

/* body {
   -webkit-font-smoothing: antialiased;
}
 */
 
 .select-box > a:hover{
 	text-decoration: underline;
 }
 
form {
	width: 380px;
	margin: 4em auto;
	padding: 3em 2em 2em 2em;
	/* background: red; */
	border: 3px solid black;
	border-radius: 15px;
	/* box-shadow: rgba(0,0,0,0.14902) 0px 1px 1px 0px,rgba(0,0,0,0.09804) 0px 1px 2px 0px; */
}

.group {
	position: relative;
	margin-bottom: 30px;
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
keyframes ripples { 0% {
	opacity: 0;
}
25


%
{
opacity


:


1
;


}
100


%
{
width


:


200
%;


padding-bottom


:


200
%;


opacity


:


0
;


}
}
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