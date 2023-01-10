<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form method="post" enctype="multipart/form-data">
	<input type="text" name="textParam" />
	<input type="date" name="dateParam" />
	<input type="file" name="file1" />
	<input type="file" name="file2" multiple="multiple" />
	<input type="submit" value="upload" />
</form>
<pre>
	업로드 결과
	${result.textParam }
	${result.dateParam }
	${result.file1Savename }
	<c:forEach items="${result.file2Savenames }" var="savename">
		${savename }
	</c:forEach>
<%-- <c:remove var="result" scope="session" /> --%>
</pre>
</body>
</html>