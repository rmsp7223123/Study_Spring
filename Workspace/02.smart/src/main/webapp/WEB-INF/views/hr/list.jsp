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
	<h3 class="my-4">사원목록</h3>
	<form method="post" action="list">
		<div class="row my-3 justify-content-between">
			<div class="col-auto">
				<label class="me-3"> 부서명 </label> <select name="department_id"
					class="form-control col" onchange="submit()">
					<option value="-1">전체</option>
					<c:forEach items="${departments }" var="d">
						<option value="${d.department_id }"
							${d.department_id eq department_id ? 'selected' : '' }>${d.department_name }</option>
					</c:forEach>
				</select>
			</div>
			<div class="col-auto">
				<button type="button" onclick="location='new'"
					class="btn btn-primary">사원등록</button>
			</div>
		</div>
	</form>
	<table class="tb-list">
		<colgroup>
			<col width="80px">
			<col width="250px">
			<col width="300px">
			<col width="300px">
			<col width="140px">
		</colgroup>
		<tr>
			<th>사번</th>
			<th>사원명</th>
			<th>부서</th>
			<th>업무</th>
			<th>입사일자</th>
		</tr>
		<!-- 사원정보가 없는 경우 -->
		<c:if test="${empty list }">
			<tr>
				<td colspan='5'>사원정보가 없습니다.</td>
			</tr>
		</c:if>

		<!-- 사원정보가 있는 경우 -->
		<c:forEach items="${list }" var="vo">
			<tr>
				<th>${vo.employee_id }</th>
				<th><a class="text_link" href="info?id=${vo.employee_id }">
						${vo.name }</a></th>
				<th>${vo.department_name }</th>
				<th>${vo.job_title }</th>
				<th>${vo.hire_date }</th>
			</tr>
		</c:forEach>


	</table>
</body>
</html>