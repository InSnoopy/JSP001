<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%! 
	// java code : 전역 코드
	String variable;
	private void test(){}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
/* comment */
</style>
</head>
<body>

<h4>JSP spec</h4>
<pre>
	: 서블릿 스펙에서 파생된 하위 스펙, 템플릿 기반의 스크립트 형태를 가진 스펙.
	
	JSP 소스 표준 구성 요소
	1. frontend code [정적 텍스트] : 일반 텍스트, HTML, JavaScript, CSS
	2. backend script code
		1) scriptlet :
			<%
				// java code : 지역 코드 
				String data = "데이터";
				Date now = new Date();
			%>
		2) directive : <%--<%@ page import="java.util.Date" %> --%>
			: JSP 페이제에 대한 부가설정이나 전처리 구문에 사용되며, 
			  지사자의 이름과 속성들의 형태로 사용됨.
			 page(required) : 페이지에 대한 환경 설정.
			 include(optional) : 정적 include
			 taglib(optional) : 커스텀 태그 로딩.
		3) expression : <%=data %>, <%=now %>
		4) declaration : 
		5) comment : <%-- --%>
			- client side comment : HTML, JS, CSS
<!-- comment -->
			- server side comment : Java, JSP
			<% // Java comment %>
			<%-- JSP comment --%> 
	3. action tag
	4. EL(표현언어)
	5. JSTL
</pre>
<script>
// comment
	console.log("body 랜더링 완료");
</script>
</body>
</html>