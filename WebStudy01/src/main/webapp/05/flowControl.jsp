<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>05/flowControl.jsp</title>

<!-- jsp 액션 태그  :앞에 jsp를 프릭스트?-->
<!-- org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/includee/preScript.jsp", out, false); -->
<%-- <jsp:include page="/includee/preScript.jsp"></jsp:include> --%>
<%@ include file="/includee/preScript.jsp" %> <!-- JSP파일 그대로 가져온다. -->
<%=varOnPre %> <!-- 정적 내포로 사용하면 에러가 안난다. -->

</head>
<body>
<h4>흐름 제어 방법</h4>
<pre>
	Http : 
		Connect-less, 
		State-less - (단점 보안 : 세션)
	A -> B 이동 방식
	
	1. 요청 분기(request dispatch) : A를 대상으로 한 최초의 요청이 계속 유지됨.
		1) forward(Model2) : A(request 처리, controller)->B(response 생성, view)에서 이동 후의 최종 응답은 B에서 전송.
		2) include(페이지 모듈화) : A -> B -> A (최종 응답에는 A+B의 모든 데이터가 포함됨.)
			내포되는 시점과 내포되는 대상에 따라 분류됨.
			- 정적 내포 : 컴파일전에 소스가 파싱되는 단계에서 소스파일이 내포됨.
			- 동적 내포 : 실행시에 실행의 결과 데이터가 내포됨.
		<%
			request.setAttribute("attr1", new Date()); // Attribute에는 데이터 타입이 상관없다.
			String path = "/02/standard2.jsp";
			// http://localhost/WebStudy01/05/flowControl.jsp?param1=value1로 요청
		
			// request.getRequestDispatcher(path).forward(request, response);
			// request.getRequestDispatcher(path).include(request, response); // 2개의 jsp가 다 화면에 출력된다.
			pageContext.include(path); // 위에 있는 include와 같다. 출력되는 스트림만 다를 뿐
			
		%>		
	2. Redirect : 
		클라이언트 -> A -> response body 가 없고, Line(302) + Header(Location) 로만 구성된 응답이 전송
		-> Location 방향으로 새로운 요청을 전송함.
		-> B에서 Body를 가진 최종 응답이 전송됨.
		<%--
			session.setAttribute("attr2", "세션 속성");
			String location = request.getContextPath() + path; 
			response.sendRedirect(location);
			// response.sendRedirect(location); // param1 안넘어온다, attr1도 안넘어간다.
			// response.sendRedirect(location+"?param1="+request.getParameter("param1")); // param1 넘어온다.
		--%>
</pre>
</body>
</html>