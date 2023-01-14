<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>member/memberList.do</title>
<jsp:include page="/includee/preScript.jsp" />
</head>
<body>
<h4>회원목록 조회</h4>

<table class="table table-bordered">
	<thead class="thead-dark">
		<tr>
			<th>일련번호</th>
			<th>회원아이디</th>
			<th>회원명</th>
		</tr>
	</thead>
	<tbody>
		<c:set var="memberList" value="${pagingVO.dataList }" />
		<c:choose>
			<c:when test="${not empty memberList }">
				<c:forEach items="${memberList }" var="member">
					<tr>
						<td>${member.rnum }</td>
						<td>${member.memId }</td>
						<td>${member.memName }</td>
					</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr>
					<td colspan="3">조건에 맞는 회원이 없음.</td>
				</tr>
			</c:otherwise>
		</c:choose>
	</tbody>
</table>

<script type="text/javascript">
	
</script>
<jsp:include page="/includee/postScript.jsp" />
</body>
</html>