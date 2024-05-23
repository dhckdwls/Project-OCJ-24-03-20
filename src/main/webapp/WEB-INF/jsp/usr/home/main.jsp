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

/* ###리스트카드 디자인################################################################################### */

/* 리스트 페이지 카드 디자인 */
/* Design */
*, *::before, *::after {
	box-sizing: border-box;
}

h1 {
	font-size: 24px;
	font-weight: 400;
	text-align: center;
}

img {
	height: auto;
	max-width: 100%;
	vertical-align: middle;
}

/* 사진아래더보기버튼 */
.btn1 {
	color: #ffffff;
	/* padding: 0.8rem; */
	font-size: 14px;
	text-transform: uppercase;
	border-radius: 4px;
	font-weight: 400;
	display: block;
	width: 100%;
	cursor: pointer;
	border: 1px solid rgba(255, 255, 255, 0.2);
	background: transparent;
}

.btn1>a {
	display: block;
	padding: 0.8rem;
}

/* 더보기 버튼 호버 했을때 */
.btn1:hover {
	background-color: rgba(255, 255, 255, 0.12);
}

.cards {
	display: flex;
	flex-wrap: wrap;
	list-style: none;
	margin: 0;
	padding: 0;
	text-align: center;
}

.cards_item {
	display: flex;
	padding: 1rem;
	flex-direction: column; /*여기*/
}

@media ( min-width : 40rem) {
	.cards_item {
		width: 50%;
	}
}

@media ( min-width : 56rem) {
	.cards_item {
		width: 33.3333%;
	}
}

.card {
	background-color: white;
	border-radius: 0.25rem;
	box-shadow: 0 20px 40px -14px rgba(0, 0, 0, 0.25);
	display: flex;
	flex-direction: column;
	overflow: hidden;
	border: 2px solid black;
}

.card_content {
	padding: 1rem;
	background: linear-gradient(to bottom left, skyblue 40%, deepskyblue 100%);
	/* 	background: linear-gradient(to bottom left, #EF8D9C 40%, #FFC39E 100%); */
}

.card_title {
	color: #ffffff;
	font-size: 2rem;
	font-weight: 700;
	letter-spacing: 1px;
	text-transform: capitalize;
	margin: 0px;
}

.card_text {
	color: #ffffff;
	font-size: 1rem;
	line-height: 1.5;
	margin-bottom: 1.25rem;
	font-weight: 400;
}

.card_image img {
	width: 100%;
	height: 200px;
}

.pageBtn>.btn {
	display: inline-block;
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
			$('.filter-page').css('display', 'none');
			$('.filter-result').css('display', 'none');

			var action = "/usr/article/random"
			$
					.get(
							action,
							{},
							function(data) {
								$('.random-title').text(data.title);
								$('.random-body').text(data.body);
								$('.random-hitCount').text(data.hitCount);
								$('.random-goodReactionPoint').text(
										data.goodReactionPoint);
								if (data.firstImage
										&& data.firstImage.length > 0) {
									$('.random-firstImage').attr('src',
											data.firstImage);
								} else {
									$('.random-firstImage').attr('src',
											data.firstImage);
								}
								if (data.firstImage2
										&& data.firstImage2.length > 0) {
									$('.random-firstImage2').attr('src',
											data.firstImage2);
								}
								var id = parseInt(data.id);
								$('.random-detail').attr('href',
										'/usr/article/detail?id=' + id).text(
										"더보기");

								$('.random-text')
										.html(
												`<i class="fa-solid fa-heart fa-xl"></i><span class="random-goodPoint"></span> <i class="fa-solid fa-eye fa-xl"></i><span class="random-hitCount"></span><i
										class="fa-solid fa-comment-dots fa-xl"></i><span class="random-repliesCount"></span>`);

								$('.random-goodPoint').text(
										data.goodReactionPoint);
								$('.random-hitCount').text(data.hitCount);
								$('.random-repliesCount').text(
										data.extra__repliesCnt);
							}, 'json');


		}

		function tryRandom() {
			randomChoice();
			return;
		}
	</script>

	<!-- 필터선택 -->
	<script>
		function filterChoice() {
			$('.base-img').css('display', 'none');
			$('.random-result').css('display', 'none');
			$('.filter-page').css('display', 'block');
			$('.filter-result').css('display', 'none');

		}
	</script>

	<script>
    var locations = new Set(); // Using a Set to store unique values

    function choose(el) {
        el.classList.toggle('btn-active');
        var location = el.textContent.trim(); // Get the text content of the button
        if (el.classList.contains('btn-active')) {
            locations.add(location); // Add the location to the set
        } else {
            locations.delete(location); // Remove the location from the set
        }
        console.log(locations); // Print the updated set
    }
</script>

	<div class="mainPage-top flex items-center justify-end">
		<a href="/usr/article/list" onClick="if(confirm('메인으로 이동할래?') == false) return false"><i
			class="fa-solid fa-x fa-2xl"></i></a>
	</div>

	<div class="mainPage-center">
		<div class="center-text-box">여행지 추천해드림</div>

		<div class="mainPage-btn-box">
			<button class="flex-1" onClick="randomChoice();">랜덤</button>
			<button class="flex-1" onclick="filterChoice();">선택</button>
		</div>

	</div>
	<div class="mainPage-transform">
		<div class="base-img">
			<img src="http://tong.visitkorea.or.kr/cms/resource/86/2687486_image2_1.jpg" alt="" />
		</div>
		<div class="main random-result" style="display: none;">
			<ul class="cards">

				<li class="cards_item">
					<div class="card">
						<div class="card_image">
							<img class="random-firstImage" src="">
						</div>
						<div class="card_content">
							<h2 class="card_title random-title"></h2>
							<p class="card_text random-text"></p>
							<button class="btn1 card_btn">
								<a class="random-detail" href="">더보기</a>
							</button>
						</div>
					</div>
				</li>
			</ul>

			<button class="btn btn-outline" onclick="tryRandom();">다시뽑기</button>
		</div>


		<div class="filter-page" style="display: none;">
			<div calss="first">
				지역선택
				<button class="btn btn-outline" onclick="choose(this);">서울</button>
				<button class="btn btn-outline" onclick="choose(this);">인천</button>
				<button class="btn btn-outline" onclick="choose(this);">대전</button>
				<button class="btn btn-outline" onclick="choose(this);">대구</button>
				<button class="btn btn-outline" onclick="choose(this);">광주</button>
				<button class="btn btn-outline" onclick="choose(this);">부산</button>
				<button class="btn btn-outline" onclick="choose(this);">울산</button>
				<button class="btn btn-outline" onclick="choose(this);">세종</button>
				<button class="btn btn-outline" onclick="choose(this);">경기</button>
				<button class="btn btn-outline" onclick="choose(this);">강원</button>
				<button class="btn btn-outline" onclick="choose(this);">충북</button>
				<button class="btn btn-outline" onclick="choose(this);">충남</button>
				<button class="btn btn-outline" onclick="choose(this);">경북</button>
				<button class="btn btn-outline" onclick="choose(this);">경남</button>
				<button class="btn btn-outline" onclick="choose(this);">전북</button>
				<button class="btn btn-outline" onclick="choose(this);">전남</button>
				<button class="btn btn-outline" onclick="choose(this);">제주</button>

			</div>
			<div class="second">태그선택 ${tags }</div>
		</div>


		<div class="filter-result" style="display: none;"></div>

	</div>

</body>

</html>