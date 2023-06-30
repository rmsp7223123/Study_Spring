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
	<h3 class="my4">방명록 작성</h3>
	<form method="post" enctype="multipart/form-data" action="register">
		<table class="tb-row">
			<colgroup>
				<col width="180px" />
			</colgroup>
			<tr>
				<th>제목</th>
				<td><input type="text" name="title"
					class="check-empty form-control" title="제목" /></td>
			</tr>
			<tr>
				<th>내용</th>
				<td><textarea name="content" class="check-empty form-control"
						title="내용"></textarea></td>
			</tr>
			<tr>
				<th>첨부파일</th>
				<td>
					<div>
						<label><input type="file" class="form-control" multiple
							id="file-multiple" name="file" /> <i role="button"
							class="fs-3 fa-solid fa-file-circle-plus"></i> </label>
					</div> <!--마우스 드래그 드랍으로 파일 첨부 -->
					<div class="form-control mt-2 py-2 file-drag">
						<div class="text-center py-3">첨부할 파일을 마우스로 끌어 오세요</div>
					</div>
				</td>
			</tr>
		</table>
		<input type="hidden" name="writer" value="${loginInfo.userid}" />
	</form>

	<div class="btn-toolbar gap-2 my-3 justify-content-center">
		<button class="btn btn-primary px-4" id="btn-save">저장</button>
		<button class="btn btn-outline-primary px-4" onclick="history.go(-1)">취소</button>
	</div>

	<script>
		var fileList = new FileList();
		$('body *').on('drop', function(e) {
			e.preventDefault();
		})

		// 		$('.file-drag').on('dragover dragleave drop', function(e) {
		// 			e.preventDefault();

		// 			//드래그 오버시 입력태그의 커서가 있을 때처럼 보여지게
		// 			if (e.type == 'dragover') {
		// 				$(this).addClass('drag-over')
		// 			} else {
		// 				$(this).removeClass('drag-over')
		// 			}
		// 		})

		$('.file-drag').on({
			'dragover dragleave drop' : function(e) {
				e.preventDefault();
				if (e.type == 'dragover')
					$(this).addClass('drag-over')
				else
					$(this).removeClass('drag-over')

			},
			'drop' : function(e) {
				console.log(e.originalEvent.dataTransfer.files)
				var files = e.originalEvent.dataTransfer.files;
				for (var i = 0; i < files.length; i++) {
					//폴더는 담지 못하게 처리
					if (files[i].type == "") {
						alert("폴더는 첨부할 수 없습니다.")
					} else {
						fileList.setFile(files[i]);
					}
				}

			}
		})

		$('#btn-save').click(function() {
			if (emptyCheck()) {
				$('form').submit();
			}
		})
	</script>


</body>
</html>