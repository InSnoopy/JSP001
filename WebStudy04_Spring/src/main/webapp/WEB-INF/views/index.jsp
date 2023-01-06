<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<h4>WELCOME</h4>
<c:set var="principal" value="${pageContext.request.userPrincipal }"/>
<!-- authMember의 값이 있다면 로그인이 되어있는 상태를 말한다. -->
<c:choose>
	<c:when test="${not empty principal }">
		<c:set var="authMember" value="${principal.realMember }"/>
		<h4>로그인된 사용자??? : ${principal }</h4>
		프로필이미지:
		<img src="data:image/*;base64,${authMember.base64MemImg }" />
		<a href="<c:url value='/mypage.do' />">${authMember.memName }[${authMember.memRole }]님</a>
		<form name="logoutForm" action="<c:url value='/login/logout.do'/>" method="post"></form>
		<a href="#" class="logoutBtn">로그아웃</a>
		<script>
			$(".logoutBtn").on("click", function(event){
				// a클릭은 자동으로 submit이 발생한다?
				event.preventDefault();
				// 히든 포맷 방식
				document.logoutForm.submit();
				return false;
			});
		</script>
	</c:when>

	<c:otherwise>
		<a href="<c:url value='/login/loginForm.jsp'/>">로그인</a>
		<a href="<c:url value='/member/memberInsert.do'/>">회원가입</a>
	</c:otherwise>
</c:choose>


