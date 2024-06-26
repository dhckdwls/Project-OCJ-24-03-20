<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="ARTICLE DETAIL"></c:set>
<%@ include file="../common/head2.jspf"%>
<main class="flex flex-col">
	<h1 style="font-size: 4rem; font-weight: bold;">여행지</h1>
	<div class="line"></div>
	<div>
		<div class="mx-auto overflow-x-auto">
			<div class="mb-4 flex">
				<div class="flex-grow"></div>
				<form action="">
					<input type="hidden" name="boardId" value="${param.boardId }" /> <select
						data-value="${param.searchKeywordTypeCode }" class="select select-bordered select-sm w-full max-w-xs"
						name="searchKeywordTypeCode">
						<option value="title">제목</option>
						<option value="body">내용</option>
						<option value="title,body">제목+내용</option>
						<option value="tag">#태그</option>
						<option value="address">주소</option>
						
					</select> <input value="${param.searchKeyword }" name="searchKeyword" type="text" placeholder="searchKeyword?"
						class="input-sm input input-bordered w-48 max-w-xs" />
					<button class="btn btn-sm btn-outline" type="submit">검색</button>
				</form>
			</div>
		</div>
	</div>
	<div style="text-align: start; margin-left: 50px;">
		<button class="btn btn-sm btn-outline">
			최신
		</button>
		<button class="btn btn-sm btn-outline">
			인기
		</button>
		<div class="badge badge-outline badge-lg" style="font-size: 15px;">${articlesCount }개</div>
	</div>
	<div class="line"></div>
	<div class="main">
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
									<i class="fa-solid fa-heart fa-xl"></i>${article.goodReactionPoint } <i class="fa-solid fa-eye fa-xl"></i>${article.hitCount }<i class="fa-solid fa-comment-dots fa-xl"></i>${article.extra__repliesCnt }
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
	</div>
	<div class="pageBtn" style="margin-bottom: 20px; text-align: center;">
		<div class="line"></div>

		<div class="btn-group">
			<c:forEach begin="1" end="${pagesCount }" var="i">
				<button>
					<a class="btn btn-sm btn-outline ${param.page == i ? 'btn-active' : '' }"
						href="?page=${i }&boardId=${param.boardId}">${i }</a>
				</button>
			</c:forEach>
		</div>

	</div>



</main>

<%@ include file="../common/foot2.jspf"%>