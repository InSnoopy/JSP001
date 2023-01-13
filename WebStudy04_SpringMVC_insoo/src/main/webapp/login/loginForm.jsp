<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- message가 있다면 경고창을 출력해라 -->
<c:if test="${not empty message }">
	<script>
		alert("${message}");
	</script>
	<!-- 확실히 지워주세요. scope는 필수로 적자. -->
	<c:remove var="message" scope="session"/>
</c:if>
</head>
<body>
<c:set var="memId" value="${memId }"/>
<c:set var="message" value="${message }"/>

	<form method="post" action="<c:url value='/login/loginProcess.do' />">
		<ul>
			<li>
				<c:set var="saveId" value="${cookie['savedId']['value'] }" />
				<!-- param이 있는 이유는 포어드 방식이 req.getRequestDispatcher(viewName).forward(req, resp); 이거이기 때문이다. -->
				<!-- 파라미터를 아직 기억하고 있다. -->
				<input type="text" name="memId" placeholder="아이디" value="${not empty validId ? validId : saveId}"/>
				<input type="checkbox" name="saveId" ${not empty saveId ? 'checked' : '' }/>아이디기억하기
				<!-- 위에 validId를 session에 저장되어 있으니 반드시remove로 지워주자 -->
				<c:remove var="validId" scope="session"/>
			</li>
			<li>				
				<input type="password" name="memPass" placeholder="비밀번호" />
				<input type="submit" value="로그인" />
			</li>
		</ul>
	</form>
</body>
</html>