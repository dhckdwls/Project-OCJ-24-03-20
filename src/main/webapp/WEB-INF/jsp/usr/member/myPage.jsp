
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="ARTICLE DETAIL"></c:set>
<%@ include file="../common/head2.jspf"%>
<main style="text-align: center;">
	<div>
		<h1 style="font-size: 3rem;">마이페이지</h1>
	</div>
	<div class="line"></div>
	<div class="container1 flex flex-col">
		<div class="card" style="width: 40%;">
			<h2 style="text-align: center; padding: 20px; margin: 0; background-color: skyblue; color: black;">회원정보</h2>
			<table>
				<tr>
					<th>아이디 :</th>
					<td>${rq.loginedMember.loginId }</td>
				</tr>
				<tr>
					<th>이름 :</th>
					<td>${rq.loginedMember.name }</td>
				</tr>
				<tr>
					<th>닉네임 :</th>
					<td>${rq.loginedMember.nickname }</td>
				</tr>
				<tr>
					<th>전화번호 :</th>
					<td>${rq.loginedMember.cellphoneNum }</td>
				</tr>
				<tr>
					<th>Email :</th>
					<td>${rq.loginedMember.email }</td>
				</tr>
				<tr>
					<th>가입일 :</th>
					<td>${rq.loginedMember.regDate }</td>
				</tr>
			</table>
		</div>
		<div>
			<a class="btn btn-sm btn-outline" href="/usr/member/modify">회원정보 수정</a> <a class="btn btn-sm btn-outline" href="/usr/member/doDelete">회원탈퇴</a>
		</div>
	</div>

	<div class="line"></div>

	<div>내가 쓴 글</div>

	<div class="line"></div>

	<div>즐겨찾기 해놓은 글</div>

	<div class="line"></div>

	<div>내가 쓴 댓글</div>




	<div class="line"></div>
</main>

<style>
.container1 {
	display: flex;
	justify-content: center;
	align-items: center;
	height: 100vh;
	background-color: #f1f1f1;
}

.card {
	background-color: #fff;
	border-radius: 10px;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
	overflow: hidden;
}

table {
	width: 100%;
	border-collapse: collapse;
}

th, td {
	padding: 15px;
	text-align: center;
	border-bottom: 1px solid #ddd;
}

th {
	background-color: #f2f2f2;
}
</style>
<%@ include file="../common/foot2.jspf"%>