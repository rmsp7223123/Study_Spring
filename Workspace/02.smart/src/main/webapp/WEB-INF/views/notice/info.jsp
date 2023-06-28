<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h3 class="my-4">공지글안내</h3>
	<table class="tb-row">
		<colgroup>
			<col width="180px" />
			<col />
			<col width="160px" />
			<col width="160px" />
			<col width="100px" />
			<col width="100px" />
		</colgroup>
		<tr>
			<th>제목</th>
			<td colspan="5">${vo.title}</td>
		</tr>
		<tr>
			<th>작성자</th>
			<td>${vo.name}</td>
			<th>작성일자</th>
			<td>${vo.writedate}</td>
			<th>조회수</th>
			<td>${vo.readcnt}</td>
		</tr>
		<tr>
			<th>내용</th>
			<td colspan="5">${fn: replace(vo.content, crlf, "<br>")}</td>
		</tr>
		<tr>
			<th>첨부파일</th>
			<td colspan="5"><c:if test="${!empty vo.filename}">
					<div class="row">
						<div class="col-auto d-flex gap-3 align-items-center">
							<span class="file-name">${vo.filename}</span> <i role="button"
								class="file-download fs-3 fa-solid fa-download"></i>

						</div>
					</div>
				</c:if></td>
		</tr>
	</table>
	<jsp:include page="/WEB-INF/views/include/modal_image.jsp" />

	<div class="btn-toolbar gap-2 my-3 justify-content-center">
		<a class="btn btn-primary" href="list">공지글목록</a>
		<!-- 관리자로 로그인 한 경우만 수정/삭제 가능 -->
		<c:if test="${loginInfo.admin eq 'Y'}">
			<a class="btn btn-primary" href="modify?id=${vo.id}">공지글수정</a>
			<a class="btn btn-primary"
				href="javascript:if(confirm('이 공지글을 삭제하시겠습니까?') ){location ='delete?id=${vo.id}'}">공지글삭제</a>
		</c:if>
	</div>

	<script>
		//폰트어썸으로 만들어진 버튼의 경우 동적으로 다시 만들어지기 때문에 이벤트는 태그자체에 직접 등록 했을 때 발생되지 않음 => 문서에 등록
		$(document).on('click', '.file-download', function() {
			location = 'download?id=${vo.id}'
		})
		// 첨부파일이 이미지 인 경우 미리보기 되게 처리
		$(function() {
			if (isImage("${vo.filename}")) {
				$('.file-name')
						.after(
								"<span class='file-preview'><img src='${vo.filepath}'></span>");
			}
		})
	</script>

</body>
</html>