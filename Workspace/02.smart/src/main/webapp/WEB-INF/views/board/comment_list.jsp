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
	<!-- 댓글이 없는 경우 -->
	<c:if test="${empty list}">
		<div class="col-md-10 content text-center">
			<div class="fs-5">등록된 댓글이 없습니다</div>
			<div>댓글을 남겨 주세요.</div>
		</div>
	</c:if>

	<!-- 댓글 작성자의 프로필 이미지 -->
	<c:forEach items="${list}" var="vo">
		<c:choose>
			<c:when test="${empty vo.profile}">
				<c:set var="profile"
					value="<i class='font-profile fa-regular fa-circle-user'></i>" />
			</c:when>
			<c:otherwise>
				<c:set var="profile"
					value="<img src='${vo.profile}'
					class='profile' />" />
			</c:otherwise>
		</c:choose>

		<!-- 댓글이 있는 경우 -->
		<div class="col-md-10 content border-bottom py-3 border-top">
			<div class="d-flec align-items-center mb-1">
				<span class="text-secondary me-2">${profile}</span> <span>${vo.name}
					[${vo.writedate}]</span>
			</div>
			<div class="comment">${vo.content}</div>
		</div>
		<div class="col-md-10 content border-bottom py-3 border-top">
			<div class="comment">댓글내용이 여기에 두번째</div>
		</div>
	</c:forEach>
</body>
</html>