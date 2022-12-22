<%@page import="java.net.URLEncoder"%>
<%@page import="kr.or.ddit.commons.wrapper.CookieHttpServletRequestWrapper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>11/cookieDesc.jsp</title>
</head>
<body>
<h4>Cookie</h4>
<pre>
	<!-- session은 한 서버에 여러 클라이언트가 요청할 수 있기에 긴 시간 유지하지 않는다. -->
	session (server) vs cookie (client)
		: http 의 stateless 특성으로 인해 커뮤니케이션 정보가 유지되지 않을 경우, 사용하는 저장소의 개념
		
	사용 단계
	1. 쿠키 객체 생성(name, value)
	2. 응답(헤더, set-cookie)에 포함시켜 전송
	<%
		Cookie firstCookie = new Cookie("firstCookie", "firstValue");
		response.addCookie(firstCookie);
		String koreanValue = URLEncoder.encode("한글값", "UTF-8"); // 값을 넣더라도 인코딩을 해서 보내야 한다.
		Cookie koreanCookie = new Cookie("koreanCookie", koreanValue);
		response.addCookie(koreanCookie);
		// Cookie domainCookie = new Cookie("domainCookie", "domain cookie value");
		// 옵셔널 쿠키는 여기다가 담자
		// domainCookie.setDomain("www.naver.com"); //에러난다 localhost랑 맞지 않아서 내 서버랑 맞지 않아서..
		// response.addCookie(domainCookie);
		
		// 다른 경로에 쿠키를 저정하기!
		Cookie otherPathCookie = new Cookie("otherPathCookie", "otherPathCookieValue");
		// 클라이언트 기준이기에 컨텍스트패스를 붙여야한다.
		otherPathCookie.setPath(request.getContextPath() + "/12");
		response.addCookie(otherPathCookie);
		
		Cookie longLiveCookie = new Cookie("longLiveCookie", "longLive");
		// 이건 로컬호스트면 다 가능한 패스 경로
		longLiveCookie.setPath("/");
		// 우리 어플리케이션에서는 어디서든 사용 가능한 패스 경로
		// longLiveCookie.setPath(request.getContextPath());
		// 1주일동안 유지하게 한다. (쿠키를..)
		longLiveCookie.setMaxAge(0);
		response.addCookie(longLiveCookie);
	%>
	
	3. 브라우저가 자기 저장소에 저장.
	4. 다음번 요청(헤더, cookie)을 통해 재전송
	
	5. 요청에 포함된 쿠키를 통해 상태를 복원.
	<a href="viewCookie.jsp">동일 경로에서 쿠키 확인</a>
	<a href="../12/viewCookie.jsp">다른 경로에서 쿠키 확인</a>
	<%--
		// 어뎁터 패턴으로 사용하는걸 추천한다. 
		// 모든지 어뎁터 패턴을 생각해보는 연습을 하자!
		String findedValue = new CookieHttpServletRequestWrapper(request).getCookieValue("koreanCookie");
		out.println("쿠기 값 : "+findedValue);
	--%>
		
	** 쿠키 속성들
	필수 속성
		name : 식별자
		value : String, url encoded value
	부가 속성
		domain(host) : 다음번 요청에 포함시켜 재전송할지 여부를 결정하는 조건.
					ex) .naver.com, www.naver.com
		path : 다음번 요청에 포함시켜 재전송할지 여부를 결정하는 조건.
			path 설정이 명시되지 않을 경우, 쿠키가 처음 생성된 경로가 반영됨.
		maxAge : 쿠키의 만료 시한. 기본값 : 세션 만료 시한.
				ex) -1, 0
				0 : 삭제를 해준다. -> 주의사항 (name, value.. 등 모든 속성이 동일한 쿠키인 경우만 삭제);
			   -1 : 브라우저가 꺼지면 삭제해라.
		secure... : 안전한 상황(https)에서만 재전송하게 된다.
		
</pre>

</body>
</html>