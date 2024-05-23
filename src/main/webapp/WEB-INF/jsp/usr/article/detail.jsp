<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="ARTICLE DETAIL"></c:set>
<%@ include file="../common/head2.jspf"%>
<style>
.img-box img {
	width: 500px;
	height: auto;
}
</style>
<!-- 변수 -->
<script>
	const params = {};
	params.id = parseInt('${param.id}');
	params.memberId = parseInt('${loginedMemberId}');
	
	console.log(params);
	console.log(params.memberId);
	
	var isAlreadyAddGoodRp = ${isAlreadyAddGoodRp};
	var isAlreadyAddBadRp = ${isAlreadyAddBadRp};
	
	
</script>


<!-- 조회수 -->
<script>
	function ArticleDetail__doIncreaseHitCount() {
		const localStorageKey = 'article__' + params.id + '__alreadyView';

		if (localStorage.getItem(localStorageKey)) {
			return;
		}

		localStorage.setItem(localStorageKey, true);

		$.get('../article/doIncreaseHitCountRd', {
			id : params.id,
			ajaxMode : 'Y'
		}, function(data) {
			$('.article-detail__hit-count').empty().html(data.data1);
		}, 'json');
	}

	$(function() {
		// 		ArticleDetail__doIncreaseHitCount();
		setTimeout(ArticleDetail__doIncreaseHitCount, 2000);
	});
</script>

<!-- 좋아요 싫어요  -->
<script>
	<!-- 좋아요 싫어요 버튼	-->
	function checkRP() {
		if(isAlreadyAddGoodRp == true){
			$('#likeButton').toggleClass('btn-outline');
		}else if(isAlreadyAddBadRp == true){
			$('#DislikeButton').toggleClass('btn-outline');
		}else {
			return;
		}
	}
	
	function doGoodReaction(articleId) {
		if(isNaN(params.memberId) == true){
			if(confirm('로그인 해야해. 로그인 페이지로 가실???')){
				var currentUri = encodeURIComponent(window.location.href);
				window.location.href = '../member/login?afterLoginUri=' + currentUri; // 로그인 페이지에 원래 페이지의 uri를 같이 보냄
			}
			return;
		}
		
		$.ajax({
			url: '/usr/reactionPoint/doGoodReaction',
			type: 'POST',
			data: {relTypeCode: 'article', relId: articleId},
			dataType: 'json',
			success: function(data){
				console.log(data);
				console.log('data.data1Name : ' + data.data1Name);
				console.log('data.data1 : ' + data.data1);
				console.log('data.data2Name : ' + data.data2Name);
				console.log('data.data2 : ' + data.data2);
				if(data.resultCode.startsWith('S-')){
					var likeButton = $('#likeButton');
					var likeCount = $('#likeCount');
					var DislikeButton = $('#DislikeButton');
					var DislikeCount = $('#DislikeCount');
					
					if(data.resultCode == 'S-1'){
						likeButton.toggleClass('btn-outline');
						likeCount.text(data.data1);
					}else if(data.resultCode == 'S-2'){
						DislikeButton.toggleClass('btn-outline');
						DislikeCount.text(data.data2);
						likeButton.toggleClass('btn-outline');
						likeCount.text(data.data1);
					}else {
						likeButton.toggleClass('btn-outline');
						likeCount.text(data.data1);
					}
					
				}else {
					alert(data.msg);
				}
		
			},
			error: function(jqXHR,textStatus,errorThrown) {
				alert('좋아요 오류 발생 : ' + textStatus);

			}
			
		});
	}
	
	
	
	function doBadReaction(articleId) {
		
		if(isNaN(params.memberId) == true){
			if(confirm('로그인 해야해. 로그인 페이지로 가실???')){
				var currentUri = encodeURIComponent(window.location.href);
				window.location.href = '../member/login?afterLoginUri=' + currentUri; // 로그인 페이지에 원래 페이지의 uri를 같이 보냄
			}
			return;
		}
		
	 $.ajax({
			url: '/usr/reactionPoint/doBadReaction',
			type: 'POST',
			data: {relTypeCode: 'article', relId: articleId},
			dataType: 'json',
			success: function(data){
				console.log(data);
				console.log('data.data1Name : ' + data.data1Name);
				console.log('data.data1 : ' + data.data1);
				console.log('data.data2Name : ' + data.data2Name);
				console.log('data.data2 : ' + data.data2);
				if(data.resultCode.startsWith('S-')){
					var likeButton = $('#likeButton');
					var likeCount = $('#likeCount');
					var DislikeButton = $('#DislikeButton');
					var DislikeCount = $('#DislikeCount');
					
					if(data.resultCode == 'S-1'){
						DislikeButton.toggleClass('btn-outline');
						DislikeCount.text(data.data2);
					}else if(data.resultCode == 'S-2'){
						likeButton.toggleClass('btn-outline');
						likeCount.text(data.data1);
						DislikeButton.toggleClass('btn-outline');
						DislikeCount.text(data.data2);
		
					}else {
						DislikeButton.toggleClass('btn-outline');
						DislikeCount.text(data.data2);
					}
			
				}else {
					alert(data.msg);
				}
			},
			error: function(jqXHR,textStatus,errorThrown) {
				alert('싫어요 오류 발생 : ' + textStatus);
			}
			
		});
	}
	
	$(function() {
		checkRP();
	});
</script>

<!-- 댓글 -->
<script>
		var ReplyWrite__submitDone = false;

		function ReplyWrite__submit(form) {
			if (ReplyWrite__submitDone) {
				alert('이미 처리중입니다');
				return;
			}
			console.log(123);
			
			console.log(form.body.value);
			
			if (form.body.value.length < 3) {
				alert('댓글은 3글자 이상 입력해');
				form.body.focus();
				return;
			}

			ReplyWrite__submitDone = true;
			form.submit();

		}
</script>


<!-- 댓글 수정 -->
<script>
function toggleModifybtn(replyId) {
    console.log(replyId);
    
    $('#modify-btn-'+replyId).hide();
    $('#save-btn-'+replyId).show();
    $('#cancle-btn-'+replyId).show();
    $('#reply-'+replyId).hide();
    $('#modify-form-'+replyId).show();

    // Retrieve the current comment content from the span element
    var currentText = $('#reply-'+replyId).text();
    // Set the value of the input field with the current comment content
    $('#modify-form-'+replyId+' input[name="reply-text-'+replyId+'"]').val(currentText);
}

function toggleModifyHideBtn(replyId) {
    $('#modify-btn-'+replyId).show();
    $('#save-btn-'+replyId).hide();
    $('#cancle-btn-'+replyId).hide();
    $('#reply-'+replyId).show();
    $('#modify-form-'+replyId).hide();
    // Clear the input field value when canceling the edit
    $('#modify-form-'+replyId+' input[name="reply-text-'+replyId+'"]').val('');
}


function doModifyReply(replyId) {
	 console.log(replyId); // 디버깅을 위해 replyId를 콘솔에 출력
	    
	    // form 요소를 정확하게 선택
	    var form = $('#modify-form-' + replyId);
	    console.log(form); // 디버깅을 위해 form을 콘솔에 출력

	    // form 내의 input 요소의 값을 가져옵니다
	    var text = form.find('input[name="reply-text-' + replyId + '"]').val();
	    console.log(text); // 디버깅을 위해 text를 콘솔에 출력
	    
	    // 만약 입력된 내용이 3자 미만이라면 경고 메시지를 표시하고 함수를 종료합니다
	    if (text.length < 3) {
	        alert('3자 이상의 내용을 입력하세요.');
	        return;
	    }

	    // form의 action 속성 값을 가져옵니다
	    var action = form.attr('action');
	    console.log(action); // 디버깅을 위해 action을 콘솔에 출력
	
    $.post({
    	url: '/usr/reply/doModify', // 수정된 URL
        type: 'POST', // GET에서 POST로 변경
        data: { id: replyId, body: text }, // 서버에 전송할 데이터
        success: function(data) {
        	$('#modify-form-'+replyId).hide();
        	$('#reply-'+replyId).text(data);
        	$('#reply-'+replyId).show();
        	$('#save-btn-'+replyId).hide();
        	$('#modify-btn-'+replyId).show();
        	$('#cancle-btn-'+replyId).hide();
        },
        error: function(xhr, status, error) {
            alert('댓글 수정에 실패했습니다: ' + error);
        }
	})
}

</script>

<!-- 댓글창 엔터 막기 -->
<script>
    $(document).ready(function() {
        // 입력 필드에서 Enter 키 누를 때 줄 바꿈 추가
        $('.reply-box').on('keypress', 'input[type="text"]', function(event) {
            if (event.key === "Enter") { // Enter 키를 눌렀을 때
                // 줄 바꿈 문자("\n") 추가
                var cursorPosition = this.selectionStart; // 현재 커서 위치 가져오기
                var currentValue = $(this).val(); // 현재 입력 값 가져오기
                var newValue = currentValue.substring(0, cursorPosition) + "\n" + currentValue.substring(cursorPosition); // 줄 바꿈 추가
                $(this).val(newValue); // 새로운 값으로 입력 필드 업데이트
                
                // 이벤트 전파 중지
                event.preventDefault();
                event.stopPropagation();

                // 커서 위치 조정
                this.setSelectionRange(cursorPosition + 1, cursorPosition + 1);
            }
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

<!-- 게시물 사진 관련 스타일 -->
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

<main class="flex flex-col items-center" style="text-align: center;">
	<div style="width: 100%; text-align: center;">
		<div>
			<h1 style="font-size: 3rem;'">${article.title }</h1>
			<%-- <h1>${rq.getImgUri(article.id)}</h1> --%>
		</div>
		<div class="line"></div>
		<div class="article-image-box">
			<div id="slider">
				<a href="#" class="control_next">></a> <a href="#" class="control_prev"><</a>
				<ul>

					<li style="background: #aaa;"><img src="${rq.getImgUri(article.id)}"
						onerror="${rq.profileFallbackImgOnErrorHtml}" alt="" /></li>
					<c:if test="${article.firstImage.trim().length() != 0 }">
						<li style="background: #aaa;"><img src="${article.firstImage }" alt="" /></li>
					</c:if>
					<c:if test="${article.firstImage2.trim().length() != 0 }">
						<li style="background: #aaa;"><img src="${article.firstImage2 }" alt="" /></li>
					</c:if>
					<li><img class="w-full rounded-xl" src="${rq.getImgUri(article.id)}"
						onerror="${rq.profileFallbackImgOnErrorHtml}" alt="" /></li>
					<li style="background: #aaa;">네번째</li>

				</ul>
			</div>
			<div class="slider_option">
				<input type="checkbox" id="checkbox"> <label for="checkbox">자동 넘기기</label>
			</div>
		</div>

		<div class="line"></div>

		<div>
			<h1>여행지 정보</h1>
			<div>주소 : ${article.address}</div>

		</div>
		<div>
			<h1>여행지 설명</h1>
			<div>${article.body }</div>
		</div>
		<div>
			<c:forEach var="tag" items="${tags}">
				<c:if test="${tag.length() != 0}">
					<button class="btn btn-sm btn-outline">${tag}</button>
				</c:if>
			</c:forEach>
		</div>
		<button id="likeCount" class="btn btn-outline" style="width: 100px;">${article.goodReactionPoint }</button>
		<button id="likeButton" class="btn btn-outline btn-success" onclick="doGoodReaction(${param.id})">좋아요</button>
		<button class="btn btn-outline article-detail__hit-count" style="width: 100px;">조회수 : ${article.hitCount }</button>
		<div>
			<c:if test="${article.userCanModify }">
				<a class="btn btn-outline btn-sm" href="../article/modify?id=${article.id }">수정</a>
			</c:if>
			<c:if test="${article.userCanDelete }">
				<a class="btn btn-outline btn-sm" onclick="if(confirm('정말 삭제하시겠습니까?') == false) return false;"
					href="../article/doDelete?id=${article.id }">삭제</a>
			</c:if>
		</div>
	</div>
	<div class="line"></div>
	<div style="width: 90%; height: 100%; border: 2px solid black;">
		<!-- 지도를 표시할 div 입니다 -->
		<div id="map" style="width: 100%; height: 350px;"></div>
		<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=4e61cb52e3e91adc0353005a87c20fd2&libraries=services"></script>
		<script>
		var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
	    mapOption = {
	        center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
	        level: 3 // 지도의 확대 레벨
	    };  

	// 지도를 생성합니다    
	var map = new kakao.maps.Map(mapContainer, mapOption); 

	// 주소-좌표 변환 객체를 생성합니다
	var geocoder = new kakao.maps.services.Geocoder();

	// 주소로 좌표를 검색합니다
	geocoder.addressSearch('${article.address}', function(result, status) {

	    // 정상적으로 검색이 완료됐으면 
	     if (status === kakao.maps.services.Status.OK) {

	        var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

	        // 결과값으로 받은 위치를 마커로 표시합니다
	        var marker = new kakao.maps.Marker({
	            map: map,
	            position: coords
	        });

	        // 인포윈도우로 장소에 대한 설명을 표시합니다
	        var infowindow = new kakao.maps.InfoWindow({
	            content: '<div style="width:150px;text-align:center;padding:6px 0;">여기요!</div>'
	        });
	        infowindow.open(map, marker);

	        // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
	        map.setCenter(coords);
	    } 
	});    
			/* var address = "${article.address}";
			console.log(address);
			 var mapx = ${article.mapX};
			var mapy = ${article.mapY};
			var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
			mapOption = {
				center : new kakao.maps.LatLng(mapy, mapx), // 지도의 중심좌표
				level : 2
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
			marker.setMap(map);  */
		</script>

	</div>
	<p>
		<button onclick="setZoomable(false)" class="btn btn-sm btn-outline">지도 확대/축소 끄기</button>
		<button onclick="setZoomable(true)" class="btn btn-sm btn-outline">지도 확대/축소 켜기</button>
	</p>

	<div class="line"></div>
	<div class="reply-box" style="font-size: 20px; width: 100%;">
		<div class="badge badge-outline badge-lg" style="font-size: 15px; right: 0;">${repliesCount }개</div>
		<table class="styled-table" style="width: 100%;">
			<tbody>
				<tr>
					<td style="width: 10%;">작성자</td>
					<td style="width: 15%;">마지막 등록날짜</td>
					<td style="width: 50%;">내용</td>
					<td style="width: 10%;">좋아요</td>
					<td style="width: 7%;">수정</td>
					<td style="width: 7%;">삭제</td>
				</tr>
				<c:forEach var="reply" items="${replies }">
					<tr>
						<td>${reply.extra__writer }</td>
						<td>${reply.updateDate }</td>
						<td><span id="reply-${reply.id }">${reply.body }</span>
							<form method="POST" id="modify-form-${reply.id }" style="display: none;" action="">
								<input class="input input-bordered" type="text" value="${reply.body }" name="reply-text-${reply.id }" />
							</form></td>
						<td>${reply.goodReactionPoint }</td>
						<c:if test="${reply.userCanModify }">
							<td><c:if test="${reply.userCanModify }">
									<button onclick="toggleModifybtn('${reply.id}');" id="modify-btn-${reply.id }" style="white-space: nowrap;"
										class="btn btn-outline">수정</button>
									<button onclick="doModifyReply('${reply.id}');" style="white-space: nowrap; display: none;"
										id="save-btn-${reply.id }" class="btn btn-outline">저장</button>
									<button class="btn btn-outline" id="cancle-btn-${reply.id }" style="display: none;"
										onclick="toggleModifyHideBtn('${reply.id}');">수정취소</button>
								</c:if></td>
						</c:if>
						<c:if test="${reply.userCanDelete }">
							<td><a class="btn btn-outline" href="/usr/reply/doDelete?id=${reply.id }" class="delete-btn">삭제</a></td>
						</c:if>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div>
			<c:if test="${!rq.isLogined() }">
				<form action="">
					<input style="border: 1px solid black;" type="text" placeholder="로그인 후 댓글작성 이용가능" disabled /> <a
						href="/usr/member/login" class="btn btn-sm btn-outline">로그인</a>
				</form>
			</c:if>
			<c:if test="${rq.isLogined() }">
				<form action="/usr/reply/doWrite" method="POST">
					<input type="hidden" name="relTypeCode" value="article" /> <input type="hidden" name="relId"
						value="${article.id }" /> <input style="border: 1px solid black;" type="text" placeholder="내용을 입력해주세요"
						name="body" autocomplete="off" />
					<button type="submit" class="btn btn-sm btn-outline">댓글작성</button>
				</form>
			</c:if>

		</div>
		<div class="replyPageBtn" style="margin-bottom: 20px; text-align: center;">
			<div class="line"></div>

			<div class="btn-group">
				<c:forEach begin="1" end="${replyPagesCount }" var="i">
					<button>
						<a class="btn btn-sm btn-outline ${param.page == i ? 'btn-active' : '' }"
							href="/usr/article/detail?id=${article.id}&page=${i}">${i }</a>
					</button>
				</c:forEach>
			</div>
</main>
<%@ include file="../common/foot2.jspf"%>