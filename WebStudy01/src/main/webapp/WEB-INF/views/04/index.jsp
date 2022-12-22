<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<h4>WELCOME</h4>

<!-- authMember의 값이 있다면 로그인이 되어있는 상태를 말한다. -->
<c:choose>
	<c:when test="${not empty sessionScope.authMember }">
		<form name="logoutForm" action="<c:url value='/login/logout.do'/>" method="post"></form>
		<a href="#" class="logoutBtn">${authMember.memId } 님 로그아웃</a>
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
	</c:otherwise>
</c:choose>


