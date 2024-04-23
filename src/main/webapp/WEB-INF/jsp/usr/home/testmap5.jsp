<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="TEST MAP3"></c:set>
<%@ include file="../common/head2.jspf"%>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=4e61cb52e3e91adc0353005a87c20fd2"></script>

<main>

<div>
  <label for="departure">출발지:</label>
  <input type="text" id="departure" placeholder="출발지를 입력하세요">
</div>
<div>
  <label for="destination">목적지:</label>
  <input type="text" id="destination" placeholder="목적지를 입력하세요">
</div>
<button onclick="getDirections()">길찾기</button>

<div id="map" style="width: 100%; height: 500px;"></div>

<script>
var mapContainer = document.getElementById('map');
var mapOptions = {
    center: new kakao.maps.LatLng(37.566826, 126.9786567), // 서울의 좌표
    level: 7 // 지도 확대 레벨
};
var map = new kakao.maps.Map(mapContainer, mapOptions);

function getDirections() {
  var departure = document.getElementById('departure').value;
  var destination = document.getElementById('destination').value;
  
  var url = "https://apis-navi.kakaomobility.com/v1/directions?origin=" + encodeURIComponent(departure) + "&destination=" + encodeURIComponent(destination) + "&car_type=1&car_fuel=GASOLINE&alternatives=false&road_details=false";

  var xhr = new XMLHttpRequest();
  xhr.open('GET', url);
  xhr.setRequestHeader('Authorization', 'KakaoAK c5251b97be72c5363c435074456a8239');
  xhr.onreadystatechange = function() {
    if (xhr.readyState === XMLHttpRequest.DONE) {
      if (xhr.status === 200) {
        var data = JSON.parse(xhr.responseText);
        var route = data.routes[0];
        var sections = route.sections;
        sections.forEach(function(section) {
          var roads = section.roads;
          roads.forEach(function(road) {
            var vertexes = road.vertexes;
            var polyline = new kakao.maps.Polyline({
              path: vertexes.map(function(vert) {
                return new kakao.maps.LatLng(vert[1], vert[0]);
              }),
              strokeWeight: 5,
              strokeColor: 'blue',
              strokeOpacity: 0.7,
              strokeStyle: 'solid'
            });
            polyline.setMap(map);
          });
        });
      } else {
        console.error('Failed to fetch directions:', xhr.status);
      }
    }
  };
  xhr.send();
}
</script>
</main>

<%@ include file="../common/foot2.jspf" %>
