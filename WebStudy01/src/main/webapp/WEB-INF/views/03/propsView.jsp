<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/jquery-3.6.1.min.js"></script>
</head>
<body>
<h4> properties 파일 뷰어 </h4>
<!-- <img src="../../resources/images/cat1.jpg" /> -->
<table>
	<thead>
		<tr>
			<th>key</th>
			<th>value</th>
		</tr>
	</thead>
	<tbody>
		
	</tbody>
</table>
<script type="text/javascript">
	$.ajax({
		dataType : "json",
		success : function(resp) {
			resp['prop1']
		},
		error : function(jqXHR, status, error) {
			console.log(jqXHR);
			console.log(status);
			console.log(error);
		}
	});
</script>
</body>
</html>