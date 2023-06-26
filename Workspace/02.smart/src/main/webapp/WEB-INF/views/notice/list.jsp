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
	<h3 class="my-4">공지글 목록</h3>

	<div class="row">
		<c:if test="${loginInfo.admin eq 'Y'}">
			<div class="col-auto">
				<a href="new" class="btn btn-primary">새글쓰기</a>
			</div>
		</c:if>
	</div>
	<table class="tb-list">
		<colgroup>
			<col width="100px" />
			<col />
			<col width="120px" />
			<col width="120px" />
		</colgroup>
		<tr>
			<th>번호</th>
			<th>제목</th>
			<th>작성자</th>
			<th>작성일자</th>
		</tr>
		<c:if test="${empty list }">
			<tr>
				<td colspan="4">공지글이 없습니다</td>
			</tr>
		</c:if>

		<c:forEach items="${list}" var="vo">
			<tr>
				<td>${vo.no}</td>
				<td class="text-start"><a href="#">${vo.title}</a></td>
				<td>${vo.name}</td>
				<td>${vo.writedate}</td>
			</tr>
		</c:forEach>

	</table>
</body>
</html>