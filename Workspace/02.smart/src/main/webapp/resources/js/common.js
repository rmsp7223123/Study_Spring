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
})
//파일이 이미지파일인지 확인
function isImage(filename) {
	var ext = filename.substr(filename.lastIndexOf('.') + 1).toLowerCase();
	var imgs = ["png", "jpg", "gif", "bmp", "jpeg", "webp", "jfif"];
	return imgs.indexOf(ext) == -1 ? false : true;
}

$(function() {
	$('input#file-single').change(function() {
		var _preview = $('#file-attach .file-preview');
		var _delete = $('#file-attach .file-delete');

		var attached = this.files[0];
		if (attached) {
			//이미지 파일인지 확인
			if (isImage(attached.name)) {
				_delete.removeClass('d-none');
				//미리보기 태그가 있을때에만
				if (_preview.length > 0) {
					_preview.html("<img>");

					var reader = new FileReader();
					reader.readAsDataURL(attached);
					reader.onload = function(e) {
						//						_preview.children("img").attr("src", this.result);
						_preview.children("img").attr("src", e.target.result);
					}
				}
			} else {
				//이전에 선택했던 이미지 파일 처리
				_preview.empty();
				// 실제 file태그의 정보 초기화
				$(this).val('');
				_delete.addClass('d-none');
			}

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


