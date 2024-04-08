<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="글쓰기"></c:set>
<%@ include file="../common/head2.jspf"%>

<script type="text/javascript">
	let ArticleWrite__submitFormDone = false;
	function ArticleWrite__submit(form) {
		if (ArticleWrite__submitFormDone) {
			return;
		}
		form.title.value = form.title.value.trim();
		if (form.title.value == 0) {
			alert('제목을 입력해주세요');
			return;
		}
		
		form.body.value = form.body.value.trim();
		if (form.body.value == 0){
			alert("내용을 입력해주세요");
			return;
		}

		$('#fileInput').attr('name', 'file__article__' + ${currentId} + '__extra__Img__1');

		ArticleWrite__submitFormDone = true;
		form.submit();
	}
</script>


<main style="text-align:center;">
	<h1>${currentId }</h1>
	<div>
		<h1 style="font-size:3rem;">글쓰기</h1>
	</div>
	<div class="line"></div>
	<div class="write-box" style="text-align:center;">
		<form method="POST" action="../article/doWrite" enctype="multipart/form-data" onsubmit="ArticleWrite__submit(this); return false;">
			
			<input type="hidden" name="boardId" value="1"/>
			<input type="hidden" name="contentTypeId" value="12"/>
			
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
						<th>주소</th>
						<td>
							<input class="input input-bordered input-primary w-full max-w-xs" autocomplete="off" type="text"
								placeholder="주소1을 입력해주세요" name="address" />
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
						<th>첨부 이미지</th>
						<td>
							<input id="fileInput" placeholder="이미지를 선택해주세요" type="file" name="multipartFile"/>
						</td>
					</tr>
					<tr>
						<th></th>
						<td>
							<input class="btn btn-outline btn-info" type="submit" value="작성하기" />
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
</main>



<%@ include file="../common/foot2.jspf"%>