
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
			<a class="btn btn-sm btn-outline" href="../member/checkPw"">회원정보 수정</a> <a class="btn btn-sm btn-outline"
				href="/usr/member/doDelete?id=${rq.loginedMemberId }">회원탈퇴</a>
		</div>
	</div>

	<div class="line"></div>

	<div>
		<h1>내가 쓴 글 테스트</h1>
		<c:if test="${articles != null }">
			<div>
				<ul class="cards">
					<c:forEach var="article" items="${articles }">
						<li class="cards_item">
							<div class="card">
								<div class="card_image">
									<img src="${article.firstImage }">
								</div>
								<div class="card_content">
									<h2 class="card_title">${article.title }</h2>
									<p class="card_text">
										<i class="fa-solid fa-heart fa-xl"></i>${article.goodReactionPoint } <i class="fa-solid fa-eye fa-xl"></i>${article.hitCount }<i
											class="fa-solid fa-comment-dots fa-xl"></i>${article.extra__repliesCnt }
									</p>
									<button class="btn1 card_btn">
										<a href="/usr/article/detail?id=${article.id}">더보기</a>
									</button>
								</div>
							</div>
						</li>
					</c:forEach>
				</ul>
			</div>
		</c:if>
		<c:if test="${articles.size() == 0 }">
		<h1>작성한 게시글이 없어요.</h1>
		</c:if>
	</div>
	<div class="line"></div>

	<div><h1>좋아요 표시한 게시글</h1>
	<c:if test="${likeArticles != null }">
			<div>
				<ul class="cards">
					<c:forEach var="article" items="${likeArticles }">
						<li class="cards_item">
							<div class="card">
								<div class="card_image">
									<img src="${article.firstImage }">
								</div>
								<div class="card_content">
									<h2 class="card_title">${article.title }</h2>
									<p class="card_text">
										<i class="fa-solid fa-heart fa-xl"></i>${article.goodReactionPoint } <i class="fa-solid fa-eye fa-xl"></i>${article.hitCount }<i
											class="fa-solid fa-comment-dots fa-xl"></i>${article.extra__repliesCnt }
									</p>
									<button class="btn1 card_btn">
										<a href="/usr/article/detail?id=${article.id}">더보기</a>
									</button>
								</div>
							</div>
						</li>
					</c:forEach>
				</ul>
			</div>
		</c:if>
		<c:if test="${likeArticles.size() == 0 }">
		<h1>작성한 게시글이 없어요.</h1>
		</c:if>
	
	
	</div>
	

	<div class="line"></div>

	<div><h1>내가 쓴 댓글</h1>
	<c:if test="${replies.size() == 0 }">
	<h1>작성한 댓글이 없어요</h1>
	</c:if>
	<c:if test="${replies.size() != 0 }">
	<c:forEach var="reply" items="${replies }">
	<div>${reply.id }</div>
	<div>${reply.regDate }</div>
	<div>${reply.updateDate }</div>
	<div>${reply.id }</div>
	<div>${reply.body }</div>
	<button class="btn btn-sm btn-outline">수정</button>
	<button class="btn btn-sm btn-outline" onclick="if(confirm('댓글을 삭제하시겠습니까?') == false) return false; deleteReply(${reply.id})">삭제</button>
	</c:forEach>
	</c:if>
	</div>
	
	<script>
function deleteReply(replyId){	
}	
	</script>




	<div class="line"></div>
</main>

<style>
.container1 {
	display: flex;
	justify-content: center;
	align-items: center;
	height: 100vh;
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