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
	<div>
		<a href="<c:url value='/'/>">홈으로</a>
	</div>
	<div>
		<a href="<c:url value='/member'/>">회원가입 화면으로</a>
	</div>
	<h1>회원정보[${method }]</h1>
	<div>성명 :${name }</div>
	<div>성별 :${gender }</div>
	<div>이메일 :${email }</div>
	<div>나이 : ${age }</div>
	<hr />
	<div>성명 :${vo.name }</div>
	<div>성별 :${vo.gender }</div>
	<div>이메일 :${vo.email }</div>
	<div>나이 : ${vo.age }</div>
</body>
</html>