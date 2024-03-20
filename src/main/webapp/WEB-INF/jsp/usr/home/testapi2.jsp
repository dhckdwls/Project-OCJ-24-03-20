<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="ARTICLE DETAIL"></c:set>
<%@ include file="../common/head2.jspf"%>
<main>
	<div>서울 1 인천 2 대전 3 대구 4 광주 5 부산 6 울산 7 세종 8 경기 31 강원 32 충북 33 충나 34 경북 35 경남 36 전북 37 전남 38 제주 39</div>
	<div>관광타입(12:관광지, 14:문화시설, 15:축제공연행사, 25:여행코스, 28:레포츠, 32:숙박, 38:쇼핑, 39:음식점) ID</div>
	<div>서울 12 (13040) 인천2</div>
	<script>
    const apiUrl = 'http://apis.data.go.kr/B551011/KorService1/areaBasedList1';
    //const serviceKey = '7gBxrsj7WSHvOZjYdEQXGXuT9pq9L8NMGDZ9hzG7VnyftpPH7IIKkWxq2HkS94X9AsKLEzXCkaOZeH94lv28Bg%3D%3D'; // 귀하의 실제 인증 키로 대체하세요
    const serviceKey = '7gBxrsj7WSHvOZjYdEQXGXuT9pq9L8NMGDZ9hzG7VnyftpPH7IIKkWxq2HkS94X9AsKLEzXCkaOZeH94lv28Bg=='; // 귀하의 실제 인증 키로 대체하세요
    const pageNo = 1;
    const numOfRows = 20000;
    const arrange = 'A';//(A=제목순, C=수정일순, D=생성일순)
    const areaCode = ''; // 필요에 따라 변경하세요
    const contentTypeId = 12; // 관광 명소에 해당하는 contentTypeId
    const mobileApp = 'AppTest';
    const mobileOS = 'ETC';
	/*  */
	var arr1 = [];
	/*  */
    const url = apiUrl+'?serviceKey='+ serviceKey +'&pageNo=' + pageNo +'&numOfRows='+ numOfRows +'&MobileApp=' + mobileApp +'&MobileOS='+ mobileOS +'&arrange='+ arrange +'&areaCode=' + areaCode +'&contentTypeId='+ contentTypeId;

    fetch(url)
      .then(response => response.text())
      .then(xmlText => {
        // XML을 DOM으로 파싱
        const parser = new DOMParser();
        const xmlDoc = parser.parseFromString(xmlText, 'application/xml');

        // 특정 태그의 값을 가져오기
        const items = xmlDoc.getElementsByTagName('item');
        for (let i = 0; i < items.length; i++) {
          const title = items[i].getElementsByTagName('title')[0].textContent;
          const addr1 = items[i].getElementsByTagName('addr1')[0].textContent;
          const addr2 = items[i].getElementsByTagName('addr2')[0].textContent;
          const firstimage = items[i].getElementsByTagName('firstimage')[0].textContent;
          const firstimage2 = items[i].getElementsByTagName('firstimage2')[0].textContent;
          const mapx = items[i].getElementsByTagName('mapx')[0].textContent;
          const mapy = items[i].getElementsByTagName('mapy')[0].textContent;
          const tel = items[i].getElementsByTagName('tel')[0].textContent;
          const zipcode = items[i].getElementsByTagName('zipcode')[0].textContent;
          const mlevel = items[i].getElementsByTagName('mlevel')[0].textContent;
          
          
          
          
         

          // 필요한 정보를 로그에 출력 또는 다른 용도로 활용
          console.log(title);
          console.log(addr1);
          console.log(addr2);
          console.log(firstimage);
          console.log(firstimage2);
          console.log(mapx);
          console.log(mapy);
          console.log(tel);
          console.log(zipcode);
          console.log(mlevel);
   
          
          
          
          
        }
        console.log(items);

        console.log(xmlDoc);
        
      })
      .catch(error => {
        console.error('에러 발생:', error);
      });
  </script>

	<response> 
	<header></header>
	<body>
		<items>
		 <item>
		<addr1></addr1>
		<addr2></addr2>
		<areacode></areacode>
		<booktour></booktour>
		<cat1></cat1>
		<cat2></cat2>
		<cat3></cat3>
		<contenttypeid></contenttypeid>
		<createdtime></createdtime>
		<firstimage></firstimage>
		<firstimage2></firstimage2>
		<cpyrhtdivcd></cpyrhtdivcd>
		<mapx></mapx>
		<mapy></mapy>
		<mlevel></mlevel>
		<modifiedtime></modifiedtime>
		<sigungucode></sigungucode>
		<tel></tel>
		<title></title>
		<zipcode></zipcode>
		 </item>
		 </items>
		<numofrows></numofrows>
		<pageno></pageno>
		<totalcount></totalcount>
	</body>
	</response>




</main>
<%@ include file="../common/foot2.jspf"%>