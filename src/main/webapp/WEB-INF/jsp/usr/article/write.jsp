<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="글쓰기"></c:set>
<%@ include file="../common/head2.jspf"%>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
    function openAddressPopup() {
        new daum.Postcode({
            oncomplete: function(data) {
                document.querySelector('.address').value = data.address;
                document.querySelector('.address-box').textContent = "주소 : " + data.address;
            }
        }).open();
    }
</script>


<script type="text/javascript">
	let ArticleWrite__submitFormDone = false;
	function ArticleWrite__submit(form) {
		if (ArticleWrite__submitFormDone) {
			return;
		}
		form.title.value = form.title.value.trim();
		if (form.title.value == 0) {
			alert('제목을 입력해주세요');
			return;
		}
		
		form.body.value = form.body.value.trim();
		if (form.body.value == 0){
			alert("내용을 입력해주세요");
			return;
		}

		$('#fileInput').attr('name', 'file__article__' + ${currentId} + '__extra__Img__1');

		ArticleWrite__submitFormDone = true;
		form.submit();
	}
</script>

<!-- 미리보기 -->
<!-- 이미관련 스타일 -->
<style>
/* 이미지 슬라이더 스타일 */
#slider {
	position: relative;
	overflow: hidden;
	margin: 20px auto 0 auto;
	border-radius: 4px;
}

/* 이미지 슬라이더의 목록 스타일 */
#slider ul {
	position: relative;
	margin: 0;
	padding: 0;
	height: 200px;
	list-style: none;
}

/* 각 슬라이드의 스타일 */
#slider ul li {
	position: relative;
	display: block;
	float: left;
	margin: 0;
	padding: 0;
	width: 500px;
	height: 300px;
	background: #ccc;
	text-align: center;
	line-height: 300px;
}

/* 슬라이드 이동 버튼의 스타일 */
a.control_prev, a.control_next {
	position: absolute;
	top: 40%;
	z-index: 999;
	display: block;
	padding: 4% 3%;
	width: auto;
	height: auto;
	background: #2a2a2a;
	color: #fff;
	text-decoration: none;
	font-weight: 600;
	font-size: 18px;
	opacity: 0.8;
	cursor: pointer;
}

/* 슬라이드 이동 버튼에 호버 효과를 적용하는 스타일 */
a.control_prev:hover, a.control_next:hover {
	opacity: 1;
	-webkit-transition: all 0.2s ease;
}

/* 이전 슬라이드로 이동하는 버튼의 스타일 */
a.control_prev {
	border-radius: 0 2px 2px 0;
}

/* 다음 슬라이드로 이동하는 버튼의 스타일 */
a.control_next {
	right: 0;
	border-radius: 2px 0 0 2px;
}

/* 슬라이더 옵션(자동 재생 체크박스)의 스타일 */
.slider_option {
	position: relative;
	margin: 10px auto;
	width: 160px;
	font-size: 18px;
}
</style>

<!-- 게시물 사진 관련 js -->
<script>
jQuery(document).ready(function ($) {
	  
	  // 자동 재생 체크박스 변경 이벤트 핸들러
	  $('#checkbox').change(function(){
	    // 체크박스가 선택된 경우, 3초마다 슬라이더를 오른쪽으로 이동하는 함수를 호출합니다.
	    setInterval(function () {
	        moveRight();
	    }, 3000);
	  });
	  
		var slideCount = $('#slider ul li').length;
		var slideWidth = $('#slider ul li').width();
		var slideHeight = $('#slider ul li').height();
		var sliderUlWidth = slideCount * slideWidth;
		/*
	  slideCount = $('#slider ul li').length;: 슬라이더 안에 있는 <ul> 태그 아래의 <li> 태그들의 수를 세어서 slideCount 변수에 저장합니다. 이는 전체 슬라이드의 개수를 나타냅니다.

	slideWidth = $('#slider ul li').width();: 슬라이드의 너비를 구하기 위해 첫 번째 슬라이드의 너비를 가져와서 slideWidth 변수에 저장합니다. 이는 모든 슬라이드의 너비가 같다고 가정합니다.

	slideHeight = $('#slider ul li').height();: 슬라이드의 높이를 구하기 위해 첫 번째 슬라이드의 높이를 가져와서 slideHeight 변수에 저장합니다. 이는 모든 슬라이드의 높이가 같다고 가정합니다.

	sliderUlWidth = slideCount * slideWidth;: 전체 슬라이드 목록의 너비를 계산하여 sliderUlWidth 변수에 저장합니다. 각 슬라이드의 너비에 전체 슬라이드의 수를 곱한 값이 됩니다. 이는 모든 슬라이드가 가로로 나열되어 있을 때 전체 너비를 나타냅니다.

	이렇게 계산된 값들은 이후에 슬라이더의 크기를 설정하거나 슬라이드의 이동에 사용될 수 있습니다.
	  */
	  
	  
		$('#slider').css({ width: slideWidth, height: slideHeight });
		
		$('#slider ul').css({ width: sliderUlWidth, marginLeft: - slideWidth });
		  
	  //마지막 슬라이드를 슬라이드 목록의 첫번째로 이동
	    $('#slider ul li:last-child').prependTo('#slider ul');
	    
	    //왼쪽으로 이동하는 함수
	    function moveLeft() {
	        $('#slider ul').animate({
	            left: + slideWidth
	        }, 200, function () {
	            $('#slider ul li:last-child').prependTo('#slider ul');
	            $('#slider ul').css('left', '');
	        });
	    };
	    //오른쪽으로 이동하는 함수
	    function moveRight() {
	        $('#slider ul').animate({
	            left: - slideWidth
	        }, 200, function () {
	            $('#slider ul li:first-child').appendTo('#slider ul');
	            $('#slider ul').css('left', '');
	        });
	    };
	   
	    //이전으로 이동하는 함수로 연결
	    $('a.control_prev').click(function () {
	        moveLeft();
	    });
	    
	    //다음으로 이동하는 함수로 연결
	    $('a.control_next').click(function () {
	        moveRight();
	    });

	});    

</script>

<!-- 지도 관련 스크립트 -->
<script>
	//버튼 클릭에 따라 지도 확대, 축소 기능을 막거나 풀고 싶은 경우에는 map.setZoomable 함수를 사용합니다
	function setZoomable(zoomable) {
		// 마우스 휠로 지도 확대,축소 가능여부를 설정합니다
		map.setZoomable(zoomable);
	}
</script>


<!-- 입력된 내용 미리보기에 반영해주는 스크립트 -->
<script>
function copyTitle(input) {
    var enteredValue = input.value;
    var titleBox = document.querySelector('.title-box');
    titleBox.textContent = "제목: " + enteredValue;
}

function copyBody(input) {
    var enteredValue = input.value;
    var bodyBox = document.querySelector('.body-box');
    bodyBox.textContent = "내용: " + enteredValue;
}

function copyAddress(input) {
    var enteredValue = input.value;
    var addressBox = document.querySelector('.address-box');
    addressBox.textContent = "주소: " + enteredValue;
    
}



/* 태그 */
function copyTag(input) {
    var tagDiv = document.querySelector('.tag-div');
    tagDiv.innerHTML = ""; // Clear previous tags
    
    var tags = input.value.split('#').filter(tag => tag.trim() !== ''); // Split input value by '#' and filter out empty tags
    tags.forEach(function(tag) {
        var button = document.createElement('button');
        button.classList.add('btn', 'btn-sm', 'btn-outline');
        button.textContent = tag;
        tagDiv.appendChild(button);
    });
}

/*사진  */
 function previewImage(event) {
    var fileInput = event.target;
    var fileList = fileInput.files;
    var previewUl = document.querySelector('.preview-ul');
    previewUl.innerHTML = ""; // Clear previous previews
    
    for (var i = 0; i < fileList.length; i++) {
        var file = fileList[i];
        var reader = new FileReader();
        
        reader.onload = function(event) {
            var imageUrl = event.target.result;
            var listItem = document.createElement('li');
            var image = document.createElement('img');
            image.classList.add('w-full', 'rounded-xl');
            image.src = imageUrl;
            listItem.appendChild(image);
            previewUl.appendChild(listItem);
        };
        
        reader.readAsDataURL(file);
    }
}


</script>

<main>
	<section class="flex" style="width: 100%; height: 100vh;">
		<div style="width: 50%; text-align: center;">
			<h1>글쓰기</h1>
			<form method="POST" action="../article/doWrite" enctype="multipart/form-data"
				onsubmit="ArticleWrite__submit(this); return false;" style="font-size: 25px; margin-top: 50px;">

				<input type="hidden" name="boardId" value="1" /> <input type="hidden" name="contentTypeId" value="12" />
				<input type="hidden" name="mapX" value="0"/>
				<input type="hidden" name="mapY" value="0"/>
				<input type="hidden" name="firstImage" value="0"/>
				<input type="hidden" name="firstImage2" value="0"/>

				<table class="join-box table-box-1" border="1">
					<tbody>
						<tr>
							<th>여행지</th>
							<td><input name="title" class="input input-bordered input-primary w-full max-w-xs" placeholder="여행지를 입력해주세요"
								autocomplete="off" onkeyup="copyTitle(this);" /></td>
						</tr>
						<tr>
							<th>내용</th>
							<td><input class="input input-bordered input-primary w-full max-w-xs" autocomplete="off" type="text"
								placeholder="내용 입력해주세요" name="body" onkeyup="copyBody(this);"/></td>
						</tr>
						<!-- <tr>
							<th>이미지경로</th>
							<td><input class="input input-bordered input-primary w-full max-w-xs" autocomplete="off" type="text"
								placeholder="이미지경로를 입력해주세요" name="firstImage" /></td>
						</tr>
						<tr>
							<th>이미지경로2</th>
							<td><input class="input input-bordered input-primary w-full max-w-xs" autocomplete="off" type="text"
								placeholder="이미지경로2를 입력해주세요" name="firstImage2" /></td>
						</tr> -->
						<!-- <tr>
							<th>x좌표</th>
							<td><input class="input input-bordered input-primary w-full max-w-xs" autocomplete="off" type="text"
								placeholder="x좌표를 입력해주세요" name="mapX" /></td>
						</tr>
						<tr>
							<th>y좌표</th>
							<td><input class="input input-bordered input-primary w-full max-w-xs" autocomplete="off" type="text"
								placeholder="y좌표 입력해주세요" name="mapY" /></td>
						</tr> -->
						<tr>
							<th>주소</th>
							<td><input class="address input input-bordered input-primary w-full max-w-xs" autocomplete="off" type="text"
								placeholder="주소를 입력해주세요" name="address" onkeyup="copyAddress(this);" readonly/>
								<button class="btn btn-outline" type="button" onclick="openAddressPopup()" style="margin-top: 10px;">주소 검색</button>
								</td>
						</tr>

						<tr>
							<th>태그</th>
							<td><input class="input input-bordered input-primary w-full max-w-xs" autocomplete="off" type="text"
								placeholder="예시 : #안녕#사랑해#가족과 함께" name="tag" onkeyup="copyTag(this);" /></td>
						</tr>
						<tr>
							<th>첨부 이미지</th>
							<td><input id="fileInput" placeholder="이미지를 선택해주세요" type="file" onchange="previewImage(event)" /></td>
							<!-- name="multipartFile"  -->
						</tr>
						<tr>
							<th></th>
							<td><input class="btn btn-outline btn-info" type="submit" value="작성하기" /></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<div style="width: 50%; text-align: center; overflow-y: auto; height: 100vh;">
			<h1>미리보기</h1>
			<div>
				<div>
					<h1 class="title-box" style="font-size: 3rem;'">제목: ~~~</h1>
					<%-- <h1>${rq.getImgUri(article.id)}</h1> --%>
				</div>
				<div class="line"></div>
				<div class="article-image-box">
					<div id="slider">
						<a href="#" class="control_next">></a> <a href="#" class="control_prev"><</a>
						<ul class="preview-ul">
						</ul>
					</div>
					<div class="slider_option">
						<input type="checkbox" id="checkbox"> <label for="checkbox">자동 넘기기</label>
					</div>
				</div>

				<div>
					<h1>여행지 정보</h1>
					<div class="address-box">주소 : ~~~</div>

				</div>
				<div>
					<h1>여행지 설명</h1>
					<div class="body-box">내용 : ~~~</div>
				</div>
				<div class="tag-div">
					<%-- <c:forEach var="tag" items="${tags}">
						<c:if test="${tag.length() != 0}">
							<button class="btn btn-sm btn-outline">${tag}</button>
						</c:if>
					</c:forEach> --%>
				</div>
				<button id="likeCount" class="btn btn-outline" style="width: 100px;">좋아요 수 : 0</button>
				<button id="likeButton" class="btn btn-outline btn-success" onclick="doGoodReaction(${param.id})">좋아요</button>
				<button class="btn btn-outline article-detail__hit-count" style="width: 100px;">조회수 : 0</button>
				<div>
					<c:if test="${article.userCanModify }">
						<a class="btn btn-outline btn-sm" href="../article/modify?id=${article.id }">수정</a>
					</c:if>
					<c:if test="${article.userCanDelete }">
						<a class="btn btn-outline btn-sm" onclick="if(confirm('정말 삭제하시겠습니까?') == false) return false;"
							href="../article/doDelete?id=${article.id }">삭제</a>
					</c:if>
				</div>




				<!-- 지도를 표시할 div 입니다 -->
				<div id="map" style="width: 100%; height: 350px;"></div>
				<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=4e61cb52e3e91adc0353005a87c20fd2"></script>
				<script>
			var mapx = 128.4049220662;
			var mapy = 35.9368172454;
			var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
			mapOption = {
				center : new kakao.maps.LatLng(35.9368172454, 128.4049220662), // 지도의 중심좌표
				level : 3
			// 지도의 확대 레벨
			};
			// 지도를 표시할 div와  지도 옵션으로  지도를 생성합니다
			var map = new kakao.maps.Map(mapContainer, mapOption);
			
			
			

			 //마커가 표시될 위치입니다 
			var markerPosition = new kakao.maps.LatLng(mapy, mapx);

			// 마커를 생성합니다
			var marker = new kakao.maps.Marker({
				position : markerPosition
			});

			// 마커가 지도 위에 표시되도록 설정합니다
			marker.setMap(map);  
		</script>
			</div>
	</section>
</main>




<%@ include file="../common/foot2.jspf"%>