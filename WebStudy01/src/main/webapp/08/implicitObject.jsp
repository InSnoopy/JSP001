<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>08/implicitObject</title>
</head>
<body>
<h4>기본객체(내장객체)</h4>
<pre>
	request(HttpServletRequest) : 클라이언트와 그로부터 발생한 요청에 대한 정보를 캡슐화한 객체
	response(HttpSErvletResponse) : 서버에서 클라이언트로 전송되는 응답에 대한 정보를 캡슐화한 객체
	out(JspWriter) (버퍼를 통해서 body에 컨텐츠를 기록하기 위해) : response.getWriter(), 왼쪽으로 response body에 컴텐츠를 기록(버퍼를 제어)했음 
	session(HttpSession) : 한 클라이언트와 하나의 브라우저를 대상으로 생성되는 한 세션에 대한 정보를 캡슐화한 객체.
	application(ServletContext) : 하나의 컨텍스트와 서버에 대한 정보를 캡슐화한 객체.
	
	page(Object) == this, custom tag 작성시 활용됨.
	config(ServletConfig) : 현재 서블릿의 설정 정보를 캡슐화한 객체.
	
	pageContext(PageContext **중요**) : 현재 JSP 페이지에 대한 모든 정보를 캡슐화한 객체.
	${pageContext.request.contextPath} 
		
	exception : 발생한 에러(예외)에 대 정보를 캡슐화한 객체. , page 지시자의 isErrorPage가 활성화된 경우에만 사용가능.
</pre>
</body>
</html>