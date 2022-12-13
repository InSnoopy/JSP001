<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>05/cacheControl.jsp</title>
</head>
<body>
<h4>Cache 제어</h4>
<pre>
	cpu - 캐시 메모리 - 램
	
	cache 란? 시스템 내부에서 발생하는 속도 저하를 커버하기 위한 임시 저장 데이터.
	
	cache는 보안에 안좋다.
	cache는 실시간성이 없다.
	
	- 캐시를 제어하기 위한 3가지
	Pragma(http 1.0버전), Cache-Control(http 1.1버전) - 클라이언트가 어떤 버전인지 모르니 2개다 써야한다.
		: 캐싱 정책 설정용
		: no-cache -> 일단 저장하고 캐시 데이터 사용전 확인 절차를 거치도록 함. : 이 캐시 써도 되냐 최신 버전이 맞냐 이렇게 물어본다.
		: no-store -> 아예 저장하지 않는다.
		: must-revalidate -> no-cache랑 똑같은 의미를 가지고 있지만 다른 점은 시한이 만료된 캐시 데이터만..
		: public -> 클라이언트, 프록시 서버(클라이언트와 서버 사이에 있는)에서도 캐싱
		: private -> 클라이언트 캐싱
		
		- 만료 설정
		ex) 1. public;maxages=milliseconds (지금부터 앞으로의 시간)
		    2. Expires (구체적인 날짜)
		    
	Expires(만료되다라는 뜻)
		: 캐싱 데이터 만료 시한 설정용(구체적인 날짜)
		
	<%
		response.setHeader("Pragma", "no-store"); //1.0
		response.setHeader("Cache-Control", "no-store"); //1.0
		// response.setHeader("Pragma", "no-cache"); //1.0
		// response.setHeader("Cache-Control", "no-cache"); //1.0
		response.addHeader("Pragma", "no-cache"); //1.0
		response.addHeader("Cache-Control", "no-cache"); //1.0
		response.setDateHeader("Expires", 0); // 70년 1월 1일, 0시 0분 0초
	%> 
	
</pre>
</body>
</html>