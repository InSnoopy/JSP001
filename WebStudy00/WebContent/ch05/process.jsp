<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Implicit Objects(내장 객체)</title>
</head>
<body>
	
	<% // 스크립틀릿
	   // process.jsp?name = 개똥이
	   //             name = value <= 요청 파라미터 (request객체 안에 들어 있다.)
	   request.setCharacterEncoding("UTF-8");
	   String name = request.getParameter("name");
	   
	   
	%>
	<!-- 표현문 -->
	<p>이름 : <%=name %></p>
	
	<h2>웹 브라우저 / 서버 정보 출력하기(request 내장 객체 사용)</h2>
	<p>요청 정보 길이 : <%=request.getContentLength() %></p>
	<p>클라이언트 전송 방식 : <%=request.getMethod() %></p>
	<p>요청 URI : <%=request.getRequestURI() %></p>
	<p>서버 이름 : <%=request.getServerName() %></p>
	<p>서버 포트 : <%=request.getServerPort() %></p>
	<p>클라이언트 IP : <%=request.getRemoteAddr() %>
	<p>요청 정보 인코딩 : <%=request.getCharacterEncoding() %>
	<p>요청 정보 콘텐츠 유형 : <%=request.getContentType() %>
	<p>요청 정보 프로토콜 : <%=request.getProtocol() %>
	<p>쿼리문 : <%=request.getQueryString() %>

</body>
</html>