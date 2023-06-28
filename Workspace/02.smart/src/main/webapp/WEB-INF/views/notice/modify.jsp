<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fnc"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h3 class="my4">공지글 수정</h3>
	<form method="post" enctype="multipart/form-data" action="update">
		<table class="tb-row">
			<colgroup>
				<col width="180px" />
			</colgroup>
			<tr>
				<th>제목</th>
				<!-- escapeXml : xml 마크업 문자로 인식될 수 있는 문자는 제외시키기 -->
				<td><input type="text" name="title"
					value="${fnc :escapeXml(vo.title)}"
					class="check-empty form-control" title="제목" /></td>
			</tr>
			<tr>
				<th>내용</th>
				<td><textarea name="content" class="check-empty form-control"
						title="내용">${vo.content}</textarea></td>
			</tr>
			<tr>
				<th>첨부파일</th>
				<td>
					<div class="row">
						<div class="col-auto d-flex align-items-center gap-4">
							<label><input type="file" class="form-control"
								id="file-single" name="file" /> <i role="button"
								class="fs-3 fa-solid fa-file-circle-plus"></i> </label>
							<div class="d-flex gap-3 align-items-center" id="file-attach">
								<span class="file-name">${vo.filename}</span> <i
									class="${empty vo.filename ? 'd-none' : ''} fs-3 fa-solid fa-file-circle-xmark file-delete text-danger"></i>
							</div>
						</div>
					</div>
				</td>
			</tr>
		</table>
		<input type="hidden" name="id" value="${vo.id}" /> <input
			type="hidden" name="filename" />
	</form>

	<div class="btn-toolbar gap-2 my-3 justify-content-center">
		<button class="btn btn-primary px-4" id="btn-save">저장</button>
		<button class="btn btn-outline-primary px-4" onclick="history.go(-1)">취소</button>
	</div>

	<script>
		$(function() {
			singleFile = '';
			if (isImage("${vo.filename}")) {
				$('.file-name')
						.after(
								"<span class='file-preview'><img src='${vo.filepath}'></span>")
			}
		})
		$('#btn-save').click(function() {
			$('[name=filename]').val($('.file-name').text())
			if (emptyCheck()) {
				singleFileUpload();
				$('form').submit();
			}
		})
	</script>


</body>
</html>