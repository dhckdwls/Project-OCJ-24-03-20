<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="API TEST5"></c:set>

<%@ include file="../common/head.jspf"%>



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
        console.log('Status: '+this.status+'nHeaders: '+JSON.stringify(this.getAllResponseHeaders())+'nBody: '+this.responseText);
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