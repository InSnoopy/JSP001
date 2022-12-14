<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" buffer="8kb"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h4>response(HttpServletResponse)</h4>
<pre>
	Http 의 response packaging
	1. Response Line : Status Code(응답상태코드, XXX)
		100~ : ...ING...
		200~ : OK
		300~ : 최종 처리하기 위해 클라이언트의 추가 액션이 필요함. (response body가 없다.) (서버나 프레임워크에서 자동으로 셋팅)
			304(cache data 관련): Not Modified - 캐싱된 자원이 최신 버전인지 확인하고
				: 버전이 최신이면 그대로 캐싱에 있던 자원을 쓰고
				: 버전이 최신 버전이 아니라면 200코드 보내주며 다시 새로운 자원을 받는다.
				: 은행 관련 어플에서는 캐싱이 항상 최신이여야 한다. 이걸 header에 보낸다?
			301/302/307 : Moved + 자원에 위치를 보내준다. (location header)에 위치를 적어서 보내준다. 다시 새롭게 요청한다. (redirect request)
				: http://www.naver.com을 요청하지만 다시 '리다이렉트'로 다시 https://www.naver.com로 다시 요청해준다.
				: 잠시 이동? 보안 이동? 뭐 이런거에 따라서 다르다.
				<%
					// request.getRequestDispatcher("/04/messageView.jsp").forward(request, response); // 서버 내에서 이동
					// String location = request.getContextPath() + "/04/messageView.jsp"; 
					// response.sendRedirect(location); // 클라이언트로부터 새로운 요청이 발생.
				%>
		400~ : client side error -> Fail (개발자가 직접 셋팅)
			400 : 클라이언트 전송 데이터(파라미터) 검증시 활용.
				: <%=HttpServletResponse.SC_BAD_REQUEST %> 
			401 / 403 : 인증(Authentication-신원 확인)과 인가(Authorization-신원이 확인된 사람에게 권한을 부여) 기반의 접근 제어에 활용
				: 401-신원 확인이 안된 회원이 요청할 경우. 이 자원은 반드시 인증이 거친 회원만 가능합니다.하고 메시지를 보낸다.
				: 403-관리자 회원, 일반 회원 같은 회원이지만 관리자에게만 권한을 부여한다. 일반 회원이 관리자 회원의 권한을 사용하려면 (넌 못들어와!)그 때 알려주는 에러 코드 
				: <%=HttpServletResponse.SC_UNAUTHORIZED %>, <%=HttpServletResponse.SC_FORBIDDEN %>
			404 : 메시지에 대한 자원이 없을 경우, URL의 주소를 잘못 요청할 경우
				: <%=HttpServletResponse.SC_NOT_FOUND %>
			405 : Request Method가 없는 경우, 현재 요청의 메소드에 대한 콜백 메소드가 재정의되지 않았을 때.
				: <%=HttpServletResponse.SC_METHOD_NOT_ALLOWED %>
			406 / 415 : content-type(MIME)과 관련된 상태코드
			406 : html을 요청했는데 html을 못보내준다. response의 body를 못 만든다.
				: <%=HttpServletResponse.SC_NOT_ACCEPTABLE %> : Accept request 헤더에 설정된 MIME 데이터를 만들어 낼 수 없을 때
				: ex) accept:allication/json
					  content-type: application/json
					  이렇게 두개가 한 쌍이여야 한다. 하지만 2번째에서 만들어내지 못하는 경우.
			415 : 서버에서 자원을 JSON 형식으로 보내는데 나는 JSON을 해석하지 못한다. 
				: 이건 편지를 서버에서 못읽는다는 뜻 , reqeust의 body를 해석하지 못한다.
				: <%=HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE %> : Content-Type request 헤더를 해석할 수 없을때.
				: ex) content-type:application/json --> unmarshalling()
				
		500~ : server side error -> Fail 
			500 : Internal Server Error)
		
	2. Response Header : meta data
		Content(body) 에 대한 부가정보 설정 : Content-*, Content-Type(MIME), Content-Length(size)
								Content-Disposition(content name, 첨부여부)
			<%--
				response.setHeader("Content-Disposition", "inline[attatchement];filename=\"파일명\"");
			--%>
		Cache control : 자원에 대한 캐싱 설정
		Auto Reuqest : 주기적으로 갱신되는 자원에 대한 자동 요청
		Location 기반의 이동구조(Redirection).
		
	3. Response Body(Content body, message body) : 
<%-- 		response.getWriter(), response.getOutputStream(), <%= %>, out <!-- steam을 이용해서 body에 작성함 --> --%>
<%--8kbyte 짜리 버퍼를 사용하고 있다?? ->buffer="8kb"--%>
</pre>
</body>
</html>