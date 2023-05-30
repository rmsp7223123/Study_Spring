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
	<h1>확인1Model</h1>
	오늘은${today }입니다
	<a href="<c:url value='/'/>">홈으로</a>
	<h1>확인2 ModelAndView</h1>
	현재시각은 ${now }입니다
</body>
</html>