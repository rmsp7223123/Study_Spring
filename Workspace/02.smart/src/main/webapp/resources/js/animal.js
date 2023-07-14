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
	animal.sigungu = $('#sigungu').length == 0 ? '' : $('#sigungu').val();
	animal.shelter = $('#shelter').length == 0 ? '' : $('#shelter').val();
	animal.upkind = $('#upkind').length == 0 ? '' : $('#upkind').val();
	animal.kind = $('#kind').length == 0 ? '' : $('#kind').val();

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
}).on('change', '#upkind', function() {  // 축 종 변경시 해당 품종 조회
	animal_kind();
	animal_list(1);
	//	$.ajax({
	//		url: 'animal/upkind'
	//	})
})

function animal_kind() {
	$('#kind').remove();
	$.ajax({
		url: 'animal/kind',
		data: { upkind: $('#upkind').val() },
	}).done(function(res) {
		$('#upkind').after(res)
	})
}

//시도 조회
function animal_sido() {
	$.ajax({
		url: 'animal/sido'
	}).done(function(res) {
		$('.animal-top').append(res);
		animal_type();
	})
}

// 축 종 태그 만들기
function animal_type() {
	var type = `
		<select class="form-select" id="upkind">
			<option value=''>축종 선택</option>	
			<option value='417000'>개</option>	
			<option value='422400'>고양이</option>	
			<option value='429900'>기타</option>	
		</select>
	`;
	//	$('.animal-top').prepend(type);
	$('.animal-top').append(type);
}

