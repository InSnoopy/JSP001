<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.Enumeration"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("EUC-KR"); // decoding
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	request line : <%=request.getRequestURL() %> <br>
	request line->query String : <%=request.getQueryString() %> <br>
 	request body : <%=request.getInputStream().available() %><br> 
	
	표준 입력양식(form)을 통해 입력된 파라미터 확보<br>
	String getParameter(name)<br>
	String[] getParameterValues(name)<br>
	Enumeration&lt;String&gt; emgetParameterNames()<br>
	Map&lt;String, String[]gt;getParameterMap();<br>
<table>
	<thead>
		<tr>
			<th>파라미터명</th>
			<th>파라미터값(들)</th>
		</tr>
	</thead>
	<tbody>
		<%
			Enumeration<String> names = request.getParameterNames();
			while(names.hasMoreElements()){
				String parameterName = names.nextElement();
				String[] values = request.getParameterValues(parameterName);
				%>
				<tr>
					<td><%=parameterName %></td>
					<td><%=Arrays.toString(values) %></td>
				</tr>
				<%
			}
		%>
	</tbody>
</table>

<table>
	<thead>
		<tr>
			<th>파라미터명</th>
			<th>파라미터값(들)</th>
		</tr>
	</thead>
	<tbody>
		<%
			Map<String, String[]> parameterMap = request.getParameterMap();
			for(Entry<String, String[]> entry : parameterMap.entrySet()){
				String parameterName = entry.getKey();
				String[] values = entry.getValue();
				%>
				<tr>
					<td><%=parameterName %></td>
					<td><%=Arrays.toString(values) %></td>
				</tr>
				<%
			}
		%>
	</tbody>
</table>
</body>
</html>