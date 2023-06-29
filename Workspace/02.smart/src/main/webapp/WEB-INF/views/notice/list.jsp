<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
.input-group .form-select {
	flex: initial;
	width: 130px;
}
</style>
</head>
<body>
	<h3 class="my-4">공지글 목록</h3>
	<form method="post" action="list">
		<input type="hidden" name="curPage" value="1" />
		<div class="row justify-content-between mb-3">
			<div class="col-auto">
				<div class="input-group">
					<select name="search" id="" class="form-select">
						<option value="all" ${page.search eq 'all' ? 'selected' : ''}>전체</option>
						<option value="title"
							<c:if test="${page.search eq 'title'}">selected</c:if>>제목</option>
						<option value="content"
							${page.search eq 'content' ? 'selected' : '' }>내용</option>
						<option value="writer"
							${page.search eq 'writer' ? 'selected' : '' }>작성자</option>
						<option value="t_c" ${page.search eq 't_c' ? 'selected' : '' }>제목+내용</option>
					</select> <input type="text" value="${page.keyword}" name="keyword"
						class="form-control" />
					<button class="btn btn-primary px-3">
						<i class="fa-solid fa-magnifying-glass"></i>
					</button>
				</div>
			</div>
			<c:if test="${loginInfo.admin eq 'Y'}">
				<div class="col-auto">
					<a href="new" class="btn btn-primary">새글쓰기</a>
				</div>
			</c:if>
		</div>
	</form>
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
			<th>첨부파일</th>
		</tr>
		<c:if test="${empty page.list }">
			<tr>
				<td colspan="4">공지글이 없습니다</td>
			</tr>
		</c:if>

		<c:forEach items="${page.list}" var="vo">
			<tr>
				<td>${vo.no}</td>
				<td class="text-start"><a class="text-link"
					href="info?id=${vo.id}&curPage=${page.curPage}&search=${page.search}&keyword=${page.keyword}">${vo.title}</a></td>
				<td>${vo.name}</td>
				<td>${vo.writedate}</td>
				<td><c:if test="${!empty vo.filename }">
						<i class="fa-regular fa-file"></i>
					</c:if></td>
			</tr>
		</c:forEach>

	</table>
	<jsp:include page="/WEB-INF/views/include/page.jsp" />
</body>
</html>