<%@page import="java.io.File"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<%
	
	String imageFolder = application.getInitParameter("imageFolder");
	
	File folder = new File(imageFolder);
    File[] imageFiles = folder.listFiles();

	System.out.println(imageFolder);
	
	String name = "";
	String mime = "";
	String path = "";

    for(File f : imageFiles){
        name = f.getName();
        mime = application.getMimeType(name);
        path = f.getPath();
        out.print("name : "+name+"mime : "+mime+"path : "+path);
     }
	
%>
	<form action='<%=request.getContextPath() %>/imageStreaming.do'>
		<select name='imgChoice'>
			<%
			for(int i=0; i<imageFiles.length; i++){
				
			%>
			<option value="<%=imageFiles[i].getName()%>"><%=imageFiles[i].getName() %></option>
			<%
			}
			%>
		</select>
		<input type='submit' value='전송' />
	</form>


</body>
</html>