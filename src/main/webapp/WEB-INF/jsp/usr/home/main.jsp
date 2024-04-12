<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인페이지</title>
</head>
<style>
.mainPage-top {
	height: 5vh;
}

.mainPage-top>button {
	margin-right: 10px;
}

/* 상단 끝 */

/* 중단 */
.mainPage-center {
	height: 20vh;
	text-align: center;
}

.mainPage-center>.center-text-box {
	height: 40%;
	font-size: 40px;
}

.mainPage-center>.mainPage-btn-box {
	height: 60%;
	display: flex;
	justify-content: space-around;
	align-items: center;
}

.mainPage-center>.mainPage-btn-box>button {
	border: 2px solid black;
	height: 100%;
	display: flex;
	justify-content: center;
	align-items: center;
}

.mainPage-center>.mainPage-btn-box>button:hover {
	background-color: black;
	color: white;
}

/* 하단 */
.mainPage-transform {
	height: 75vh;
	background-color: #afafaf;
}
</style>

<body>
	<!-- 테일윈드 불러오기 -->
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/tailwindcss/2.2.19/tailwind.min.css" />

	<!-- daisy ui 불러오기 -->
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/daisyui/4.6.1/full.css" />

	<!-- 폰트어썸 불러오기 -->
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.1/css/all.min.css">

	<!-- 제이쿼리 불러오기 -->
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

	<!-- 랜덤선택 ajax -->
	<script>
		function randomChoice() {
			$('.base-img').css('display', 'none');
			$('.random-result').css('display', 'block');

			var action = "/usr/article/random"
			$.get(action, {}, function(data) {
				$('.random-title').text(data.title);
				$('.random-body').text(data.body);
				$('.random-hitCount').text(data.hitCount);
				$('.random-goodReactionPoint').text(data.goodReactionPoint);
				if (data.firstImage && data.firstImage.length > 0) {
					$('.random-firstImage').attr('src', data.firstImage);
				}else{
					$('.random-firstImage').attr('src', data.firstImage);
				}
				if (data.firstImage2 && data.firstImage2.length > 0) {
					$('.random-firstImage2').attr('src', data.firstImage2);
				}

			}, 'json');
		}
	</script>

	<div class="mainPage-top flex items-center justify-end">
		<a href="/usr/article/list" onClick="confirm('메인으로 이동할래?')"><i class="fa-solid fa-x fa-2xl"></i></a>
	</div>

	<div class="mainPage-center">
		<div class="center-text-box">여행지 추천해드림</div>

		<div class="mainPage-btn-box">
			<button class="flex-1" onClick="randomChoice();">랜덤</button>
			<button class="flex-1">선택</button>
		</div>

	</div>
	<div class="mainPage-transform">
		<div class="base-img">
			<img src="http://tong.visitkorea.or.kr/cms/resource/86/2687486_image2_1.jpg" alt="" />
		</div>
		<div class="main random-result" style="display: none;">
			<div class="random-title"></div>
			<div class="random-body"></div>
			<div class="random-hitCount"></div>
			<div class="random-goodReactionPoint"></div>
			<img class="random-firstImage" src="" alt="" />
			<img class="random-firstImage2" src="" alt="" />
			

		</div>

		<div class="filter-page" style="display: none;"></div>

		<div class="filter-result" style="display: none;"></div>

	</div>

</body>
<!-- 123 -->
<%-- <div>
				<ul class="cards">

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
				</ul>
			</div> --%>

<!-- 123 -->

</html>