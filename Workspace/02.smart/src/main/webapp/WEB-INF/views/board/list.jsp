<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h3 class="my-4">방명록 목록</h3>
	<div class="row justify-content-between mb-3">
		<div class="col-auto">
			<a href="new" class="btn btn-primary">새글쓰기</a>
		</div>
	</div>
	<table class="tb-list">
		<colgroup>
			<col width="100px" />
			<col />
			<col width="120px" />
			<col width="120px" />
			<col width="100px" />
		</colgroup>
		<tr>
			<th>번호</th>
			<th>제목</th>
			<th>작성자</th>
			<th>작성일자</th>
			<th>조회수</th>
		</tr>
		<c:if test="${empty page.list}">
			<tr>
				<td colspan="5">방명록 글이 없습니다</td>
			</tr>
		</c:if>
		<c:forEach items="${page.list}" var="vo">
			<tr>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>

		</c:forEach>
	</table>
</body>
</html>