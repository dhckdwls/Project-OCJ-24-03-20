![image](https://github.com/dhckdwls/Project-OCJ-24-03-20/assets/148337305/e703093e-ea62-4088-97e4-83b476c7af8a)![image](https://github.com/dhckdwls/Project-OCJ-24-03-20/assets/148337305/98bb1e9c-3295-4786-a66d-227b0eaacb4a)## 소스코드
[기초작업](https://github.com/dhckdwls/2024_01_Spring_AM)
학원에서 배우던 코드들을 기반으로 개인 프로젝트를 진행
log4JDBC를 사용하던중 log의 용량이 너무 커져서 github에 push 작업이 안돼서 새로운 리포지터리 생성후 작업

# 사용방법

# sts 4.21

# 리포지터리 복제 후 사용 가능

## Mysql 설정

<img src="https://velog.velcdn.com/images/tama51/post/d5e473f3-a562-4f63-90eb-9729505a2b70/image.png">

## 프로젝트 파일에 있는 DB.sql 열기

## 메모장에 `#####` 으로 위에만 복사해서 사용 부분까지 복사해서 mysql에서 사용하면 됩니다.

## 프로젝트 간단한 ppt
[오창진_프로젝트.pptx](https://github.com/dhckdwls/Project-OCJ-24-03-20/files/15427356/_.pptx)
## 프로젝트 보고서
[오창진_프로젝트_결과보고서.xlsx](https://github.com/dhckdwls/Project-OCJ-24-03-20/files/15427358/_._.xlsx)

#### CSV 파일은 프로젝트에 src/main/resources 에 csv폴더에 다 들어어잇어서 추가 다운로드 필요하지 않습니다.
#### csv파일을 사용하여 작업한 부분이 없어서 특별하게 실행하지 않아도 됩니다.
#### 프로젝트 실행후 url 에 localhost:8081/readAndSaveToDB 입력 하시면 됩니다.
#### 그 후에 sql에서 select * from CSV 하면 확인할수 있습니다.

######################################################################################################


## 프로젝트 소개

개발 기간 : 2024.03 ~ 2024.05

개발 인원 : 개인 프로젝트

기술 스택
- 개발 언어 : java
- front-end : HTML, CSS, JavaScript, JQuery, TailWind CSS, Dasiy UI
- back-end : Spring Boot, LomBok, Tomcat, JSP, MyBatis
- API : daum 우편번호 주소 서비스, KakaoDevelopers KakaoMap, 공공데이터포털 한국관광공사 국문관광정보 서비스
- DB : MySQL
- 배포 및 관리 : github
-기타 개발 환경 : JDK, MAVEN, Spring Tool Suit4, window10

Random Trip - 국내 여행지에 대해 자유롭게 공유하고 의견을 나누고 위치를 확인하는 플랫폼
주제 선정 이유 : 부모님이나 친구들, 또는 혼자서 국내 여행을 다니는데 기억에 남는 곳을 다녀가고 싶었는데 검색을 하게 되면 하나의 지역과 유사한 곳이 나오기는 하지만 아닐때도 있다. 사진정보가 부족한 경우도 있고 한곳만 다녀가는 것이 아니기 때문에 여러 사람들과 경험을 공유하고 정보를 얻을 수 있는 사이트를 만들고 싶어서 선정하게 되었다.

데이터베이스 ERD
<img src="https://velog.velcdn.com/images/tama51/post/4b9637ab-1fc1-4fd5-8ff4-f4688539cd7c/image.png">

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

메인 페이지(리스트)
<img src="https://velog.velcdn.com/images/tama51/post/9c12b87f-5d81-414f-a166-6ced1bade9ee/image.png">
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

글작성
<img src="https://velog.velcdn.com/images/tama51/post/bcd2873f-c977-4ced-bce7-481dac43088d/image.png">
자신이 다녀온 여행지의 제목과 내용을 입력합니다.
주소는 api를 활용하기 위해 입력을 방지하고 주소 검색기능을 활용해서 입력되게 만들었습니다.
이 또한 다음 우편번호 주소 서비스를 활용했습니다.

작성된 내용을 토대로 오른쪽에 미리보기가 생성됩니다.

글수정
<img src="https://velog.velcdn.com/images/tama51/post/e66455f9-e4ab-4029-a5aa-28c241853960/image.png">
기존의 내용을 가져와서 글 수정 페이지로 이동해서 수정 가능합니다.

상세보기
<img src="https://velog.velcdn.com/images/tama51/post/8f9d92b2-46e9-4995-bc06-3f479b0b23d1/image.png">
로그인 하지 않은 멤버나 작성한 회원 즉 수정 삭제 권한이 없는 사람들은 수정 삭제 버튼이 보이지 않고 권한이 있는 사람들만 보이게 됩니다
글 작성시 입력된 정보를 토대로 보여주게 되고 지도에는 주소값을 사용해서 kakaoMap을 사용해서 마커와 위치를 보여주게 됩니다.

아래 부분에는 댓글작성이 가능합니다. 로그인 하지 않으면 작성할수 없고 그상태에서 작성시도시 로그인 페이지로 이동
수정과 삭제는 ajax 처리를 통해서 페이지를 새로 그리지 않도록 처리했습니다.

로그인 페이지
<img src="https://velog.velcdn.com/images/tama51/post/7e4b6377-60f2-4124-b0e2-ae1a63243811/image.png">
아이디와 비밀번호를 올바르게 입력하면 로그인 후 메인페이지(리스트)로 이동하게 됩니다.

회원가입 페이지
<img src="https://velog.velcdn.com/images/tama51/post/0b67dd40-2487-48e6-997a-6e8d6cf603c5/image.png">

아이디를 입력 받고 이미 존재하는 아이디인지 확인이 됩니다.
가입 가능 여부에 따라 아이디 입력란 밑에 가능과 불가능 문구가 나오게 됩니다.

비밀번호 입력란은 기본적으로 입력된 값이 ****모양으로 보이게 됩니다.
자신이 뭐라고 썻는지 확인할 수 있게 토글 스위치를 만들어서 텍스트가 보이게 만들수 있습니다.

회원관련 기본 정보를 입력받습니다.

마이페이지
<img src="https://velog.velcdn.com/images/tama51/post/1735797f-135a-4f7a-abaa-d3b0e9da652c/image.png">
회원정보와 작성글, 작성댓글, 좋아요 표시한 글을 볼수 있으며 회원정보를 수정할수 있습니다.

비밀번호 확인
<img src="https://velog.velcdn.com/images/tama51/post/037447fe-0d2e-43b9-b5a1-cb0032264676/image.png">
회원정보를 수정하기 전에 비밀번호를 한번 더 확인합니다.

회원정보 수정
<img src="https://velog.velcdn.com/images/tama51/post/2450fef6-17e9-4aaf-b3cb-0f77cefae664/image.png">
회원정보를 수정할수 있습니다.
비밀번호는 바꾸고 싶을떄만 바꿀 수 있도록 만들었습니다.

아이디 찾기
<img src="https://velog.velcdn.com/images/tama51/post/c4d6a0b4-2fa1-4396-9d94-b91f19ed673c/image.png">
회원가입시 입력한 이름과 이메일로 아이디를 찾을수 있습니다.
<br>
<img src="https://velog.velcdn.com/images/tama51/post/2de96824-b85b-4456-a1da-2a9d99f1bcb6/image.png">
올바른 정보를 입력하면 아이디를 찾을수 있습니다.

비밀번호 찾기
<img src="https://velog.velcdn.com/images/tama51/post/e9d6b504-5eb4-493e-9b6c-01f9f92ac6d4/image.png">
찾아낸 아이디와 회원가입시 등록한 이메일 주소르 입력하면 그 이메일 주소로 임시 비밀번호를 발송해 줍니다. 후에 로그인 후 비밀번호를 교체해서 사용하면 됩니다.

지도
<img src="https://velog.velcdn.com/images/tama51/post/8515d66a-2fe8-4f3b-af5c-5af4d289baf6/image.png">
지도 메뉴에 들어가면 여행지의 위치를 마커표시로 지도위에 그려줍니다.
마커를 클릭하면 인포윈도우에 여행지와 주소정보를 보여주게 됩니다. 다시 클릭하게 되면 사라지게 됩니다.
(길찾기 기능 미구현)
현재는 두 지점간의 직선거리만을 보여주게 됩니다.





