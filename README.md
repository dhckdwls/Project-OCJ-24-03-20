![image](https://github.com/dhckdwls/Project-OCJ-24-03-20/assets/148337305/b8c313ce-7281-4052-b8f2-281c1c682b55)![image](https://github.com/dhckdwls/Project-OCJ-24-03-20/assets/148337305/9be19707-a1a0-4cd4-8419-8cf836ca38b5)## 소스코드
<br>
[기초작업](https://github.com/dhckdwls/2024_01_Spring_AM)
<br>
학원에서 배우던 코드들을 기반으로 개인 프로젝트를 진행
log4JDBC를 사용하던중 log의 용량이 너무 커져서 github에 push 작업이 안돼서 새로운 리포지터리 생성후 작업
<br>
# 사용방법
<br>
# sts 4.21
<br>
# 리포지터리 복제 후 사용 가능
<br>
## Mysql 설정
<br>
<img src="https://velog.velcdn.com/images/tama51/post/d5e473f3-a562-4f63-90eb-9729505a2b70/image.png">
<br>
## 프로젝트 파일에 있는 DB.sql 열기
<br>
## 메모장에 `#####` 으로 위에만 복사해서 사용 부분까지 복사해서 mysql에서 사용하면 됩니다.
<br>
## 프로젝트 간단한 ppt
<br>
[오창진_프로젝트.pptx](https://github.com/dhckdwls/Project-OCJ-24-03-20/files/15427356/_.pptx)
<br>
## 프로젝트 보고서
<br>
[오창진_프로젝트_결과보고서.xlsx](https://github.com/dhckdwls/Project-OCJ-24-03-20/files/15427358/_._.xlsx)
<br>
<br>
#### CSV 파일은 프로젝트에 src/main/resources 에 csv폴더에 다 들어어잇어서 추가 다운로드 필요하지 않습니다.
<br>
#### csv파일을 사용하여 작업한 부분이 없어서 특별하게 실행하지 않아도 됩니다.
<br>
#### 프로젝트 실행후 url 에 localhost:8081/readAndSaveToDB 입력 하시면 됩니다.
<br>
#### 그 후에 sql에서 select * from CSV 하면 확인할수 있습니다.
<br>

<br>
######################################################################################################
<br>

## 프로젝트 소개
<br>
개발 기간 : 2024.03 ~ 2024.05
<br>
개발 인원 : 개인 프로젝트
<br>
기술 스택
- 개발 언어 : java
- front-end : HTML, CSS, JavaScript, JQuery, TailWind CSS, Dasiy UI
- back-end : Spring Boot, LomBok, Tomcat, JSP, MyBatis
- API : daum 우편번호 주소 서비스, KakaoDevelopers KakaoMap, 공공데이터포털 한국관광공사 국문관광정보 서비스
- DB : MySQL
- 배포 및 관리 : github
-기타 개발 환경 : JDK, MAVEN, Spring Tool Suit4, window10

<br>
Random Trip - 국내 여행지에 대해 자유롭게 공유하고 의견을 나누고 위치를 확인하는 플랫폼
주제 선정 이유 : 부모님이나 친구들, 또는 혼자서 국내 여행을 다니는데 기억에 남는 곳을 다녀가고 싶었는데 검색을 하게 되면 하나의 지역과 유사한 곳이 나오기는 하지만 아닐때도 있다. 사진정보가 부족한 경우도 있고 한곳만 다녀가는 것이 아니기 때문에 여러 사람들과 경험을 공유하고 정보를 얻을 수 있는 사이트를 만들고 싶어서 선정하게 되었다.
<br>
데이터베이스 ERD
<br>
<img src="https://velog.velcdn.com/images/tama51/post/4b9637ab-1fc1-4fd5-8ff4-f4688539cd7c/image.png">
<br>

주요 기능
회원
- 로그인
- 로그아웃
- 회원가입
- 아이디찾기
- 비밀번호 찾기
- 회원탈퇴
- 마이페이지(회원정보,작성글,댓글,좋아요글 확인)

게시글
- 게시글 작성
- 게시글 리스트
- 게시글 수정
- 게시글 삭제
- 조회수

댓글
- 댓글 작성
- 댓글 수정
- 댓글 삭제

지도
- api활용 지도와 마커표시
<br>
메인 페이지(리스트)
<br>
<img src="https://velog.velcdn.com/images/tama51/post/9c12b87f-5d81-414f-a166-6ced1bade9ee/image.png">
<br>
상단바
왼쪽 메뉴보기를 누르면 사이드 바 메뉴가 보이게 됩니다. 문구는 메뉴 숨기기로 변경되고 다시 누르게 되면 사이드메뉴가 사라지게 됩니다.
가운데 RandomTrip을 누르게 되면 메인화면(리스트)페이지로 이동합니다.
오른쪽 로그인 버튼을 누르면 로그인 페이지로 이동하고 회원가입을 누르면 회원가입 페이지로 이동합니다.
로그인 완료 후에는 로그인 회원가입이 아닌 write, mypage, logout 버튼으로 바뀌게 됩니다.

검색
검색에는 제목, 내용, 제목 + 내용, 태그 로 검색이 가능합니다.
검색어에 부합하지 않는 상황에서는 아무것도 보이지 않습니다.

리스트부분에는 작성된 글들을 확인 할 수 있습니다. api를 사용해서 사진정보가 없는 게시글도 존재합니다. 물론 회원도 사진을 첨부하지 않을수 있어서 동일합니다.
사진첨부를 강제하지 않았습니다.

<br>
글작성
<br>
<img src="https://velog.velcdn.com/images/tama51/post/bcd2873f-c977-4ced-bce7-481dac43088d/image.png">
<br>
자신이 다녀온 여행지의 제목과 내용을 입력합니다.
주소는 api를 활용하기 위해 입력을 방지하고 주소 검색기능을 활용해서 입력되게 만들었습니다.
이 또한 다음 우편번호 주소 서비스를 활용했습니다.

작성된 내용을 토대로 오른쪽에 미리보기가 생성됩니다.
<br>
글수정
<br>
<img src="https://velog.velcdn.com/images/tama51/post/e66455f9-e4ab-4029-a5aa-28c241853960/image.png">
<br>
기존의 내용을 가져와서 글 수정 페이지로 이동해서 수정 가능합니다.

<br>
상세보기
<br>
<img src="https://velog.velcdn.com/images/tama51/post/8f9d92b2-46e9-4995-bc06-3f479b0b23d1/image.png">
<br>
로그인 하지 않은 멤버나 작성한 회원 즉 수정 삭제 권한이 없는 사람들은 수정 삭제 버튼이 보이지 않고 권한이 있는 사람들만 보이게 됩니다
글 작성시 입력된 정보를 토대로 보여주게 되고 지도에는 주소값을 사용해서 kakaoMap을 사용해서 마커와 위치를 보여주게 됩니다.

아래 부분에는 댓글작성이 가능합니다. 로그인 하지 않으면 작성할수 없고 그상태에서 작성시도시 로그인 페이지로 이동
수정과 삭제는 ajax 처리를 통해서 페이지를 새로 그리지 않도록 처리했습니다.

<br>
로그인 페이지
<br>
<img src="https://velog.velcdn.com/images/tama51/post/7e4b6377-60f2-4124-b0e2-ae1a63243811/image.png">
<br>
아이디와 비밀번호를 올바르게 입력하면 로그인 후 메인페이지(리스트)로 이동하게 됩니다.

<br>
회원가입 페이지
<br>
<img src="https://velog.velcdn.com/images/tama51/post/0b67dd40-2487-48e6-997a-6e8d6cf603c5/image.png">
<br>

아이디를 입력 받고 이미 존재하는 아이디인지 확인이 됩니다.
가입 가능 여부에 따라 아이디 입력란 밑에 가능과 불가능 문구가 나오게 됩니다.

비밀번호 입력란은 기본적으로 입력된 값이 ****모양으로 보이게 됩니다.
자신이 뭐라고 썻는지 확인할 수 있게 토글 스위치를 만들어서 텍스트가 보이게 만들수 있습니다.

회원관련 기본 정보를 입력받습니다.

<br>
마이페이지
<br>
<img src="https://velog.velcdn.com/images/tama51/post/1735797f-135a-4f7a-abaa-d3b0e9da652c/image.png">
<br>
회원정보와 작성글, 작성댓글, 좋아요 표시한 글을 볼수 있으며 회원정보를 수정할수 있습니다.
<br>
<br>
비밀번호 확인
<br>
<img src="https://velog.velcdn.com/images/tama51/post/037447fe-0d2e-43b9-b5a1-cb0032264676/image.png">
<br>
회원정보를 수정하기 전에 비밀번호를 한번 더 확인합니다.

<br>
회원정보 수정
<br>
<img src="https://velog.velcdn.com/images/tama51/post/2450fef6-17e9-4aaf-b3cb-0f77cefae664/image.png">
<br>
회원정보를 수정할수 있습니다.
비밀번호는 바꾸고 싶을떄만 바꿀 수 있도록 만들었습니다.

<br>
아이디 찾기
<br>
<img src="https://velog.velcdn.com/images/tama51/post/c4d6a0b4-2fa1-4396-9d94-b91f19ed673c/image.png">
<br>
회원가입시 입력한 이름과 이메일로 아이디를 찾을수 있습니다.
<br>
<img src="https://velog.velcdn.com/images/tama51/post/2de96824-b85b-4456-a1da-2a9d99f1bcb6/image.png">
<br>
올바른 정보를 입력하면 아이디를 찾을수 있습니다.
<br>
비밀번호 찾기
<br>
<img src="https://velog.velcdn.com/images/tama51/post/e9d6b504-5eb4-493e-9b6c-01f9f92ac6d4/image.png">
<br>
찾아낸 아이디와 회원가입시 등록한 이메일 주소르 입력하면 그 이메일 주소로 임시 비밀번호를 발송해 줍니다. 후에 로그인 후 비밀번호를 교체해서 사용하면 됩니다.
<br>
지도
<br>
<img src="https://velog.velcdn.com/images/tama51/post/8515d66a-2fe8-4f3b-af5c-5af4d289baf6/image.png">
<br>
지도 메뉴에 들어가면 여행지의 위치를 마커표시로 지도위에 그려줍니다.
마커를 클릭하면 인포윈도우에 여행지와 주소정보를 보여주게 됩니다. 다시 클릭하게 되면 사라지게 됩니다.
(길찾기 기능 미구현)
현재는 두 지점간의 직선거리만을 보여주게 됩니다.

## 문제 상황 및 해결

### api 관련 문제
공공데이터포털 API 호출시 encoding key 로 작업을 했다.
<img src="https://velog.velcdn.com/images/tama51/post/8f595de3-e713-4c89-873a-0d9d1977dfd6/image.png">
<br>
사용 설명서를 보면 2015년 1월 이후부터는 인코딩 불필요 하다고 되어 있어서
나는 encoding key를 사용해서 작업을 했다 그랬더니 계속 request에 대한  response data가 존재 하지 않았다.
문제가 뭘까 찾아보다가 공공데이터포털에서 test를 실행해 보았다.
<br>
<img src="https://velog.velcdn.com/images/tama51/post/b344f464-e7c8-4895-9296-1cdecb543dd1/image.png">
<br>

encoding key로 실행을 하게 되니 올바르지 않은 service key라고 한다.
<br>
<img src="https://velog.velcdn.com/images/tama51/post/1e52fe2a-2ae0-4edf-ad23-58675a405378/image.png">
<br>
그래서 decoding key로 서비스를 호출했더니 이번에는 올바른게 불러올수 있게 되었다
이 일 때문에 apiservice 에서 return 타입을 responsedata로 바꿔 주었다.
![api service](https://github.com/dhckdwls/Project-OCJ-24-03-20/assets/148337305/0cd0ea9a-70f9-494b-8820-92c357833607)

return 타입을 responsdata로 줘버려서 이 api가 호출이 잘 되었는지 알 수 있게 만들어놓았다.


### CSV 파일 문제
<br>
<img src="https://velog.velcdn.com/images/tama51/post/f5f88dc4-1e88-4dcb-920c-bc8bc72dbd43/image.png">
<br>

지역별 국내 관광지명, 주소, 분류, 방문자수, 순위 의 정보를 가지고 있는 csv 파일이다 100순위 까지 기록되어있다.

도별,구별, 군별 이렇게 나뉘어져서 총 250개의 파일이 존재 
행으로만 25000개정도 된다 
근데 나는 이 많은 데이터를 일일이 mysql을 사용해서 dbd에 넣어야 하는데 방법을 알 수 가 없었다.

알고보니까
<br>
![image](https://github.com/dhckdwls/Project-OCJ-24-03-20/assets/148337305/a3b73d90-2951-40fd-a227-14d206f638a1)
<br>

이렇게 해서 csv 파일 데이터를 직접 넣어줄수 있는 방법이 있다고 한다
근데 나는 검색을 잘못해서 java 에서 csv파일 읽어서 db에 저장하는 방법을 찾게 되었다

그랬더니 라이브러리 중에 openCSV 라는 라이브러리가 있다고 한다
이는 java에서 csv파일을 읽을수 있게 도와주는데 xml 문법이나 json 형식에 맞는 객체를 사용해서 읽어 올수 있게된다
나는 이걸 사용해서 데이터를 읽은 후에 repository에서 mybatis를 통해서 db에 저장할 수 있었다.

