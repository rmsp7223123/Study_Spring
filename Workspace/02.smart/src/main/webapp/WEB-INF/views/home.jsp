<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" pageEncoding="utf-8"
	contentType="text/html; charset=utf-8"%>
<html>
<head>
<title>Home</title>
</head>
<body>
	<h3 class="my-4">스마트 웹&amp;앱 과정</h3>
	<img style="width: 100%" src="img/hanul.png"> 상품명 :
	<input type="text" id="name" /> 가격 :
	<input type="text" id="price" value="0" />
	<button onclick="test1()">보내기jsp</button>
	<button onclick="xml()">보내기xml</button>
	<div id="xml-result"></div>
	<table id="ajax-result">
		<tr>
			<th>상품명</th>
			<th>가격</th>
		</tr>
		<tr>
			<th>우유</th>
			<th>1000</th>
		</tr>
		<tr>
			<th>콜라</th>
			<th>1200</th>
		</tr>
		<tr>
			<th>사이다</th>
			<th>1500</th>
		</tr>
	</table>


	<script>
		function xml() {
			$.ajax({
				url : 'xml',
			}).done(function(res) {
				console.log(res)
				console.log($(res).find("product"))
				$(res).find("product").each(function(){
					var name = $(this).find("name").text()
					var price = $(this).find("price").text()
					var tag = `<div><span>\${name}</span><span>\${price}</span></div>`
					$('#xml-result').append(tag)
				})
			})

		}

		function test1() {
			$.ajax({
				url : 'test1',
				//			type : 'post',
				data : {
					name : $('#name').val(),
					price : $('#price').val()
				},
			}).done(function(res) {
				console.log(res)
				$('#ajax-result').append(res)
			})
		}
	</script>

</body>
</html>
