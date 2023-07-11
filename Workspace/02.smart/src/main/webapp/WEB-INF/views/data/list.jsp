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
	<h3 class="my-4">공공데이터</h3>
	<ul class="nav nav-pills justify-content-center gap-4 mb-3">
		<li class="nav-item"><a class="nav-link">약국 조회</a></li>
		<li class="nav-item"><a class="nav-link">유기 동물 조회</a></li>
	</ul>
	<div id="data-list"></div>


	<script>
		$(function() {
			// 버튼을 강제 클릭
			$('ul.nav-pills li').eq(0).trigger('click');
		})

		// 		$('ul.nav-pills li').click(function() {
		// 			$('ul.nav-pills li a').removeClass('active');
		// 			$(this).children('a').addClass('active');

		// 			var idx = $(this).index();
		// 			if (idx == 0) {
		// 				pharmacy_list();
		// 			} else if (idx == 1) {
		// 				animal_list();
		// 			}
		// 		})

		$('ul.nav-pills li').on({
			'click' : function() {
				$('ul.nav-pills li a').removeClass('active');
				$(this).children('a').addClass('active');
				var idx = $(this).index();
				if (idx == 0) {
					pharmacy_list();
				} else if (idx == 1) {
					animal_list();
				}
			},
			'mouseover' : function() {
				$(this).addClass('shadow')
			},
			"mouseleave" : function() {
				$(this).removeClass('shadow')
			}
		})

		// 약국 목록 조회
		function pharmacy_list() {
			var table = 
			`
			<table class="tb-list pharmacy">
				<colgroup>
					<col width="150px" />
					<col width="150px" />
					<col width="300px" />
				</colgroup>
				<thead>
				<tr>
					<th>약국명</th>
					<th>전화번호</th>
					<th>주소</th>
				</tr>
				</thead>
				<tbody></tbody>
			</table>
			`;
			$('#data-list').html(table);
			table = '';
			$.ajax({
				url : "<c:url value='/data/pharmacy'/>"
			}).done(function(res){
				console.log(res)
				res = res.response.body;
				$(res.items.item).each(function(){
					table += 
						`
						<tr>
							<td>\${this.yadmNm}</td>
							<td>\${this.telno}</td>
							<td class='text-start'>\${this.addr}</td>
						</tr>
						`;
				})
				$('#data-list .pharmacy tbody').html(table);
			})
		}

		// 유기 동물 목록 조회
		function animal_list() {

		}
	</script>
</body>
</html>