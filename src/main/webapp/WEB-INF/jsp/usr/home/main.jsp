<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인페이지</title>
</head>
<style>
.mainPage-top {
  height : 5vh;
}


.mainPage-top > button {
  margin-right:10px;
}

/* 상단 끝 */

/* 중단 */
.mainPage-center{
  height : 20vh;
  text-align : center;
}

.mainPage-center > .center-text-box{
  height : 40%;
  font-size : 40px;
  
}

.mainPage-center > .mainPage-btn-box {
  height: 60%;
  display: flex;
  justify-content: space-around;
  align-items: center;
}

.mainPage-center > .mainPage-btn-box > button {
  border:2px solid black;
  height: 100%; 
  display: flex;
  justify-content: center;
  align-items: center; 
}

.mainPage-center > .mainPage-btn-box > button:hover{
  background-color: black;
  color:white;
}


/* 하단 */
.mainPage-transform{
  height : 75vh;
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

<div class="mainPage-top flex items-center justify-end">
  <button><i class="fa-solid fa-x fa-2xl"></i></button>
</div>

<div class="mainPage-center">
  <div class="center-text-box">여행지 추천해드림</div>

  <div class="mainPage-btn-box">
    <button class="flex-1">랜덤</button>
    <button class="flex-1">선택</button>
  </div>

</div>
<div class="mainPage-transform">
<img src="http://tong.visitkorea.or.kr/cms/resource/86/2687486_image2_1.jpg" alt="" />





</div>

</body>
</html>