<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!-- prefix : 접두어 -->
<%
	request.setCharacterEncoding("UTF-8");
	// 자바 세계의 변수
	String id = request.getParameter("id");
	String password = request.getParameter("passwd");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<!-- java 세계에 있는 변수를 html세계로 가져온다. -->
	<c:set var="userId" value="<%=id %>"/>
	<c:set var="userPassword" value="<%=password %>"/>
	아이디 : ${userId},
	비밀번호 : ${userPassword} <br/>
	
<script type="text/javascript">
	// html 변수를 Javascript 변수에 넣는다.
	let varId = "${userId}";
	let varPw = "${userPassword};"
	
	console.log("varId : ",varId);
	console.log("varPw : ",varPw);
</script>

	<%
	if(id.equals("a001") && password.equals("java")){
		// response.sendRedirect("response01_succes.jsp");
	}else{
		// response.sendRedirect("response01_failed.jsp");
	}
	%>

</body>
</html>