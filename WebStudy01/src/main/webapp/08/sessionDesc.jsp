<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>88/sessionDesc.jsp</title>
<script src="<%=request.getContextPath() %>/resources/js/jquery-3.6.1.min.js"></script>
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
		3) session timeout 이내에 새로운 요청을 통해 아이디가 재전송되지 않을때.
		4) session invalidation(명시적인 로그아웃)

</pre>
<script>

	let obj_timerArea = $("#timerArea");
	let y = <%=session.getMaxInactiveInterval() %>
	
	let timeCount = function(){
		let x
		y--;
		obj_timerArea.html("<p>"+y+"</p>")
	    if(y==60){
	    	x = confirm("60초 남았습니다. 연장하시겠습니까?");
	    	console.log(x);
	    }
		if(x==true){
			y+=60;			
		}
		if(y<=0){
			session.invalidate();
			return;
		}
	};
	
	$.fn.sessionTimer=function(time){
	    setInterval(timeCount, 1000);
		return this;
	}
	
	let x = $("#timerArea").sessionTimer(y);

	
</script>
</body>
</html>