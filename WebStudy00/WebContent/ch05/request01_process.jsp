<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%	// 스크립틀릿 : 지역변수
	request.setCharacterEncoding("UTF-8");
	String userId = request.getParameter("id");
	String password = request.getParameter("passwd");
	// 호스트명
	String hostValue = request.getHeader("host");
	// 설정된 언어
	String alValue = request.getHeader("accept-language");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Implcit Objects</title>
</head>
<body>
	<p>아이디 : <%=userId %></p>
	<p>비밀번호 : <%=password %></p>
	<p>호스트명 : <%=hostValue %></p>
	<p>설정된 언어 : <%=alValue %></p>


	<p><%=request.getSession() %></p>
	<p><%=request.getContentType() %></p>
	<p><%=request.getContextPath() %></p>
	<P><%=PageContext.SESSION %>
	<P><%=PageContext.OUT %>
	<p><%=getServletInfo() %></p>
	<p><%=getServletName() %></p>
	<p><%=getServletConfig() %></p>
	<p><%=getServletContext().getContextPath() %></p>
	<p><%=getServletContext().getServerInfo() %></p>
	<p><%=getServletContext().getRealPath(getServletContext().getContextPath()) %></p>
	<p><%=getServletContext().getServletContextName() %></p>
	
	
</body>
</html>