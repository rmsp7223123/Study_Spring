<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<select name="" id="sido" class="form-select">
	<option value="">시도 선택</option>
	<c:forEach items="${list.items.item}" var="vo">
		<option value="${vo.orgCd}">${vo.orgdownNm}</option>
	</c:forEach>
</select>

<script>
	$('#sido').change(function() {
		animal_sigungu();
		animal_list(1);
	})
	function animal_sigungu() {
		$('#sigungu').remove();
		if ($('#sido').val() == '') {
			return;
		}
		$.ajax({
			url : 'animal/sigungu',
			data : {
				sido : $('#sido').val()
			}
		}).done(function(res) {
			$('#sido').after(res);
		})
	}
</script>