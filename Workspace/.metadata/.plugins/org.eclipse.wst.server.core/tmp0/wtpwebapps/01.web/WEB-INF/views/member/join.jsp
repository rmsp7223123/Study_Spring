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
	<h2>회원가입</h2>
	<form method="post" action="joinRequest" autocomplete="off">
		<table border="1">
			<tr>
				<th>성명</th>
				<td><input type='text' name='name'></td>
			</tr>
			<tr>
				<th>성별</th>
				<td><input type='radio' name='gender' value='남' checked>남
					<input type='radio' name='gender' value='여'>여</td>
			</tr>
			<tr>
				<th>이메일</th>
				<td><input type='email' name='email'></td>
			</tr>
			<tr>
				<th>나이</th>
				<td><input type='text' pattern="[0-9]{1,3}" name='age' required></td>
			</tr>
		</table>
		<input type="submit" value='회원가입(HttpServletRequest)'> <input
			type="submit" value='@RequestParam' onclick="action ='joinParam' ">
		<input type="submit" value='데이터객체' onclick="action = 'joinDataObject'">
		<input type="submit" value='@PathVariable'
			onclick="go_path( this.form )">
	</form>

	<script>
		function go_path(f) {
			/* action='joinPath/홍길동/남/hong@naver.com/20' */
			f.action = 'joinPath/' + f.name.value + '/' + f.gender.value + '/'
					+ f.email.value + '/' + f.age.value;
		}
	</script>
</body>
</html>