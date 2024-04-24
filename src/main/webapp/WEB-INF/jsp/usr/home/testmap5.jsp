<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="TEST MAP3"></c:set>
<%@ include file="../common/head2.jspf"%>
<main>
<!-- Daum 우편번호 서비스 스크립트 -->
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

<form action="" method="">
    <input class="input input-bordered input-primary" type="text" placeholder="출발지 입력" name="startpoint" id="startAddress" readonly />
    <button class="btn btn-outline" type="button" onclick="openAddressPopup('startAddress')">출발지검색</button>
    <input class="input input-bordered input-primary" type="text" placeholder="도착지 입력" name="endpoint" id="endAddress" readonly />
    <button class="btn btn-outline" type="button" onclick="openAddressPopup('endAddress')">도착지검색</button>
    <button class="btn btn-outline" type="submit">길 찾기</button>
</form>

<script>
// 주소 검색 팝업 열기
function openAddressPopup(inputId) {
    new daum.Postcode({
        oncomplete: function(data) {
            var address = data.address; // 선택한 주소
            document.getElementById(inputId).value = address; // 입력 창에 주소 설정
        }
    }).open();
}

</script>
	
	<!-- rest_api_key = c5251b97be72c5363c435074456a8239 -->
    <!-- 지도를 표시할 div -->
    <div id="map" style="width:100%;height:800px;"></div>
    <script type="text/javascript"
        src="//dapi.kakao.com/v2/maps/sdk.js?appkey=4e61cb52e3e91adc0353005a87c20fd2&libraries=services"></script>
     <script>
     var mapContainer = document.getElementById('map'); // 지도를 표시할 div
     var mapOption = {
         center: new kakao.maps.LatLng(36.4329471, 127.4203292), // 지도의 중심좌표
         level: 3 // 지도의 확대 레벨
     };

     // 지도를 생성합니다
     var map = new kakao.maps.Map(mapContainer, mapOption);
     </script>

    

</main>
<%@ include file="../common/foot2.jspf"%>
