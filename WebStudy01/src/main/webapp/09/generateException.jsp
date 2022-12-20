<%@page import="java.io.IOException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>09/generateException.jsp</title>
</head>
<body>
<h4> 예외 처리 테스트 </h4>
<%
	if(1==1)
		// 이 경우 check인데 빨간줄이 안나는 이유는
		// jsp는 톰캣이 catch로 예외를 처리하기 때문이다.
		throw new IOException("강제발생예외"); // throw뜻 예외를 발생시킨다.
%>
</body>
</html>