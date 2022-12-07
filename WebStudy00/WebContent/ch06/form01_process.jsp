<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="kr.or.ddit.vo.MemberVO"%>
<!-- JSTL
	JSP Standard Tag Library
	JSP에서 자주 사용되는 태그들을 묶어놓은 라이브러리 
 -->
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript" src="<%=request.getContextPath() %>/ckeditor/ckeditor.js"></script>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%       
   request.setCharacterEncoding("utf-8");

   MemberVO memberVO = new MemberVO();
   
   String id      = request.getParameter("id");
   String passwd  = request.getParameter("passwd");
   String name    = request.getParameter("name");
   String postNum = request.getParameter("postNum");
   String phone1  = request.getParameter("phone1");
   String phone2  = request.getParameter("phone2");
   String phone3  = request.getParameter("phone3");
   String gender  = request.getParameter("gender");
   String hobby1  = request.getParameter("hobby1");
   String hobby2  = request.getParameter("hobby2");
   String hobby3  = request.getParameter("hobby3");
   String comment = request.getParameter("comment");
   
   memberVO.setId(id);
   memberVO.setPasswd(passwd);
   memberVO.setName(name);
   memberVO.setPostNum(postNum);
   memberVO.setPhone1(phone1);
   memberVO.setPhone2(phone2);
   memberVO.setPhone3(phone3);
   memberVO.setGender(gender);
   memberVO.setHobby1(hobby1);
   memberVO.setHobby2(hobby2);
   memberVO.setHobby3(hobby3);
   memberVO.setComment(comment);

	// 내장 객체 => session => 동일한 웹브라우저에서 공유
	session.setAttribute("memberVO", memberVO);
   
%>

	<!-- 아스가르트 (=memberVO)의 토르가 지구의 토르(memberVO)로 넘어옴 -->
	<c:set var="memberVO" value="<%=memberVO%>" />
	<p>아이디: ${memberVO.id}</p>
	<p>비밀번호:${memberVO.passwd}</p>
	<p>이름:${memberVO.name}</p>
	<p>연락처:${memberVO.phone1}-${memberVO.phone2}-${memberVO.phone3}</p>
	<p>성별:${memberVO.gender}</p>
	<p>취미:${memberVO.hobby1} ${memberVO.hobby2} ${memberVO.hobby3}</p>
	<p>
		<textarea rows="3" cols="30" name="comment">${memberVO.comment}</textarea>
	</p>
	
	<%
	// 5초 후에 form01.jsp로 되돌아감
	response.setHeader("Refresh", "5;URL=form01.jsp");
	%>
<script type="text/javascript">
CKEDITOR.replace("comment");
</script>
</body>
</html>