<%@page import="java.util.Objects"%>
<%@page import="java.util.Scanner"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//_JSPService(request, response)
	String numParam = request.getParameter("number");
	if(numParam!=null && !numParam.matches("[\\d{1,2}]")){
		response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		return;
	}
%>
<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h4>팩토리얼 연산 처리</h4>
<h4></h4>
number = 10;
<!-- action이 없다면 현재 브라우저에게 보내진다. -->
<form>
	<input type="number" name="number" value="<%=Objects.toString(numParam, "") %>" onchange="this.form.submit()"/>
</form>
<%
if(numParam!=null){

	int input = Integer.parseInt(numParam);
	String pattern = "%d! = %d";
	int result = fact(input);
	String expr = String.format(pattern, input, result);
%>
<%=expr %>	
<%
}
%>
<!-- number : <input id="number" type="text" name="id" value="3"><br>  -->

<%!
	private int fact(int n){
		if(n<0)
			throw new IllegalArgumentException("음수는 연산 불가");
		else if(n<=1)
			return n;			
		else
			return fact(n-1) * n;
	}
%>

</body>
</html>