<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h4>공유된 속성 데이터들</h4>
<pre>
	page scope : <%=pageContext.getAttribute("pageAttr") %>, ${pageAttr }
	request scope : <%=request.getAttribute("requestAttr") %>, ${requestAttr }
	session scope : <%=session.getAttribute("sessionAttr") %>, ${sessionAttr }
	
	<%
		// 만약에 session을 한번 메시지를 요청하고 지우길 원한다면?
		// 이런 방식을 flash attribute방식이라고 한다.
		// 대부분의 메세지는 이 방식을 사용한다.
		session.removeAttribute("sessionAttr");
	%>
	application scope : <%=application.getAttribute("applicationAttr") %>, ${applicationAttr }
</pre>
</body>
</html>