<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" buffer="1kb" autoFlush="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>07/bufferDesc.jsp</title>
</head>
<body>
<!-- 
	buffer="8kb",
	autoFlush="true"
	둘다 out이 관리한다. -->
<h4>out : JspWriter</h4>
<pre>
	출력 버퍼 관리자.
	현재 버퍼의 크기 : <%=out.getBufferSize() %>
	잔여 버퍼의 크기 : <%=out.getRemaining() %>
	<!-- 버퍼 오버플로우가 발생되기 전에 방출한다. 단) auto flush가 true인 경우-->
	auto flush 지원 여부 : <%=out.isAutoFlush() %>
	<%
		for(int i=1; i<=100; i++){
			// 한글 2바이트, 공백+숫자 1바이트씩
			// 한번에 10바이트
			out.println(i+"번째 반복");
						
			if(out.getRemaining()<50)
				out.flush();
			if(i==100)
 				// throw new RuntimeException("강제 발생 예외");
				// 위에 out.flush()가 이미 방출되어서 forward쪽에서는 다시 방출이 안된다.
				// Dispatcher는 한번의 방출과 한번의 버퍼만 있을뿐
				// request.getRequestDispatcher("/02/standard.jsp").forward(request, response);
				// include는 동작이 된다.
				// request.getRequestDispatcher("/02/standard.jsp").include(request, response);
				
				// 포어드에서는 버퍼를 한번 지워야 한다.
				// include는 버퍼를 지워도 되지 않기 때문에
				
				// include는 페이지 모듈화에 사용
				// 이동하기 전에 flush를 하고 이동한다는 뜻
				pageContext.include("/02/standard.jsp", true);
				// 이동하기을 하더라도 flush를 이동하지 않고 
				pageContext.include("/02/standard.jsp", true);
		}
	%>
</pre>
</body>
</html>