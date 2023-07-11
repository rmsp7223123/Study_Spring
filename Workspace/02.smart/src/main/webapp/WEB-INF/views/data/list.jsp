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

	<jsp:include page="/WEB-INF/views/include/loading.jsp" />


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
					pharmacy_list(1);
				} else if (idx == 1) {
					animal_list(1);
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
		function pharmacy_list(pageNo) {
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
			
			$('.loading').show();
			
			table = '';
			$.ajax({
				url : "<c:url value='/data/pharmacy'/>",
				data : {pageNo : pageNo},
				async : false,
			}).done(function(res){
				console.log(res)
				res = res.response.body;
				$(res.items.item).each(function(){
					table += 
						`
						<tr>
							<td>\${this.yadmNm}</td>
							<td>\${this.telno ? this.telno : '-'}</td>
							<td class='text-start'>\${this.addr}</td>
						</tr>
						`;
				})
				$('#data-list .pharmacy tbody').html(table);
				
				//페이지 목록 표현
				makePage(res.totalCount, pageNo);
			})
			setTimeout(function(){
				$('.loading').hide();
			}, 1000);
		}

		// 유기 동물 목록 조회
		function animal_list(pageNo) {

		}
		
		
		$(document).on('click', '.pagination a' ,function(){
			pharmacy_list($(this).data('page'));
		})
		
		
		var page = {pageList : 10, blockPage : 10}; // 페이지 당 보여질 목록 수, 블럭 당 보여질 페이지 수
		// 페이지 정보 만들기
		function makePage(totalList, curPage) {
			$('.pagination').closest('nav').remove(); // 페이지 목록이 이미 있으면 삭제
			page.totalList = totalList; // 총 목록 수
			page.curPage = curPage; // 현재 페이지 번호
			page.totalPage = Math.ceil(page.totalList / page.pageList); // 총 페이지 수
			page.totalBlock = Math.ceil(page.totalPage / page.blockPage); // 총 블럭 수
			page.curBlock = Math.ceil(page.curPage / page.blockPage); // 현재 블럭 번호
			page.endPage = page.curBlock * page.blockPage ; // 끝 페이지 번호
			page.beginPage = page.endPage - (page.blockPage-1); // 시작 페이지 번호
			if(page.totalPage < page.endPage) {
				page.endPage = page.totalPage
			}
			var prev = page.curBlock == 1 ? 'invisible' : '';
			var next = page.curBlock == page.totalBlock ? 'invisible' : '';
			var nav = 
				`
			<nav>
			<ul class="pagination mt-3 justify-content-center">
			<li class="page-item"><a class="page-link \${prev}" data-page="1"><i class="fa-solid fa-angles-left"></i></a></li>
			<li class="page-item"><a class="page-link \${prev}" data-page="\${page.beginPage-page.blockPage}"><i class="fa-solid fa-angle-left"></i></a></li>`;		
			for(var no=page.beginPage; no<=page.endPage; no++) {
				nav +=
			`<li class="page-item">
				\${no == page.curPage ?
				`<a class="page-link active">\${no}</a>`
			 : `<a class="page-link" data-page="\${no}">\${no}</a>`}</li>`;
			}
			nav+=
			`<li class="page-item"><a class="page-link \${next}" data-page="\${page.endPage+1}"><i class="fa-solid fa-angle-right"></i></a></li>
			<li class="page-item"><a class="page-link \${next}" data-page="\${page.totalPage}"><i class="fa-solid fa-angles-right"></i></a></li>
			</ul>
			</nav>`;
			$('#data-list').after(nav);
		}
	</script>
</body>
</html>