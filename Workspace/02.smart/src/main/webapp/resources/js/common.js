/**
 * 공통 함수 선언 
 */

function modalAlert(type, title, message) {
	$('#modal-alert .modal-title').html(title);
	$('#modal-alert .modal-body').html(message);

	$('.modal-icon').removeClass('text-info text-warning text-danger text-primary text-success fa-circle-question fa-circle-exclamation')
	$('.modal-footer .btn-ok').removeClass('btn-info btn-warning btn-danger btn-primary btn-success');

	if (type == 'danger') { // confirm
		$('.modal-footer .btn-ok').addClass('btn-secondary').text('아니오')
		$('.modal-footer .btn-danger').removeClass('d-none')
		$('.modal-icon').addClass('fa-circle-question')
	} else {
		$('.modal-footer .btn-ok').addClass('btn-' + type).text('확인')
		$('.modal-footer .btn-danger').addClass('d-none')
		$('.modal-icon').addClass('fa-circle-exclamation')
	}
	$('.modal-icon').addClass('text-' + type);
}

//동적으로 만들어진 요소에 대해서는 document에 이벤트를 등록해야 한다
$(document).on('click', '.date + .date-delete', function() {
	$(this).css('display', 'none'); // 삭제버튼 안보이게
	$(this).prev('.date').val('');

}).on('click', '#file-attach .file-delete', function() {
	$(this).addClass('d-none'); // 삭제버튼 안보이게
	$('input[type=file]').val(''); // 첨부되어 있던 이미지파일 없애기
	var _preview = $('#file-attach .file-preview');
	if (_preview.length > 0) {
		_preview.empty();
	}
	var _name = $('#file-attach .file-name') // 파일명 태그
	if (_name.length > 0) {
		_name.empty(); // 파일명 삭제
	}
})


//파일이 이미지파일인지 확인
function isImage(filename) {
	var ext = filename.substr(filename.lastIndexOf('.') + 1).toLowerCase();
	var imgs = ["png", "jpg", "gif", "bmp", "jpeg", "webp", "jfif"];
	return imgs.indexOf(ext) == -1 ? false : true;
}

//첨부파일 크기 제한 함수
function rejectedFile(fileInfo, tag) {
	// 1024byte = 1kb , 1024*1024byte = 1MB, ...
	if (fileInfo.size > 1024 * 1024 * 10) {
		alert("10MB 이하의 파일만 첨부가 가능합니다.")
		tag.val('');
		return true;
	} else {
		return false;
	}
}

$(function() {
	$('input#file-single').change(function() {
		var _preview = $('#file-attach .file-preview');
		var _delete = $('#file-attach .file-delete');
		var _name = $('#file-attach .file-name');

		var attached = this.files[0];
		if (attached) {
			//파일사이즈 제한
			if (rejectedFile(attached, $(this))) return;
			if (_name.length > 0) _name.text(attached.name);
			_delete.removeClass('d-none');
			//이미지 파일인지 확인
			if (isImage(attached.name)) {
				singleFile = attached; // 선택한 파일정보를 관리

				//미리보기 태그가 있을때에만
				if (_preview.length > 0) {
					_preview.html("<img>");

				} else {
					// 첨부파일이 이미지인데, 미리보기가 요소가 없으면 동적으로 만들어 보이게 처리
					// 삭제버튼 앞에 넣기
					_delete.before("<span class='file-preview'><img></span>");
					_preview = $('#file-attach .file-preview');
				}
				var reader = new FileReader();
				reader.readAsDataURL(attached);
				reader.onload = function(e) {
					// _preview.children("img").attr("src", this.result);
					_preview.children("img").attr("src", e.target.result);
				}
			} else {
				//이전에 선택했던 이미지 파일 처리
				_preview.empty();
				if ($(this).hasClass("image-only")) {
					singleFile = ''; // 이미지파일이 아닌 경우 관리정보를 초기화
					$(this).val(''); // 실제 file태그의 정보 초기화
					_delete.addClass('d-none');
				}
			}

		} else {
			// 파일선택 창에서 취소를 클릭한 경우 : 어떠한 처리도 하지 않음
			// 파일정보는 관리된 singleFile 변수에 있음
		}
	})



	$('.date').change(function() {
		$(this).next('.date-delete').css('display', 'inline')
	})

	$('[name=phone]').keyup(function() {
		toPhone($(this));
	})
	var today = new Date();
	var range = today.getFullYear() - 100 + ':' + today.getFullYear();
	$.datepicker.setDefaults({
		dateFormat: "yy-mm-dd",
		changeYear: true,
		changeMonth: true,
		yearRange: range,
		showMonthAfterYear: true,
		monthNamesShort: ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"],
		dayNamesMin: ["일", "월", "화", "수", "목", "금", "토"],
		maxDate: today
	})

	$(".date").datepicker();
	$(".date").attr('readonly', true);

})

function toPhone(tag) {
	// 			02-1234-5678, 010-1234-5678
	var phone = tag.val().replace(/[^0-9]/g, '').replace(/[-]/g, ''); // 숫자만 입력되게 처리
	if (phone.length > 1) { // 2자리 이상 입력하면
		var start = phone.substr(0, 2) == "02" ? 2 : 3, middle = start + 4;

		// - 넣기
		if (phone.length > middle) {
			phone = phone.substr(0, start) + "-"
				+ phone.substr(start, 4) + "-"
				+ phone.substr(middle, 4);
		} else if (phone.length > start) {
			phone = phone.substr(0, start) + "-"
				+ phone.substr(start, 4) + "-";
		}
	}


	tag.val(phone);
}

//입력항목 입력여부 확인
function emptyCheck() {
	var ok = true;
	$('.check-empty').each(function() {
		if ($(this).val() == "") {
			alert($(this).attr('title') + '입력하세요.');
			$(this).focus();
			ok = false;
			return ok;
		}
	})
	return ok;
}

