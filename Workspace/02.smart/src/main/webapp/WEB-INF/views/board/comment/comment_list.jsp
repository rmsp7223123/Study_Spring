<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
	<div class="col-md-10 content border-bottom py-3 border-top"
		data-id="${vo.id}">
		<div class="d-flex align-items-center mb-1 justify-content-between">
			<div class="d-flex align-items-center mb-1">
				<span class="text-secondary me-2">${profile}</span> <span>${vo.name}
					[${vo.writedate}]</span>
			</div>
			<c:if test="${loginInfo.userid eq vo.writer}">
				<div>
					<span class="title me-4 d-none">댓글수정[ <span class="writing">0</span>
						/ 200 ]
					</span> <a class="btn btn-outline-info btn-sm btn-modify-save me-2">수정</a><a
						class="btn btn-outline-danger btn-sm btn-delete-cancel">삭제</a>
				</div>
			</c:if>
		</div>


		<div class="comment">${fn : replace(fn:replace(vo.content, lf, '<br>'), crlf, '<br>')}</div>
		<div class="d-none hidden"></div>
	</div>
</c:forEach>

<script>
	// 수정&저장 버튼 클릭시
	$('.btn-modify-save').click(function() {
		var _content = $(this).closest('.content');
		if ($(this).text() == '수정') {
			modifyStatus(_content);
		} else {
			// 변경해서 입력한 댓글을 DB에 저장하도록 처리
			// JSON 데이터를 서버로 보낼 때 : post, 보내는 내용에 대해 json으로 지정
			$.ajax({
				type : 'post',
				contentType : 'application/json',
				url : '<c:url value="/board/comment/update"/>',
				data : JSON.stringify({
					id : _content.data('id'),
					content : _content.find('textarea').val()
				})
			}).done(function(res) {
				console.log(res)
				alert(res);
			})
		}
	})

	// 변경모드 상태
	function modifyStatus(_content) {
		// 버튼은 저장/취소
		_content.find('.btn-modify-save').text('저장').removeClass(
				"btn-outline-info").addClass("btn-primary");
		_content.find('.btn-delete-cancel').text('취소').removeClass(
				"btn-outline-danger").addClass("btn-secondary");

		// .comment안에 있던 댓글 내용은 textarea에 보여지게
		var _comment = _content.find('.comment') // 댓글 내용이 있는 태그
		var comment = _comment.html().replace(/<br>/g, '\n'); // 댓글 내용
		_comment
				.html(`<textarea class="form-control w-100">\${comment}</textarea>`);
		_content.find('.writing').text(comment.length); // 실제 입력 글자 수 표현
		_content.find('.title').removeClass('d-none'); // 입력 글자 수 부분 보이게
		_content.find('.hidden').html(`\${comment}`); // 원래 댓글 내용 그대로 담기
	}

	// 취소버튼 클릭시
	$('.btn-delete-cancel').click(function() {
		var _content = $(this).closest('.content');
		stayStatus(_content);
	})

	//가만히 있는 상태
	function stayStatus(_content) {
		_content.find('.btn-modify-save').text('수정').addClass(
				"btn-outline-info").removeClass("btn-primary");
		_content.find('.btn-delete-cancel').text('삭제').addClass(
				"btn-outline-danger").removeClass("btn-secondary");

		// .comment안에 있던 textarea의 내용은 원래대로
		var _comment = _content.find('.comment') // 댓글 내용이 있는 태그
		var textarea = _content.find('.hidden').html().replace(/\n/g, '<br>');
		_comment.html(textarea);
		_content.find('.title').addClass('d-none'); // 입력 글자 수 부분 보이게
		_content.find('.hidden').empty();
	}

	$('.btn-modify-save').click(function() {

	})
</script>