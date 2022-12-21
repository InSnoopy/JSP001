<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>10/pageContextDesc.jsp</title>
</head>
<body>
<h4>pageContext(pageContext)</h4>
<pre>
CAC(Context Aware Computing) 
서블릿 컨텍스트 - 서블릿이 존재하려면 그 이전에 서블릿을 가지고 있는 어플리케이션 -> 어플리케이션을 실행하려는 WAS가 필요
페이지 컨텍스트 - 현재 JSP 상황 판단을 하기위한 모든걸 가지고 있다?
	: 하나의 JSP 페이지와 관련된 모든 정보(기본 객체)를 가진 객체
	: pageContext는 JSP에서만 사용 가능 서블릿에서는 사용 불가
	1. EL 에서 주로 기본 객체를 확보할때 사용. (EL에서는 기본객체를 지원하지 않는다 - pageContext는 지원한다.)
	   ex) <%=request.getContextPath() %>,
	   	   <%=((HttpServletRequest)pageContext.getRequest()).getContextPath() %> <- 자바에서는 이렇게 번역된다.
	   	   ${pageContext.request.contextPath} -> 위에꺼 쓰려면 강제 캐스팅도 해야하고 길다 EL로 간략하게 작성 가능하다.
	2. 에러 데이터 확보
	3. 흐름 제어(요청 분기) : forward/include
	4. 영역 제어(*****)

</pre>
<h4>Scope</h4>
<pre>
	Servlet[JSP] Container : 서블릿 객체나 JSP 객체의 모든 관리 권한을 가진 주체(IoC-Inversion Of Control:제어권이 개발자가 아니라 서버로 넘어가는 것을 말한다. -> 모든 프레임 워크에 밑바닥에 깔렸다.).
						   : 프레임워크를 사용하기 전까지 개발자가 마음대로 하지만 프레임워크를 사용하는 순간 서버에 권한으 넘긴다.
    Scope : 웹 어플리케이션에서 데이터를 공유하기 위해 사용되는 저장 공간(Map&lt;String, Object&gt; 형태로 되어있다). 총 4개의 Map으로 구성되어 있다. 
    Attribute : scope를 통해 공유되는 데이터(String name/Object value).
    
    : Scope라는 저장 공간을 소유한 기본 객체의 생명주기와 동일함.
    page scope : pageContext의 소유 공간. (현재 페이지에서만 공유 가능 영역) -> 커스텀 태그를 사용할 경우 가끔 필요하다.
    request scope : 해당 영역의 소유 요청 객체가 소멸될때 함께 소멸됨.
    session scope : 해당 영역을 소유한 세션 객체와 생명주기 동일. (회원마다 요청하는게 다른 경우? session에 따라 내용이 다른 경우)
    application scope : ServletContext와 동일한 생명주기를 가짐. (모든 사용자에게 똑같은 내용을 보낼때는 여기 scope에 넣어두는게 좋다.)
    
    setAttribute(name, value), getAttribute(name), removeAttribute(name)
    
	서버가 무거워지기 때문에 session보다 jsp 인증 토큰으로 사용한다.
		 -> session, application을 사용하고 지우는 기능으로 자주 사용해줘야한다. (removeAttribute(name))
    
    <%
    	pageContext.setAttribute("pageAttr","페이지 속성");
		request.setAttribute("requestAttr", "요청 속성");
		session.setAttribute("sessionAttr", "세션 속성");
		application.setAttribute("applicationAttr", "어플리케이션 속성");
		
		pageContext.setAttribute("sample", "페이지샘플");
		pageContext.setAttribute("sample", "요청샘플", PageContext.REQUEST_SCOPE); // pageContext를 통해서 4개의 scope를 다 컨트롤 가능하다.
		
// 		1. forward
// 		String viewName = "/09/attrView.jsp";
// 		request.getRequestDispatcher(viewName).forward(request, response);
		// request, session, application 나옴

// 		2. include
		String viewName = "/09/attrView.jsp";
// 		request.getRequestDispatcher(viewName).include(request, response);
		// A의 결과 B의 결과를 +한거다.
		// 각각의 jsp는 다르기 때문에 pageContext는 다르다.
		// request, session, application 나옴
		
// 		3. redirect
		String location = request.getContextPath() + viewName;
// 		response.sendRedirect(location);
		// session, application 나옴

    %>
	
</pre>

<h4>공유된 속성 데이터들</h4>
<pre>
	- EL은 사용의 범위가 좁은것 부터 부른다. page->request->session->application 순서
	page sample : ${sample }
	request sample : ${requestScope.sample }

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