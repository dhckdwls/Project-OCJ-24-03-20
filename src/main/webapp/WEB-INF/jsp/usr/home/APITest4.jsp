<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="API TEST4"></c:set>

<%@ include file="../common/head.jspf"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>지도 이동시키기</title>

</head>
<body>

	<style>
#container {
	overflow: hidden;
	height: 700px;
	position: relative;
}

#btnRoadview, #btnMap {
	position: absolute;
	top: 5px;
	left: 5px;
	padding: 7px 12px;
	font-size: 14px;
	border: 1px solid #dbdbdb;
	background-color: #fff;
	border-radius: 2px;
	box-shadow: 0 1px 1px rgba(0, 0, 0, .04);
	z-index: 1;
	cursor: pointer;
}

#btnRoadview:hover, #btnMap:hover {
	background-color: #fcfcfc;
	border: 1px solid #c1c1c1;
}

#container.view_map #mapWrapper {
	z-index: 10;
}

#container.view_map #btnMap {
	display: none;
}

#container.view_roadview #mapWrapper {
	z-index: 0;
}

#container.view_roadview #btnRoadview {
	display: none;
}
</style>

	<div id="container" class="view_map">
		<div id="mapWrapper" style="width: 100%; height: 700px; position: relative;">
			<div id="map" style="width: 100%; height: 100%"></div>
			<!-- 지도를 표시할 div 입니다 -->
			<input type="button" id="btnRoadview" onclick="toggleMap(false)" title="로드뷰 보기" value="로드뷰">
		</div>
		<div id="rvWrapper" style="width: 100%; height: 700px; position: absolute; top: 0; left: 0;">
			<div id="roadview" style="height: 100%"></div>
			<!-- 로드뷰를 표시할 div 입니다 -->
			<input type="button" id="btnMap" onclick="toggleMap(true)" title="지도 보기" value="지도">
		</div>
	</div>

	<p>
		<button class="btn btn-outline btn-ghost" onclick="setCenter()">지도 중심좌표 이동시키기</button>
		<button class="btn btn-outline btn-ghost" onclick="panTo()">지도 중심좌표 대전으로 이동시키기</button>
	</p>
	<p>
		<button class="btn btn-outline btn-ghost" onclick="setZoomable(false)">지도 확대/축소 끄기</button>
		<button class="btn btn-outline btn-ghost" onclick="setZoomable(true)">지도 확대/축소 켜기</button>
	</p>

	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=4e61cb52e3e91adc0353005a87c20fd2"></script>
	<script>
		var lat;
		var lon;
		// 		주차장
		async function getData2() {
			const API_KEY = 'ixQo%2FUislf4YkHMgIBaDkwtFr%2FjmxRZLI55pNfsWntbXQewj3hrI50T6IoARyuZNWhk10ra5m39wMU57zRKeIw%3D%3D';
			const url = 'https://www.yuseong.go.kr/ys_parking/ysparkingList/ORP/getJSONData.do?_wadl&type=json';
			const response = await
			fetch(url);
			const data = await
			response.json();

			console.log("data", data);
			console.log(data.response);
			console.log(data.response.body);
			console.log(data.response.header);
			console.log(data.response.body.items);
			console.log(data.response.body.items[0]);
			console.log(data.response.body.items[0].item.addr);
			console.log(data.response.body.items[0].item.latitude);
			console.log(data.response.body.items[0].item.longitude);

			lat = data.response.body.items[0].item.latitude;
			lon = data.response.body.items[0].item.longitude;
		}

		getData2();

		// 		카카오지도
		var container = document.getElementById('container'), // 지도와 로드뷰를 감싸고 있는 div 입니다
		mapWrapper = document.getElementById('mapWrapper'), // 지도를 감싸고 있는 div 입니다
		btnRoadview = document.getElementById('btnRoadview'), // 지도 위의 로드뷰 버튼, 클릭하면 지도는 감춰지고 로드뷰가 보입니다 
		btnMap = document.getElementById('btnMap'), // 로드뷰 위의 지도 버튼, 클릭하면 로드뷰는 감춰지고 지도가 보입니다 
		rvContainer = document.getElementById('roadview'), // 로드뷰를 표시할 div 입니다
		mapContainer = document.getElementById('map'); // 지도를 표시할 div 입니다
		
		// 지도와 로드뷰 위에 마커로 표시할 특정 장소의 좌표입니다 
		var placePosition = new kakao.maps.LatLng(33.450701, 126.570667);
		
		var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
		mapOption = {
			center : new kakao.maps.LatLng(lat, lon), // 지도의 중심좌표
			level : 3
		// 지도의 확대 레벨
		};

		var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다
		
		// 로드뷰 객체를 생성합니다 
		var roadview = new kakao.maps.Roadview(rvContainer);
		
		// 로드뷰의 위치를 특정 장소를 포함하는 파노라마 ID로 설정합니다
		// 로드뷰의 파노라마 ID는 Wizard를 사용하면 쉽게 얻을수 있습니다 
		roadview.setPanoId(1023434522, placePosition);
		
		// 특정 장소가 잘보이도록 로드뷰의 적절한 시점(ViewPoint)을 설정합니다 
		// Wizard를 사용하면 적절한 로드뷰 시점(ViewPoint)값을 쉽게 확인할 수 있습니다
		roadview.setViewpoint({
		    pan: 321,
		    tilt: 0,
		    zoom: 0
		});
		
		
		// 로드뷰 초기화가 완료되면 
		kakao.maps.event.addListener(roadview, 'init', function() {

		    // 로드뷰에 특정 장소를 표시할 마커를 생성하고 로드뷰 위에 표시합니다 
		    var rvMarker = new kakao.maps.Marker({
		        position: placePosition,
		        map: roadview
		    });
		});
		
		// 지도와 로드뷰를 감싸고 있는 div의 class를 변경하여 지도를 숨기거나 보이게 하는 함수입니다 
		function toggleMap(active) {
		    if (active) {

		        // 지도가 보이도록 지도와 로드뷰를 감싸고 있는 div의 class를 변경합니다
		        container.className = "view_map"
		    } else {

		        // 지도가 숨겨지도록 지도와 로드뷰를 감싸고 있는 div의 class를 변경합니다
		        container.className = "view_roadview"   
		    }
		}
		
		var imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_red.png', // 마커이미지의 주소입니다    
		imageSize = new kakao.maps.Size(24, 35), // 마커이미지의 크기입니다
		imageOption = {
			offset : new kakao.maps.Point(27, 69)
		}; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.
		var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize,
				imageOption);

		// 버튼 클릭에 따라 지도 확대, 축소 기능을 막거나 풀고 싶은 경우에는 map.setZoomable 함수를 사용합니다
		function setZoomable(zoomable) {
			// 마우스 휠로 지도 확대,축소 가능여부를 설정합니다
			map.setZoomable(zoomable);
		}

		// 마커가 표시될 위치입니다 
		var positions = [ {
			title : '카카오',
			latlng : new kakao.maps.LatLng(33.450705, 126.570677)
		}, {
			title : '생태연못',
			latlng : new kakao.maps.LatLng(33.450936, 126.569477)
		}, {
			title : '텃밭',
			latlng : new kakao.maps.LatLng(33.450879, 126.569940)
		}, {
			title : '근린공원',
			latlng : new kakao.maps.LatLng(33.451393, 126.570738)
		}, {
			title : '주차장',
			latlng : new kakao.maps.LatLng(36.3551262, 127.34788)
		} ];

		for (var i = 0; i < positions.length; i++) {

			// 마커를 생성합니다
			var marker = new kakao.maps.Marker({
				map : map, // 마커를 표시할 지도
				position : positions[i].latlng, // 마커를 표시할 위치
				title : positions[i].title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
				image : markerImage,
				clickable: true
			// 마커 이미지 
			});
		}
		
		
		// 마커가 지도 위에 표시되도록 설정합니다
		marker.setMap(map);

		// 아래 코드는 지도 위의 마커를 제거하는 코드입니다
		// marker.setMap(null);

		// 마커가 드래그 가능하도록 설정합니다 
		marker.setDraggable(true);
		
		
		
		
		// 지도에 교통정보를 표시하도록 지도타입을 추가합니다
		map.addOverlayMapTypeId(kakao.maps.MapTypeId.TRAFFIC);    

		// 아래 코드는 위에서 추가한 교통정보 지도타입을 제거합니다
		// map.removeOverlayMapTypeId(kakao.maps.MapTypeId.TRAFFIC);

		function setCenter() {
			// 이동할 위도 경도 위치를 생성합니다 
			var moveLatLon = new kakao.maps.LatLng(33.452613, 126.570888);

			// 지도 중심을 이동 시킵니다
			map.setCenter(moveLatLon);
		}

		function panTo() {
			// 이동할 위도 경도 위치를 생성합니다 
			var moveLatLon = new kakao.maps.LatLng(lat, lon);

			// 지도 중심을 부드럽게 이동시킵니다
			// 만약 이동할 거리가 지도 화면보다 크면 부드러운 효과 없이 이동합니다
			map.panTo(moveLatLon);
		}
	</script>
</body>
</html>

<%@ include file="../common/foot.jspf"%>