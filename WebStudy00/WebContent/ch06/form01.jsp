<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="kr.or.ddit.vo.MemberVO"%>
<%
//session.setAttribute("memberVO", memberVO);
//session.getAttribute() => Object가 리턴됨
MemberVO memberVO = (MemberVO)session.getAttribute("memberVO");
%>
<c:set var="memberVO" value="<%=memberVO%>" />
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/ckeditor/ckeditor.js"></script>
<title>Form Processing</title>
<script type="text/javascript">
$(function(){
// 	alert("개똥이");
})
</script>
</head>
<body>
<!-- input 태그 정리 
   1. type
      - text : 한 줄 텍스트 입력
      - radio : 라디오 버튼 중 하나만 선택
      - checkbox : 다중 선택
      - password : 암호 입력
      - hidden : 숨기기
      - file : 파일 업로드
      - button : 버튼 모양
      - reset : 폼데이터 초기화
      - submit : 서버로 전송
   2. name : 입력 양식 식별(유일함)
   3. value : 초깃값 설정
-->
   <h3>회원 가입</h3>
   <form action="<%=request.getContextPath() %>/ch06/form01_process.jsp" name="member" method="post">
      <!-- required : 필수 
         placeholder : 힌트
         autofocus : 자동 포커스
      -->
      <p>
         아이디 : <input type="text" name="id" required placeholder="아이디를 입력해주세요"
            autofocus value="${memberVO.id}" />
         <!-- 클릭하여 a001, b001, c001이 아닌지 검사하여
            중복되었다면 "아이디가 중복 되었습니다"를 alert하고 
            아이디 입력란을 초기화한 후 autofocus 처리해보자
             -->
         <input id="idck" type="button" value="아이디 중복 검사" />
      </p>
      <p>비밀번호 : <input type="password" name="passwd" required placeholder="알파벳과 숫자의 혼합" /></p>
      <!-- 최대글자수 => maxlength -->      
      <!-- 입력 양식 너비 설정 => size -->
      <p>이름 : <input type="text" name="name" maxlength="7"
         size="7" required /></p>
      <p>
         <!-- 읽기 전용 => 단지 읽기만 함 => readonly -->
         우편번호 : <input type="text" name="postNum" readonly required />
         <!-- 검색 버튼 클릭 => 우편번호 입력란에 332211을 입력해보자 -->
         <input type="button" id="addr">우편번호 검색</input>
      </p>
      <p>
         연락처 : <input type="text" maxlength="4" size="4" name="phone1" required />
         - <input type="text" maxlength="4" size="4" name="phone2" required />
         - <input type="text" maxlength="4" size="4" name="phone3" required />
      </p>
      <!-- checked속성 : 기본값 미리 선택. radio / checkbox -->
      <p>
         성별 : <input type="radio" name="gender" value="남성" 
         <c:if test="${memberVO.gender=='남성'}">checked</c:if>
         />남성
         <input type="radio" name="gender" value="여성"
         <c:if test="${memberVO.gender=='여성'}">checked</c:if>
         />여성
      </p>
      <p>
         취미 : 독서<input type="checkbox" name="hobby1" checked />
         독서<input type="checkbox" name="hobby2" />
         독서<input type="checkbox" name="hobby3" />
      </p>
     <p>
     	<!-- rows : 줄 수 / cols : 열 수 -->
     	<textarea rows="3" cols="30" name="comment"
     	placeholder="가입 인사를 입력해주세요"></textarea>
     </p>
      <p>
         <!-- 폼데이터에 내용이 채워져야지만 활성화 -->
         <!-- 비활성화 -> disabled -->
         <input type="submit" value="가입하기" />
         <input type="reset" value="다시 쓰기" />
      </p>
   </form>


<script type="text/javascript">
CKEDITOR.replace("comment");

	let v_idck = document.getElementById("idck");
	let v_id = document.getElementsByName("id")[0];
	
	let v_addr = document.getElementById("addr");
	let	v_postNum = document.getElementsByName("postNum")[0];

	v_idck.addEventListener("click", function() {
		if(v_id.value=="a001"||v_id.value=="b001"||v_id.value=="c001"){
			alert("아이디가 중복되었습니다.");
			v_id.value=("");
			v_id.focus();
		}else{
			alert("아이디가 없습니다.");
		}
	});
	
	v_addr.addEventListener("click", function(){
		v_postNum.value="332211";
	})
	
	
</script>
</body>




</html>