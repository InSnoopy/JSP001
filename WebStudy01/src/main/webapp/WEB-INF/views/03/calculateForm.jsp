<%@page import="kr.or.ddit.enumpkg.OperatorType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="<%=request.getContextPath() %>/resources/js/jquery-3.6.1.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/custom.js"></script>
</head>
<body>
<input type="radio" name="dataType" value="json"/>JSON
<input type="radio" name="dataType" value="xml"/>XML
<!-- form에 액션이 없다면? 자기 자신이지만 클라이언트가 보는 주소에 보내는거다 -->
<form method="post" id="calForm">
	<input type="number" name="leftOp" placeholder="좌측피연산자"/>
	<select name="operator">
		<%
			OperatorType[] operators = OperatorType.values();
			for(OperatorType tmp : operators){
				%>
				<option value="<%=tmp.name()%>"><%=tmp.getSign() %></option>		
				<%
			}
		%>
	</select>
	<input type="number" name="rightOp" placeholder="우측피연산자"/>
	<button type="submit">=</button>
</form>
<div id="resultArea">

</div>
<script>
	let resultArea = $("#resultArea");
	$("#calForm").on("submit", function(event){
		event.preventDefault();
		let url = this.action;
		let method = this.method;
		let data = $(this).serializeObject(); // $(this) Jquery 객체화 해야한다.
		data.leftOp = parseInt( data.leftOp );
		data.rightOp = parseInt( data.rightOp );

		$.ajax({
			url : url,
			method : method,
			contentType:"application/json",
			data : JSON.stringify(data),
			dataType : "json",
			success : function(resp) {
				resultArea.html( resp.expression );
			},
			error : function(jqXHR, status, error) {
				console.log(jqXHR);
				console.log(status);
				console.log(error);
			}
		});
		return false;
	});
</script>
</body>
</html>