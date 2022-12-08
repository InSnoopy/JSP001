<%@page import="java.nio.file.Paths"%>
<%@page import="java.nio.file.StandardCopyOption"%>
<%@page import="java.nio.file.Files"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.net.URLConnection"%>
<%@page import="java.net.URL"%>
<%@page import="kr.or.ddit.servlet01.DescriptionServlet"%>
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
<h4>자원의 종류와 식별방법</h4>
<pre>
	: 자원의 위치와 경로 표기 방법에 따라 분류
	
	1. File system resource : d:/contents/images/cat1.jpg
	<%
		String realPath = "d:/contents/images/cat1.jpg";
		File fileStsremResource = new File(realPath);
	%>
	파일의 크기 : <%=fileStsremResource.length() %>
	
	2. Class path resource : /kr/or/ddit/images/cat2.png
	<%
		// /kr/or/ddit/images/cat2.png
		String qualifiedName = "../images/cat2.png";
		// 
		realPath = DescriptionServlet.class.getResource(qualifiedName).getFile();
		// classLoader()는 상대주소는 사용 안한다. 무조건 클래스패스로 앞에 /를 쓰지 않는다.
		DescriptionServlet.class.getClassLoader().getResource("kr/or/ddit/images/cat2.png").getFile();
		File classPathResource = new File(realPath);
	%>
	실제 경로 : <%=realPath %>
	파일의 크기 : <%=classPathResource.length() %>
	
	3. Web resource : https://www.google.com/logos/doodles/2022/seasonal-holidays-2022-6753651837109831.3-law.gif
	<!-- /WebStudy01/src/main/webapp/resources/js/jquery-3.6.1.min.js -->
	http://localhost/WebStudy01/resources/js/jquery-3.6.1.min.js
	<%
		// String resourceURL = "https://www.google.com/logos/doodles/2022/seasonal-holidays-2022-6753651837109831.3-law.gif";
		String resourceURL = "http://localhost/WebStudy01/resources/js/jquery-3.6.1.min.js";
    	URL url = new URL(resourceURL);
		URLConnection conn = url.openConnection();
		String resourcePath = url.getPath();
		int lastIdx = resourcePath.lastIndexOf('/');
		String fileName = resourcePath.substring(lastIdx+1);
		String folderPath = "d:/contents/images";
		File downloadFile = new File(folderPath, fileName);
		InputStream is = conn.getInputStream();
		Files.copy(is, Paths.get(downloadFile.getPath()), StandardCopyOption.REPLACE_EXISTING);
		// InputStream is = conn.getInputStream();
	%>
	
	resourcePath : <%=resourcePath %>
	
	*** 웹자원에 대한 식별성 : URI
	URI(Uniform Resource Identifier) - 자원을 식별하기 위해서
	
		URL(Uniform Resource Locator) - 전용 자원의 위치 (앞에서 몇번째):자리 변경하면.. 큰일나
		URN(Uniform Resource Name) - 전용 자원의 이름 (나래누나 일어나):이름이 같으면.. 큰일나
			이름들이 다 등록되어 있는 출석부가 있어야 한다.
		URC(Uniform Resource Content) - 전용 자원의 조건들 (이런 조건인 사람 일어나): 속성
			작은 나래, 큰 나래 -> 작은, 큰이라는 조건
	
	URL 구조 ("http://localhost/WebStudy01/resources/js/jquery-3.6.1.min.js";) 
	protocol(scheme)://IP(DN):port/content/depth1...depthN/resourceName
		// -> 루트 표현
		IP(도메인) ->
			DomainName
				3 level www.naver.com 
					com -> GlobalTopLevelDomain(GTLD) - 기관만 포함
				4 level www.naver.co.kr
					co.kr -> NationalTopLevelDomain(NTLD) - 국가가 포함되어 있다.
			
			* www.naver.com을 누르면 dnd가 받아서 ip로 다시 보내준다.
	
	URL 표기 방식
	절대경로(**) : 최상위 루트부터 전체 경로 표현 - 생략가능한 요소가 존재.
		절대 경로는 앞에 /가 붙는다.
		절대 경로가 어떤 주소로 표기할지에 따라서 아래 처럼 달라진다.
		client side : /WebStudy01/resources/images/cat1.jpg
					: context path 부터 시작된다.
		server side : /resources/images/cat1.jpg
					: context path 이후의 경로 표기.
		forward(server side), sendRedircet(client side) 차이			
		
	상대경로 : 기준점(브라우저의 현재 주소)을 중심으로 한 경로 표현
	
</pre>
<%
	// request.getContextPath()를 붙일 거라 생각하겠지만
	// 서버사이드에서 접근할 때는 contextPath가 필요하지 않는다.
	// 서버는 contextPath에 대한 정보를 이미 가지고 있다.
	// 서버사이드에서는 상대 경로는 사용하지 않는다.
	// InputStream is2 = application.getResourceAsStream("/resources/images/cat1.jpg");
	String realPath1 = application.getRealPath("/resources/images/cat1.jpg");
	String realPath2 = application.getRealPath(request.getContextPath()+"/resources/images/cat1.jpg");
	
	// 서버 사이드
	request.getRequestDispatcher("/WEB-INF/views/depth1/test.jsp").forward(request, response);
	// 클라이언트 사이드
	response.sendRedirect(request.getContextPath() + "/member/memberForm.do");
%>

<!-- 
	절대경로(**) : 생략할 수 있는 경로는 생략 가능 (조건은 이미 어딘가에 저장되어 있는 주소라면)
	"http://localhost/WebStudy01/resources/images/cat1.jpg"
	http 생략 가능 -) location 객체에 있다.
	localhost 생략 가능 -> location 객체에 있다.
	href안에 있는 주소를 가져온거다.
	
	현재 위치는 무조건 브로우저 주소로 확인한다.
	상대주소는 현재 브라우저의 위치의 기준으로부터 찾는다.
-->
<img src="<%=request.getContextPath()%>/resources/images/cat1.jpg" />
<img src="../resources/images/cat1.jpg" />
<img src="cat1.jpg" /><br/>
<%-- 서버사이드 방식으로 접근한 파일의 크기 : <%=is2.available() %>  --%>
realPath1 : <%=realPath1 %> <br/>
realPath2 : <%=realPath2 %>
</body>
</html>