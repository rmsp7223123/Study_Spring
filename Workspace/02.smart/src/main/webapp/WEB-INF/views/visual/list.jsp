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
	<h3>사원 정보 분석 시각화</h3>
	<ul class="nav nav-tabs">
		<li class="nav-item"><a class="nav-link">부서인원수</a></li>
		<li class="nav-item"><a class="nav-link">채용인원수</a></li>
		<li class="nav-item"><a class="nav-link">Link</a></li>
		<li class="nav-item"><a class="nav-link">Link</a></li>
	</ul>
	<div id="tab-content" class="m-md-2 m-lg-3" style="height: 520px">
		<canvas id="chart" class="h-100 m-auto"></canvas>
	</div>


	<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

	<script>
		$('ul.nav-tabs li').on({
			'click' : function() {
				$('ul.nav-tabs li a').removeClass('active');
				$(this).children('a').addClass('active');

				var idx = $(this).index();
				if (idx == 0) {
					departments(); // 부서원 수 조회
				} else if (idx == 1) {
					hirement(); // 채용 인원 수 조회
				}
			},
			'mouseover' : function() {
				$(this).addClass('shadow');
			},
			'mouseleave' : function() {
				$(this).removeClass('shadow');
			},
		})

		$(function() {
			$('ul.nav-tabs li').eq(0).trigger('click');
		})

		//부서원 수 조회
		function departments() {
			sampleChart();
		}

		//채용 인원 수 조회
		function hirement() {

		}

		function sampleChart() {
			new Chart($('#chart'), {
				type : 'bar',
				data : {
					labels : [ 'Red', 'Blue', 'Yellow', 'Green', 'Purple',
							'Orange' ],
					datasets : [ {
						label : '# of Votes',
						data : [ 12, 19, 3, 5, 2, 3 ],
						borderWidth : 1
					} ]
				},
				options : {
					scales : {
						y : {
							beginAtZero : true
						}
					}
				}
			});

		}
	</script>
</body>


</html>