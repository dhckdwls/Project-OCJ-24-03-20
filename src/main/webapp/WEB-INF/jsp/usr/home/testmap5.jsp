<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="TEST MAP3"></c:set>
<%@ include file="../common/head2.jspf"%>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=4e61cb52e3e91adc0353005a87c20fd2"></script>
<style>
    #map {
        width: 100%;
        height: 400px;
    }
</style>
<main>
    <h1>카카오 맵 경로 찾기</h1>
    <form id="directionForm">
        <label for="startAddress">출발지:</label>
        <input class="input input-bordered" type="text" id="startAddress" name="startAddress" placeholder="출발지 주소를 입력하세요">
        <br>
        <label for="destinationAddress">도착지:</label>
        <input class="input input-bordered" type="text" id="destinationAddress" name="destinationAddress" placeholder="도착지 주소를 입력하세요">
        <br>
        <!-- 버튼을 추가합니다. -->
        <button class="btn btn-outline" type="button" onclick="findRoute()">경로 찾기</button>
    </form>
    <div id="map"></div>

    <script>
        // API Key를 입력합니다.
        const KAKAO_MAP_API_KEY = '4e61cb52e3e91adc0353005a87c20fd2';

        // 지도를 표시할 div 요소를 가져옵니다.
        const mapContainer = document.getElementById('map');
        const options = {
            center: new kakao.maps.LatLng(37.566826, 126.9786567), // 초기 지도 중심 좌표를 서울 시청으로 설정합니다.
            level: 7 // 지도의 확대 레벨을 설정합니다.
        };
        const map = new kakao.maps.Map(mapContainer, options);

        // 경로를 찾고 지도에 표시하는 함수
        function findRoute() {
            // 출발지와 도착지 주소를 가져옵니다.
            const startAddress = document.getElementById('startAddress').value;
            const destinationAddress = document.getElementById('destinationAddress').value;

            // 출발지와 도착지 좌표를 찾아 경로를 그립니다.
            const geocoder = new kakao.maps.services.Geocoder();
            geocoder.addressSearch(startAddress, function (startResult, startStatus) {
                if (startStatus === kakao.maps.services.Status.OK) {
                    const startLatLng = new kakao.maps.LatLng(startResult[0].y, startResult[0].x);
                    geocoder.addressSearch(destinationAddress, function (destResult, destStatus) {
                        if (destStatus === kakao.maps.services.Status.OK) {
                            const destLatLng = new kakao.maps.LatLng(destResult[0].y, destResult[0].x);

                            // 경로를 검색합니다.
                            const options = {
                                // 자동차 경로 설정
                                travelMode: kakao.maps.services.TravelMode.TRANSIT
                            };
                            const rt = new kakao.maps.Routes(options);
                            rt.keywordSearch('길찾기', startLatLng, destLatLng, function (result, status) {
                                if (status === kakao.maps.services.Status.OK) {
                                    // 지도에 경로를 표시합니다.
                                    const polyline = new kakao.maps.Polyline({
                                        path: result[0].path,
                                        strokeWeight: 6,
                                        strokeColor: '#FF6A6A',
                                        strokeOpacity: 0.7,
                                        strokeStyle: 'solid'
                                    });
                                    polyline.setMap(map);

                                    // 출발지, 도착지 마커를 표시합니다.
                                    const startMarker = new kakao.maps.Marker({
                                        position: startLatLng,
                                        map: map
                                    });
                                    const destMarker = new kakao.maps.Marker({
                                        position: destLatLng,
                                        map: map
                                    });

                                    // 지도를 출발지와 도착지 중심으로 이동합니다.
                                    const bounds = new kakao.maps.LatLngBounds();
                                    bounds.extend(startLatLng);
                                    bounds.extend(destLatLng);
                                    map.setBounds(bounds);
                                } else {
                                    alert('경로를 찾을 수 없습니다.');
                                }
                            });
                        } else {
                            alert('도착지 주소를 찾을 수 없습니다.');
                        }
                    });
                } else {
                    alert('출발지 주소를 찾을 수 없습니다.');
                }
            });
        }
    </script>
</main>

<%@ include file="../common/foot2.jspf" %>
