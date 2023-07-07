<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h3 class="my-4">방명록 안내</h3>
	<table class="tb-row" style="table-layout: fixed">
		<colgroup>
			<col width="180px" />
			<col />
			<col width="160px" />
			<col width="160px" />
			<col width="100px" />
			<col width="100px" />
		</colgroup>
		<tr>
			<th>제목</th>
			<td colspan="5">${vo.title}</td>
		</tr>
		<tr>
			<th>작성자</th>
			<td>${vo.name}</td>
			<th>작성일자</th>
			<td>${vo.writedate}</td>
			<th>조회수</th>
			<td>${vo.readcnt}</td>
		</tr>
		<tr>
			<th>내용</th>
			<td colspan="5">${fn: replace(vo.content, crlf, "<br>")}</td>
		</tr>
		<tr>
			<th>첨부파일</th>
			<td colspan="5"><c:forEach items="${vo.fileList}" var="file">
					<div class="row">
						<div class="my-1 d-flex align-items-center">
							<span class="file-name col text-truncate">${file.filename}</span>
							<i role="button" data-file="${file.id}"
								class="file-download fs-3 fa-solid fa-download ms-2 col"></i>
						</div>
					</div>
				</c:forEach></td>
		</tr>
	</table>

	<div class="btn-toolbar gap-2 my-3 justify-content-center">
		<a class="btn btn-primary" id="btn-list">방명록목록</a> <a
			class="btn btn-primary" id="btn-modify">방명록수정</a> <a
			class="btn btn-primary" id="btn-delete">방명록삭제</a>
	</div>
	<!--댓글입력부분 -->
	<div>
		<div class="row justify-content-center" id="comment-register">
			<div class="col-md-10 content">
				<div
					class="title d-flex align-items-center justify-content-between mb-2 invisible">
					<span>댓글작성 [ <span class="writing">0</span> / 200 ]
					</span><a class="btn btn-outline-primary btn-sm btn-register invisible">댓글등록</a>
				</div>

				<!--로그인 여부에 따라 입력하도록 안내 -->
				<div class="comment">
					<div
						class="form-control text-center py-3 d-flex justify-content-center align-items-center"
						style="height: 90px">${empty loginInfo ? "댓글을 입력하려면 여기를 클릭 후 로그인 하세요" : "댓글을 입력하세요"}</div>
				</div>

			</div>
		</div>
	</div>

	<form method="post">
		<input type="hidden" name="file" /> <input type="hidden"
			name="curPage" value="${page.curPage}" /> <input type="hidden"
			name="search" value="${page.search}" /> <input type="hidden"
			name="keyword" value="${page.keyword}" /> <input type="hidden"
			name="viewType" value="${page.viewType}" /> <input type="hidden"
			name="pageList" value="${page.pageList}" /> <input type="hidden"
			name="id" value="${vo.id}" />
	</form>

	<jsp:include page="/WEB-INF/views/include/modal_image.jsp" />
	<jsp:include page="/WEB-INF/views/include/modal_alert.jsp" />


	<script>
		// 댓글 등록 처리
		$('btn-register').click(function(){
			// 입력한 글이 있을때만 처리
			var _textarea = $('#comment-register textarea');
			if(_textarea.val().length == 0) {
				return;
			}
			$.ajax({
				url : '<c:url value="/board/comment/register"/>',
				data : {board_id:${vo.id}, content : _textarea.val(), writer : '${loginInfo.userid}'},
			}).done(function(res){
				console.log(res)
			});
		})
	
		// 댓글 등록 부분 초기화
		function initRegisterContent() {
			$('#comment-register .writing').text(0);
			// $('.btn-register').addClass('invisible'); 버튼만 안보이게
			
			// 화면초기화 상태로 
			$("#comment-register .title").addClass('invisible');
			$('#comment-register .comment textarea').remove();
			$('#comment-register .comment').append(`<div
					class="form-control text-center py-3 d-flex justify-content-center align-items-center"
					style="height: 90px">${empty loginInfo ? "댓글을 입력하려면 여기를 클릭 후 로그인 하세요" : "댓글을 입력하세요"}</div>`);
			
			
		}
	
		// 댓글 입력 textarea에서 커서를 다른곳으로 이동하면
		$(document).on('focusout', '#comment-register textarea', function(){
			// 입력이 되어 있지 않는 경우 초기화 하기
			$(this).val( $(this).val().trim(""));
			if($(this).val() == "") {
				initRegisterContent()
			}
		}).on('keyup', '#comment-register textarea', function(){
			var comment = $(this).val();
			if(comment.length > 200) {
				alert("최대 200글자 까지만 입력 가능합니다.");
				comment = comment.substr(0,200);
			}
			$(this).val(comment);
			$(this).closest('.content').find('.writing').text( comment.length );
			
			// 글자를 입력 했을 때 등록버튼이 보이게
			if(comment.length > 0) {
				$('.btn-register').removeClass('invisible');	
			} else {
				$('.btn-register').addClass('invisible');
			}
			
		})
	
		//폰트어썸으로 만들어진 버튼의 경우 동적으로 다시 만들어지기 때문에 
		//이벤트는 태그자체에 직접 등록 했을 때 발생되지 않음 => 문서에 등록
		$(document).on('click', '.file-download', function() {
			$('[name=file]').val($(this).data('file'));
			$('form').attr('action', 'download').submit();
		})

		//첨부된 파일이 이미지인 경우 미리보기 되게
		<c:forEach items="${vo.fileList}" var = "f" varStatus="s">
		if (isImage("${f.filename}")) {
			$('.file-name').eq(${s.index}).after("<span class='file-preview col'><img src='${f.filepath}'/></span>");
		}
		</c:forEach>
		
		$('#btn-list, #btn-modify, #btn-delete').click(function(){
			var id = $(this).attr('id');
			id = id.substr(id.indexOf('-')+1);
			if(id == 'delete') {
				modalAlert('danger', "방명록 삭제", '이 글을 삭제 하시겠습니까?');
				new bootstrap.Modal('#modal-alert').show();
				
			} else {
				$('form').attr('action',id).submit();				
			}
		})
		
		$('#modal-alert .btn-danger').click(function(){
			$('form').attr('action','delete').submit();
		})
		
		$('#comment-register .comment').click(function(){
			if(${empty loginInfo}){ // 로그인 정보가 없을 때 로그인을 할 것 인지 확인창
				if(confirm('로그인 하시겠습니까?')) {
					$('form').attr('action', "<c:url value='/member/login'/>").submit();
				}
			} else {
				// form-control이 지정된 태그가 div이면 입력이 안되니 입력 태그를 만들어서 교체
				if( $(this).children(".form-control").is("div") ){
					$(this).children('div.form-control').remove();
					$(this).append(`<textarea class="form-control w-100"></textarea>`)
					$(this).children("textarea").focus();
					$('.content .title').removeClass("invisible");
				}
				
			}
		})
	</script>

</body>
</html>