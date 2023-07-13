/**
 유기동물 관련 처리
 */

// 유기 동물 목록 조회
function animal_list(pageNo) {
	$('#data-list').empty();
	$('.pagination').closest('nav').remove(); // 페이지 목록이 이미 있으면 삭제
	$('.pharmacy-top').addClass('d-none');
	$('loading').show();

	if ($('#sido').length < 1) animal_sido(); // 시도 정보 조회

	page.curPage = pageNo;
	var animal = page; // 요청하는 페이지 번호, 페이지 당 보여질 목록 수
	animal.sido = $('#sido').length == 0 ? '' : $('#sido').val();

	$.ajax({
		url: "animal/list",
		//		data: { pageNo: pageNo, rows: page.pageList, },
		data: JSON.stringify(animal),
		type: 'post',
		contentType: 'application/json',
		aysnc: false,
	}).done(function(res) {
		$('#data-list').html(res);

	})
	setTimeout(function() { $('.loading').hide() }, 1000);
}

//사진 크게 보이게
$(document).on('click', '.popfile img', function() {
	$('#modal-image .modal-body').html($(this).clone())
	$('#modal-image .modal-body img').removeAttr('style');
	new bootstrap.Modal($('#modal-image')).show();
})

function animal_sido() {
	$.ajax({
		url: 'animal/sido'
	}).done(function(res) {
		$('.animal-top').append(res);
		console.log(res);
	})
}