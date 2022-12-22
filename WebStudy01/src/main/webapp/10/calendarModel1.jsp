<%@page import="java.time.LocalDate"%>
<%@page import="javax.xml.crypto.Data"%>
<%@page import="java.util.Locale"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.text.DateFormatSymbols"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	DateFormatSymbols dfs = DateFormatSymbols.getInstance();
	String[] weekDays = dfs.getWeekdays();
	pageContext.setAttribute("weekDays", weekDays);
	String[] monthDays = dfs.getMonths();
	
	Calendar cal = Calendar.getInstance();

	cal.set(Calendar.DAY_OF_MONTH,1); //DAY_OF_MONTH를 1로 설정 (월의 첫날)
	int week = cal.get(Calendar.DAY_OF_WEEK); //그 주의 요일 반환 (일:1 ~ 토:7)

	pageContext.setAttribute("dfs", dfs);
	pageContext.setAttribute("week", week);
	pageContext.setAttribute("lastDay", cal.getMaximum(Calendar.DAY_OF_MONTH));
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<c:set var="dayCount" value="1"/>
<c:set var="offset" value="${week-1 }" />

<table>
<thead>
	<tr>
		<c:forEach var="idx" begin="<%=Calendar.SUNDAY %>" end="<%=Calendar.SATURDAY %>">
			<td>${weekDays[idx] }</td>
		</c:forEach>
	<tr>
</thead>
	<c:forEach begin="1" end="6">
		<tr>
		<c:forEach begin="<%=Calendar.SUNDAY %>" end="<%=Calendar.SATURDAY %>">
			<c:choose>
				<c:when test="${0 lt dayCount - offset and lastDay+1 gt dayCount - offset}">
					<td>${dayCount - offset} </td>
					<c:set var="dayCount" value="${dayCount+1 }"/>
				</c:when>
				<c:otherwise>
					<td></td>
					<c:set var="dayCount" value="${dayCount+1 }"/>
				</c:otherwise>
			</c:choose>
			

		</c:forEach>
		</tr>
	</c:forEach>
</table>

</body>
</html>

