<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.stream.Collectors"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>10/elCollection.jsp</title>
</head>
<body>
<h4>EL에서 집합 객체 접근 방법</h4>
<%
	String[] array = new String[]{"value1", "value2"};
	List<String> list = Arrays.asList(array);
	Set<String> set = list.stream().collect(Collectors.toSet());
	
	Map<String,Object> map = new HashMap<>();
	map.put("key-1", "value1");
	map.put("key 2", "value2");
	
	pageContext.setAttribute("array", array);
	pageContext.setAttribute("list", list);
	pageContext.setAttribute("set", set);
	pageContext.setAttribute("map", map);
	
	pageContext.setAttribute("memo", null);
%>
<pre>
	array : <%--=array[3] --%>, ${array[3] }
		-> 표현식은 500error, EL은 ""
		-> EL을 사용할 경우 대부분의 에러는 다 ""(화이트 스페이스로) 출력된다.
		-> EL은 출력하는것만 목적이기 때문이다.
	list : <%=list.get(1) %>, ${list[1]}, ${list.get(1) }
		-> EL은 메소드를 호출하지 못한다.? 아닌데.. 된다. 그런데도 메소드를 사용하지 않는 이유는? 아래에 적어놈ㅋ
		-> list.get(1)를 list[1]로 그냥 사용하는 이유는 list.get(1)로 하면 500에러가 날 수 있기 떄문이다.
			: 동작 방법이 다르다. EL은 출력하는것만 목적이기 때문에 왠만하면 EL에서는 메소드를 직접 호출하지 말라는 것 뿐이다. 사용을 못하는게 아니다.!
		-> EL은 array와 동일한 방법으로 출력한다.
		
	ex)memo's writer : ${memo.writer }, ${memo['writer'] }, \${memo.getWriter() },
	: null이 떠야하지만 ""공백이 뜬다.
	: 원래 mem.getWriter()썼을 때 null에러가 떠야하는제 지금 el버전이 좋아서 그런지 에러가 안뜬다.
	: private String writer; 이기 때문에 원래 자동으로 getWriter로 사용하게 된다.
	
	set : ${set }
	: 전체만 꺼낼 수 밖에 없다.
	: EL은 set 하나만 꺼낼 수가 없다.
	
	map : <%=map.get("key-1") %>, ${map.get("key-1") }, ${map.key-1 }, ${map['key-1'] }
		: 마지막이 제일 안전하다.
	map : <%=map.get("key 2") %>, ${map.get("key 2") }, \${map.key 2 }, ${map['key 2'] }
		: 이것 또한 마지막이 안전하다.
		: 결국 연산배열로 사용하는게 제일 안전하다.
</pre>
</body>
</html>