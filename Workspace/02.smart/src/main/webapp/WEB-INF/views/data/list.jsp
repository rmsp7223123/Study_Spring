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

	<div class="row mb-2">
		<div class="col-auto">
			<select id="pageList" class="form-select">
				<c:forEach var="i" begin="1" end="5">
					<option value="${10*i}">${10*i}개씩</option>
				</c:forEach>
			</select>
		</div>
	</div>
	<div id="data-list"></div>

	<jsp:include page="/WEB-INF/views/include/loading.jsp" />
	<jsp:include page="/WEB-INF/views/include/modal_image.jsp" />


	<script type="text/javascript"
		src="https://oapi.map.naver.com/openapi/v3/maps.js?ncpClientId=3n13cy7hca"></script>
	<script type="text/javascript"
		src="//dapi.kakao.com/v2/maps/sdk.js?appkey=dc9f7c76ae759acfe86dddc50c380e16"></script>
	<script>
	// 한 페이지에 보여질 목록 수 변경
	$('#pageList').change(function(){
		page.pageList = $(this).val();
		pharmacy_list(1);
	})
	
		$(function() {
			// 버튼을 강제 클릭
			$('ul.nav-pills li').eq(1).trigger('click');
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
				data : {pageNo : pageNo, rows : page.pageList},
				async : false,
			}).done(function(res){
				console.log(res)
				res = res.response.body;
				$(res.items.item).each(function(){
					table += 
						`
						<tr>
							<td><a class="map text-link" data-x="\${this.XPos}" data-y= "\${this.YPos}">\${this.yadmNm}</a></td>
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
			$('#data-list').empty();
			$('loading').show();
			
			$.ajax({
				url : "<c:url value='/data/animal/list'/>",
				data : {pageNo : pageNo, rows : page.pageList},
				aysnc : false,
			}).done(function(res){
				console.log(res);
			})
			setTimeout(function(){$('.loading').hide()}, 1000);
		}
		
		
		$(document).on('click', '.pagination a' ,function(){
			pharmacy_list($(this).data('page'));
		})
		.on('click', '.map', function(){ // 약국명 클릭 시 지도에 약국 위치 표시
			if($(this).data('x')=='undefined' || $(this).data('y')=='undefined') {
				alert('위경도 데이터가 존재하지 않습니다.')
			} else {
				showNaverMap($(this));
	// 			showKakaoMap($(this));
			}
		})
		
		function showNaverMap(point) {
			$('#modal-image .modal-body').empty();
			
			// 지도를 담을 영역 만들기
			$('#modal-image').after("<div id='modal-map' style = 'width:668px; height : 700px'></div>");
			
			var xy = new naver.maps.LatLng(point.data('y'),point.data('x'));
			var mapOptions = {
				    center: xy,
				    zoom: 16
				};
			var map = new naver.maps.Map('modal-map', mapOptions);
			var name = point.text();
			// 원하는 위치에 마커 올리기
			
			var marker = new naver.maps.Marker({
    				position: xy,
    				map: map
				});
			
			var infowindow = new naver.maps.InfoWindow({
			    content: `<div class='text-danger fw-bold p-2'>\${name}</div>`
			});
			
			infowindow.open(map, marker);
					
			$('#modal-image .modal-body').html($("#modal-map"));
			new bootstrap.Modal($('#modal-image')).show();
		}
		
		// 카카오 지도로 약국 위치 표시
		function showKakaoMap(point){
			$('#modal-image .modal-body').empty();
			// 지도를 담을 영역 만들기
			$('#modal-image').after("<div id='modal-map' style = 'width:668px; height : 700px'></div>");
			
			var xy = new kakao.maps.LatLng(point.data('y'),point.data('x'));
			var container = document.getElementById('modal-map'); //지도를 담을 영역의 DOM 레퍼런스
			var options = { //지도를 생성할 때 필요한 기본 옵션
				center: xy, //지도의 중심좌표.
				level: 3 //지도의 레벨(확대, 축소 정도)
			};
			
			var map = new kakao.maps.Map(container, options); //지도 생성 및 객체 리턴
			
			// 마커를 생성합니다
			var marker = new kakao.maps.Marker({
			    position: xy
			});

			// 마커가 지도 위에 표시되도록 설정합니다
			marker.setMap(map);
			
			// 인포윈도우를 생성합니다
			var name = point.text();
			var infowindow = new kakao.maps.InfoWindow({
			    position : xy, 
			    content : `<div class='text-danger fw-bold p-2'>\${name}</div>` 
			});
			  
			// 마커 위에 인포윈도우를 표시합니다. 두번째 파라미터인 marker를 넣어주지 않으면 지도 위에 표시됩니다
			infowindow.open(map, marker); 
			
			$('#modal-image .modal-body').html( $('#modal-map'));
			new bootstrap.Modal($('#modal-image')).show();
		}
		
		
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