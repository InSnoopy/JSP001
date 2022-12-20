<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>88/sessionDesc.jsp</title>
<jsp:include page="/includee/preScript.jsp" />
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/custom.js"></script>
</head>
<body>
<h4>session(HttpSession)</h4>
<h4 id="timerArea"></h4>
<pre>
	(웹)세션이란?
		: 어플리케이션 서버를 사용하기 시작한 순간부터 사용 종료까지의 기한.
		
	시간(생성) : 클라이언트의 최초 요청(재전송되는 아이디가 없는 요청) 발생. -> 식별자가 부여된 세션이 새로 생성.
				->세션 아이디가 요청에 대한 응답이 전송될때 응답 헤더에 포함되어 클라이언트로 전송.
		세션 아이디 : <%=session.getId() %>
		세션 생성 시점 : <%=new Date(session.getCreationTime()) %>
		마지막 요청 시점 : <%=new Date(session.getLastAccessedTime()) %>
		timeout : <%=session.getMaxInactiveInterval() %>
		
		유지(tracking mode) : 세션 식별자(세션 아이디) 재전송 구조.
		1) COOKIE
		2) URL : <a href="sessionDesc.jsp;jsessionid=<%=session.getId()%>">URL트레킹모드</a>
			COOKIE, URL은 세션 아이디를 재전송만 하면 된다. (세션 파라미터)
			보안에 안좋다. URL에 다 노출되기 때문에 -> 다른 브라우저에도 사용이 가능하다.
		3) SSL (Secure Sockets Layer, SSL) - 보안 소켓 계층  : 오고가는 세션 아이디가 다 암호화가 된다.
			지금 우리 서버는 인증서를 발급받지 않았기 때문에.
		
	종료(만료)
		1) 세션의 아이디가 재전송되지 않을때. ex) 세션과 관련된 쿠키 삭제
		2) 브라우저가 종료될때
			-> 종료하고 다음 요청이 없을 시 만료된다. ( session timeout 설정된 값 )
		3) session timeout 이내에 새로운 요청을 통해 아이디가 재전송되지 않을때.
		4) session invalidation(명시적인 로그아웃)
			-> 이것만 그 즉시 세션이 해지된다. 나머지는 아니다.

</pre>
<div id="msgArea">
	세션을 연장하겠습니까?
	<input type="button" value="예" class="controlBtn" id="YES"/>
	<input type="button" value="아니오" class="controlBtn" id="NO"/>
</div>
<script>
	// jquery는 필수 파라미터와 옵셔널 파라미터를 설정해줘야 한다.
	// 옵셔널인 경우 [] 안에 작성한다.
	$("#timerArea").sessionTimer(${ pageContext.session.maxInactiveInterval }, {
		msgAreaSelector : "#msgArea", 
		btnSelector : ".controlBtn"
	});
</script>
<jsp:include page="/includee/postScript.jsp" />
</body>
</html>