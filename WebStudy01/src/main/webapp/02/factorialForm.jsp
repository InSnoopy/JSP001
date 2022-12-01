<%@page import="java.util.Objects"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/jquery-3.6.1.min.js"></script>
<script type="text/javascript">
	<!-- body가 끝나면 ready라는 이벤트가 발생 -->
	$(document).ready(function(){		
		let resultArea = $("#resultArea");
		$("form[name]").on("submit", function(event){
			event.preventDefault();
			console.log($(this));
			console.log(this);
			console.log($(this)[0][0].value);
			console.log($(this).get(0));
			let url = this.action;
			let method = this.method;
			let data = $(this).serialize();
			console.log(data);
			$.ajax({
				url : url,
				method : method,
				data : data,
				dataType : "html",
				success : function(resp) {

					resultArea.html(resp);
				},
				error : function(jqXHR, status, error) {
					console.log(jqXHR);
					console.log(status);
					console.log(error);
				}
			});
			return false;
		});
	})
</script>
</head>
<body>

<form name="facForm" action="<%=request.getContextPath() %>/02/factorial.do">
	<input type="number" name="number" value="" />
	<!-- 클릭 이후에 제출 이벤트 발생 -> form -->
	<input type="submit" value="전송" />
	<!-- 클릭 이후에 reset 이벤트 발생 -> 톰캣 -->
	<input type="reset" value="취소" />
	<!-- 클릭 -->
	<input type="button" value="그냥버튼" />
</form>
<div id="resultArea">
	
</div>
</body>
</html>