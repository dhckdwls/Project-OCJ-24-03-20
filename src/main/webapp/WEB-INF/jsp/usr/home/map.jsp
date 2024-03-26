<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="지도"></c:set>
<%@ include file="../common/head2.jspf"%>

<main>
    <div>
        <input type="text" placeholder="출발지" />
        <input type="text" placeholder="도착지" />
        <button class="btn btn-outline">입력</button>
    </div>

    <div id="map" style="width: 100%; height: 800px;"></div>

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

    // 주소-좌표 변환 객체 생성
    var geocoder = new kakao.maps.services.Geocoder();

    // 각 주소와 해당하는 제목을 반복 처리하여 지도에 마커 추가
    <c:forEach var="item" items="${titleAddress}">
        var title = '${item.split("/")[0]}';
        var address = '${item.split("/")[1]}';

        // Closure to capture the current values of title and address
        (function(title, address) {
            geocoder.addressSearch(address, function(result, status) {
                // 검색이 정상적으로 완료된 경우
                if (status === kakao.maps.services.Status.OK) {
                    var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

                    // 검색 결과로 받은 위치에 마커 생성
                    var marker = new kakao.maps.Marker({
                        map: map,
                        position: coords
                    });

                    // 인포 윈도우에 제목과 주소 정보를 표시
                    var infowindow = new kakao.maps.InfoWindow({
                        content: '<div style="width:200px;text-align:center;padding:10px;">' +
                            '<div style="font-weight:bold;margin-bottom:5px;">' + title + '</div>' +
                            '<div>' + address + '</div>' +
                            '</div>'
                    });

                    // 마커를 클릭했을 때 인포 윈도우를 열고 닫을 수 있는 이벤트 등록
                    kakao.maps.event.addListener(marker, 'click', function() {
                        // Check if the info window is currently open
                        if (infowindow.getMap()) {
                            // Close the info window if it's open
                            infowindow.close();
                        } else {
                            // Open the info window if it's closed
                            infowindow.open(map, marker);
                        }
                    });
                }
            });
        })(title, address);
    </c:forEach>
</script>
</main>

<%@ include file="../common/foot2.jspf"%>