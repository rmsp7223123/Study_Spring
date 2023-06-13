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
	<form action="">
		<div class="row justify-content-center">
			<div class="col-md-9 col-lg-7 col-xl-5">
				<div class="card shadow-lg border-0 rounded-lg mt-5 px-3 py-5">
					<h3 class="text-center">
						<a href="<c:url value='/'/>"><img
							src="<c:url value='/img/hanul.logo.png'/>"></a>
					</h3>
					<div class="card-body">
						<form>
							<div class="form-floating mb-3">
								<input class="form-control" type="text" name="userid" required
									placeholder="아이디를 입력해주세요"> <label>아이디</label>
							</div>
							<div class="form-floating mb-3">
								<input class="form-control" type="password" name="userpw"
									required placeholder="비밀번호를 입력해주세요"> <label>비밀번호</label>
							</div>
							<button class="btn btn-primary form-control">로그인</button>
							<div
								class="d-flex align-items-center justify-content-between mt-4 mb-0">
								<a class="small" href="join">회원가입</a> <a class="small"
									href="findPassword">비밀번호찾기</a>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</form>
</body>
</html>