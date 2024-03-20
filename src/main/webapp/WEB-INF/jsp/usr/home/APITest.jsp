<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="API TEST"></c:set>

<%@ include file="../common/head.jspf"%>

<script>
	
	async function getData() {
		const API_KEY = 'ixQo%2FUislf4YkHMgIBaDkwtFr%2FjmxRZLI55pNfsWntbXQewj3hrI50T6IoARyuZNWhk10ra5m39wMU57zRKeIw%3D%3D';
		const url = 'https://apis.data.go.kr/6300000/openapi2022/shard/getshard?serviceKey='+ API_KEY +'&pageNo=1&numOfRows=5';
		const response = await fetch(url);
		const data = await response.json();
		console.log("data", data);
	}
	
	getData();
	
	async function getData2() {
		const API_KEY = 'ixQo%2FUislf4YkHMgIBaDkwtFr%2FjmxRZLI55pNfsWntbXQewj3hrI50T6IoARyuZNWhk10ra5m39wMU57zRKeIw%3D%3D';
		const url = 'https://www.yuseong.go.kr/ys_parking/ysparkingList/ORP/getJSONData.do?_wadl&type=json';
		const response = await fetch(url);
		const data = await response.json();
		console.log("data", data);
	}
	
	getData2();
	
</script>
<script>
async function getData() {
    const API_KEY = 'ixQo%2FUislf4YkHMgIBaDkwtFr%2FjmxRZLI55pNfsWntbXQewj3hrI50T6IoARyuZNWhk10ra5m39wMU57zRKeIw%3D%3D';
    const url = 'https://apis.data.go.kr/6300000/openapi2022/shard/getshard?serviceKey=' + API_KEY + '&pageNo=1&numOfRows=5';

    try {
        const response = await fetch(url);
        const htmlText = await response.text();

        // Now you can work with the htmlText
        console.log("htmlText", htmlText);
    } catch (error) {
        console.error("Error fetching or parsing HTML:", error);
    }
}

getData();
</script>


<script>
const API_KEY = '7gBxrsj7WSHvOZjYdEQXGXuT9pq9L8NMGDZ9hzG7VnyftpPH7IIKkWxq2HkS94X9AsKLEzXCkaOZeH94lv28Bg%3D%3D';
var xhr = new XMLHttpRequest();
var url = 'http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst'; /*URL*/
var queryParams = '?' + encodeURIComponent('serviceKey') + '='+ API_KEY; /*Service Key*/
queryParams += '&' + encodeURIComponent('pageNo') + '=' + encodeURIComponent('1'); /**/
queryParams += '&' + encodeURIComponent('numOfRows') + '=' + encodeURIComponent('1000'); /**/
queryParams += '&' + encodeURIComponent('dataType') + '=' + encodeURIComponent('XML'); /**/
queryParams += '&' + encodeURIComponent('base_date') + '=' + encodeURIComponent('20240308'); /**/
queryParams += '&' + encodeURIComponent('base_time') + '=' + encodeURIComponent('0500'); /**/
queryParams += '&' + encodeURIComponent('nx') + '=' + encodeURIComponent('55'); /**/
queryParams += '&' + encodeURIComponent('ny') + '=' + encodeURIComponent('127'); /**/
xhr.open('GET', url + queryParams);
xhr.onreadystatechange = function () {
    if (this.readyState == 4) {
        alert('Status: '+this.status+'nHeaders: '+JSON.stringify(this.getAllResponseHeaders())+'nBody: '+this.responseText);
    }
};

xhr.send('');
</script>


<?xml version="1.0" encoding="UTF-8"?>
<response>
    <header>
        <resultCode>0</resultCode>
        <resultMsg>NORMAL_SERVICE</resultMsg>
    </header>
    <body>
        <dataType>XML</dataType>
        <items>
            <item>
                <baseDate>20210628</baseDate>
                <baseTime>0500</baseTime>
                <category>TMP</category>
                <fcstDate>20240303</fcstDate>
                <fcstTime>0600</fcstTime>
                <fcstValue>21</fcstValue>
                <nx>55</nx>
                <ny>127</ny>
            </item>
        </items>
        <numOfRows>10</numOfRows>
        <pageNo>1</pageNo>
        <totalCount>742</totalCount>
    </body>
</response>







<%@ include file="../common/foot.jspf"%>