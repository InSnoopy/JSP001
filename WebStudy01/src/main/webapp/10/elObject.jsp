<%@page import="java.util.Optional"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>10/elObject.jsp</title>
</head>
<body>
<h4>EL 기본 객체</h4>
<pre>
	- EL 기본 객체는 총 11개지만 크게 쪼개면 아래처럼 나눌수 있다.
	- 1~5번은 다 Map
	1. scope 객체 (Map&lt;String,Object&gt;) : pageScope, requestScope, sessionScope, applicationScope
	2. 파라미터 객체 : param(Map&lt;String,String&gt;), paramValues(Map&lt;String,String[]&gt;)
		<a href="?name1=value1&name1=value1&name2=value2">TEST</a>
		<%=request.getParameter("name1") %>, ${param.name1 }, ${param['name1'] }
		<%=request.getParameterValues("name1") %>, ${paramValues.name1 }, ${paramValues['name1'] } 
		<%--=request.getParameterValues("name1")[1] --%>, ${paramValues.name1[1] }, ${paramValues['name1'][1] } 
	3. (요청)헤더 객체 : header(Map&lt;String,String&gt;), headerValues(Map&lt;String,String[]&gt;)
		: EL은 출력만하기 때문에 클라이언트 기준으로 요청만을 원한다.
		<%--=request.getHeader("Accept") --%>, 
		<%=request.getHeader("user-agent") %>, 
		\${header.user-agent }, 
		${header['user-agent'] }
	4. 쿠키 객체 : cookie(Map&lt;String,Cookie&gt;) (key)JSESSIONID=(value)174CC759C9D61AE2124928F28CB3E660
		: 값이 Cookie라는 객체인 이유는 브라우저에서 f12에서 Application-> Cookie를 들어가면 여러가지 속성들이 있다. 이런 속성들을 관리할 수 있는 객체로 넣는다.
		<%--Cookie test; test.getName(); --%>
		<%=request.getCookies() %>
		: 위에는 꺼내는 방법이 엄청 복잡하다. for문도 돌려야하고.. 하지만 el은 단순하다.
		${cookie.JSESSIONID.getValue() }, 
		${cookie.JSESSIONID.value }, : 이런식으로 getValue()의 메소드로 호출하지 않고 쓴다. 
		${cookie['JSESSIONID']['value'] }
	5. 컨텍스트 파라미터 객체 : initParam(Map&lt;String,String&gt;) (imageFolder는 web.Xml에서 가져온거다.)
		<%=application.getInitParameter("imageFolder") %>
		${initParam.imageFolder }, ${initParam['imageFolder'] }
	
	- 유일하게 자기 파일 객체를 유지하는 객체
	- . (get으로 다 가져오는 것이다.)
	6. pageContext : ${pageContext.request.contextPath }, ${pageContext.session.id }
</pre>
<!-- 현재 요청의 파라미터 중 (sample) 이라는 이름의 파라미터 값을 출력하라(EL). -->
<!-- 단, 해당 파라미터가 없는 경우, ("SAMPLE")이라는 기본값을 사용함. -->

${not empty param['sample'] ? param.sample : "SAMPLE"}
<%=Optional.ofNullable(request.getParameter("sample")).orElse("SAMPLE") %>
http://localhost/WebStudy01/10/elObject.jsp?sample=TEST <- 이렇게 요청시

</body>
</html>