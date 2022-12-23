<%@page import="java.time.LocalDate"%>
<%@page import="javax.xml.crypto.Data"%>
<%@page import="java.util.Locale"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.text.DateFormatSymbols"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<jsp:include page="/includee/preScript.jsp"/>
</head>
<body>

<c:set var="dayCount" value="1"/>
<c:set var="offset" value="${calendar.offset }" /> 
<c:set var="lastDate" value="${calendar.lastDate }" /> 
<c:set var="weekDays" value="${calendar.weekDays }" />
<c:set var="months" value="${calendar.months }" />
<c:set var="currentYear" value="${calendar.currentYear }" />
<c:set var="currentMonth" value="${calendar.currentMonth }" />

<h4>
	<!-- monveBtn 클래스는 똑같고 data로 구분한다. -->
	<a href="#" class="moveBtn" data-year="${calendar.beforeYear }" data-month="${calendar.beforeMonth }">이전달</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<!-- calendar에 이미 toString했기 때문에 --> 
	${calendar }&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<a href="#" class="moveBtn" data-year="${calendar.nextYear }" data-month="${calendar.nextMonth }">다음달</a>
</h4>
<!-- form태그에 따라서 파라미터가 넘겨지는 갯수가 달라진다. -->
<!-- 요청에 발생되는 창고를 하나로 합치자. -->
<form id="calendarForm">
	<input type="number" name="year" placeholder="2022" value="${currentYear }"/>
	<select name="month">
		<!-- 향상된 for문 여기서 index를 원할때 varStatus에서 꺼낼 수 있다. -->
		<c:forEach items="${months }" var="month" varStatus="vs"> 
			<c:if test="${not empty month }">
				<!-- selected에 추가하는 방법 -->
				<c:set var="selected" value="${vs.index eq currentMonth ? 'selected' : '' }"></c:set>
				<option value="${vs.index }" ${selected }>${month }</option>
			</c:if>
		</c:forEach>
	</select>
	<select name="language">
		<!-- static 메서드에 리턴값이 있을 때 사용 가능 + import Locale필수 -->
		<c:forEach items="${Locale.getAvailableLocales() }" var="tmp">
			<c:if test="${not empty tmp.displayLanguage }">
				<c:set var="selected" value="${tmp eq calendar.locale ? 'selected' : '' }" />
				<c:set var="languagechoice" value="${tmp.toLanguageTag()}" />
				<!-- 자바빈 규약에 맞춰진 메서드랑 아닌거랑의 차이 () 있고 없고 차이 -->
				<option value="${tmp.toLanguageTag() }" ${selected }>${tmp.displayLanguage }(${tmp.displayCountry })</option>
			</c:if>
		</c:forEach>
	</select>
	<input type="submit" value="전송" />
</form>
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
			<c:set var="dayStr" value="${dayCount - offset }" />
			<c:choose>
				<c:when test="${dayStr gt 0 and dayStr le lastDate }">
					<td>${dayStr }</td>
				</c:when>
				<c:otherwise>
					<td>&nbsp;</td>	
				</c:otherwise>
			</c:choose>
			<c:set var="dayCount" value="${dayCount+1 }"></c:set>	
		</c:forEach>
		</tr>
	</c:forEach>
</table>
<script type="text/javascript">
	let calendarForm = $("#calendarForm");
	$("a.moveBtn").on("click",function(event){
		let year = $(this).data("year");
		let month = $(this).data("month");
		
		calendarForm.find("[name=year]").val(year); // 변경될 년도를 val값을 변경해서 보내야한다.
		// calendarForm.get(0).month.value=month; // html엘리먼트는 input태그의 name을 properyt를 가지고 있다. // 위에 방법이랑 아래 방법이랑 똑같다.
		calendarForm.find("[name=month]").val(month);
		
		calendarForm.submit(); // calendarForm을 전송하는것과 동일하다.
	});
</script>
</body>
</html>

