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
	<h3 class="my-4">방명록 안내</h3>
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
			<td colspan="5"></td>
		</tr>
	</table>

	<div class="btn-toolbar gap-2 my-3 justify-content-center">
		<a class="btn btn-primary">방명록목록</a> <a class="btn btn-primary">방명록수정</a>
		<a class="btn btn-primary">방명록삭제</a>
	</div>

	<jsp:include page="/WEB-INF/views/include/modal_image.jsp" />


	<script>
		//폰트어썸으로 만들어진 버튼의 경우 동적으로 다시 만들어지기 때문에 이벤트는 태그자체에 직접 등록 했을 때 발생되지 않음 => 문서에 등록
		$(document).on('click', '.file-download', function() {
			location = 'download?id=${vo.id}'
		})
	</script>

</body>
</html>