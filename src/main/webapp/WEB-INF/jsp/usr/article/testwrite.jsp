<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="테스트 글쓰기"></c:set>
<%@ include file="../common/head2.jspf"%>

<main>
<h1>테스트 글쓰기</h1>

<div class="write-box" style="text-align:center;">
		<form class="table-box-type-1" method="POST" action="/usr/article/doWrite"">
			<input type="hidden" name="boardId" value="1"/>
			<table class="join-box table-box-1" border="1">
				<tbody>
					<tr>
						<th>여행지제목</th>
						<td>
							<input name="title"
								class="input input-bordered input-primary w-full max-w-xs" placeholder="여행지제목을 입력해주세요" autocomplete="off" />

						</td>
					</tr>
					<tr>
						<th>내용</th>
						<td>
							<input class="input input-bordered input-primary w-full max-w-xs" autocomplete="off" type="text"
								placeholder="내용 입력해주세요" name="body" />
						</td>
					</tr>
					<tr>
						<th>이미지경로</th>
						<td>
							<input class="input input-bordered input-primary w-full max-w-xs" autocomplete="off" type="text"
								placeholder="이미지경로를 입력해주세요" name="firstImage" />
						</td>
					</tr>
					<tr>
						<th>이미지경로2</th>
						<td>
							<input class="input input-bordered input-primary w-full max-w-xs" autocomplete="off" type="text"
								placeholder="이미지경로2를 입력해주세요" name="firstImage2" />
						</td>
					</tr>
					<tr>
						<th>x좌표</th>
						<td>
							<input class="input input-bordered input-primary w-full max-w-xs" autocomplete="off" type="text"
								placeholder="x좌표를 입력해주세요" name="mapX" />
						</td>
					</tr>
					<tr>
						<th>y좌표</th>
						<td>
							<input class="input input-bordered input-primary w-full max-w-xs" autocomplete="off" type="text"
								placeholder="y좌표 입력해주세요" name="mapY" />
						</td>
					</tr>
					<tr>
						<th>주소1</th>
						<td>
							<input class="input input-bordered input-primary w-full max-w-xs" autocomplete="off" type="text"
								placeholder="주소1을 입력해주세요" name="address1" />
						</td>
					</tr>
					<tr>
						<th>주소2</th>
						<td>
							<input class="input input-bordered input-primary w-full max-w-xs" autocomplete="off" type="text"
								placeholder="주소2를 입력해주세요" name="address2" />
						</td>
					</tr>
					
					<tr>
						<th>컨텐트타입아이디</th>
						<td>
							<input class="input input-bordered input-primary w-full max-w-xs" autocomplete="off" type="text"
								placeholder="컨텐트타입아이디를 입력해주세요" name="contentTypeId" />
						</td>
					</tr>
					<tr>
						<th>지역코드</th>
						<td>
							<input class="input input-bordered input-primary w-full max-w-xs" autocomplete="off" type="text"
								placeholder="지역코드를 입력해주세요" name="areaCode" />
						</td>
					</tr>
					<tr>
						<th>태그</th>
						<td>
							<input class="input input-bordered input-primary w-full max-w-xs" autocomplete="off" type="text"
								placeholder="지역코드를 입력해주세요" name="tag" />
						</td>
					</tr>
					<tr>
						<th></th>
						<td>
							<!-- <input class="btn btn-outline btn-info" type="submit" value="작성하기" /> -->
						</td>
					</tr>
				</tbody>
			</table>
		</form>
		
		<form action="usr/photo/write" method="POST">
		<input type="file" name="photo"/>
		<input type="file" name="photo"/>
		<input type="file" name="photo"/>
		<input type="file" name="photo"/>
		<input type="file" name="photo"/>
		<input type="file" name="photo"/>
		<input type="file" name="photo"/>
		<input type="file" name="photo"/>
		<input type="file" name="photo"/>
		<input type="file" name="photo"/>
		</form>
		
		
		<input class="btn btn-outline btn-info" type="submit" value="글 작성" />
	</div>
</main>
<%@ include file="../common/foot2.jspf"%>