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
					</select> <input value="${param.searchKeyword }" name="searchKeyword" type="text" placeholder="searchKeyword?"
						class="input-sm input input-bordered w-48 max-w-xs" />
					<button class="btn btn-sm btn-outline" type="submit">검색</button>
				</form>
			</div>
		</div>
	</div>
	<div style="text-align: start; margin-left: 50px;">
		<button class="btn btn-sm btn-outline">
			<a href="#">최신순</a>
		</button>
		<button class="btn btn-sm btn-outline">
			<a href="#">인기순</a>
		</button>
		<div class="badge badge-outline badge-lg" style="font-size: 15px;">123456개</div>
	</div>
	<div class="line"></div>
	<div class="main">
		<div>
			<ul class="cards">
				<c:forEach var="APIarticle" items="${apiarticles }">
					<li class="cards_item">
						<div class="card">
							<div class="card_image">
								<img src="${APIarticle.firstimage }">
							</div>
							<div class="card_content">
								<h2 class="card_title">${APIarticle.title }</h2>
								<p class="card_text">
									<i class="fa-solid fa-heart fa-xl"></i>0 <i class="fa-solid fa-eye fa-xl"></i>0
								</p>
								<button class="btn1 card_btn">
									<a href="/usr/article/detail?id=${APIarticle.id}">더보기</a>
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
		<button>
			<a href="#" class="btn btn-sm btn-outline"><i class="fa-solid fa-backward"></i></a>
		</button>
		<button>
			<a href="#" class="btn btn-sm btn-outline"><i class="fa-solid fa-caret-left"></i></a>
		</button>
		<button>
			<a href="#" class="btn btn-sm btn-outline">1</a>
		</button>
		<button>
			<a href="#" class="btn btn-sm btn-outline">2</a>
		</button>
		<button>
			<a href="#" class="btn btn-sm btn-outline">3</a>
		</button>
		<button>
			<a href="#" class="btn btn-sm btn-outline">4</a>
		</button>
		<button>
			<a href="#" class="btn btn-sm btn-outline">5</a>
		</button>
		<button>
			<a href="#" class="btn btn-sm btn-outline"><i class="fa-solid fa-caret-right"></i></a>
		</button>
		<button>
			<a href="#" class="btn btn-sm btn-outline"><i class="fa-solid fa-forward"></i></a>
		</button>
	</div>
</main>

<%@ include file="../common/foot2.jspf"%>