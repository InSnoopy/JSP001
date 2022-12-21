<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>10/jstlDesc.jsp</title>
</head>
<body>
<h4>JSTL(Jsp Standard Tag Libarary)</h4>
<pre>
	: 커스텀 태그들 중에서 많이 활용될수 있는 것들을 모아놓은 라이브러리.
	&lt;prefix:tagname attr_name="attr_value" &gt;
	1. 커스텀 태그 로딩 : taglib directive
	2. prefix 를 통한 태그 접근
	
	** core 태그 종류
	1. EL 변수(속성)와 관련된 태그 : set, remove
		scope="page" : 기본값
		<c:set var="sample" value="샘플값" scope="session"></c:set> 
		${sample }, ${sessionScope['sample'] }
		<c:remove var="sample" scope="session"/> : remove를 사용할 경우 범위를 확실히 적어 놓자. 아니면 모든 'sample'이 지워진다.
		--> ${sample }
	2. 조건문 : if(조건식){블럭문}else{}, switch~case(조건값)...~default
		단일조건문 : if
			<c:if test="${empty param['name1'] }">
				파라미터 없음.
			</c:if>
			<c:if test="${not empty param['name1'] }">
				파라미터 있음.
			</c:if>
		다중조건문 : choose ~ when ~ otherwise
			<c:choose>
				<c:when test="${empty param['name1'] }">
					파라미터 없음.
				</c:when>
				<c:otherwise>
					파라미터 있음.
				</c:otherwise>
			</c:choose>
	3. 반복문 : foreach, forTokens, for(선언절, 조건문, 증감절) for(임시 블럭 변수 : 반복대상 집합객체)
		<c:set var="array" value='<%=new String[]{"value1", "value2"} %>'/>
		<c:forEach var="i" begin="0" end="${fn:length(array)-1}" step="1" varStatus="vs">
			${array[i] } --> 현재 반복의 상태(LoopTagStatus) : ${vs.index}], ${vs.count }, ${vs.first }, ${vs.last } 
		</c:forEach>
		<c:forEach items="${array }" var="element">
			${element }
		</c:forEach>
		
		Token는 구분자를 통해서 하나의 토큰을 구분한다. 최소 단위
			int num = 3;
			intnum=3;
			selectmem_idfrommember; 
			select mem_id from member;
			아버지가 방에 들어가신다.
			아버지 가방에 들어가신다.
			어떻게 Token화 되는거에 따라서 뜻이 달라진다. 
		<c:forTokens items="아버지 가방에 들어가신다." delims=" " var="token">
			${token }
		</c:forTokens>
		<c:forTokens items="1,2,3,4" delims="," var="token">
			${token * 10 }
		</c:forTokens>
		
	4. 기타
		url 재작성 : url(client side path, session parameter), redirect
			<c:url value="/06/memoView.jsp"></c:url>
			- 클라이언트에 절대주소를 작성해준다. request.ContextPath()를 자동으로 넣어준다.
			- 세션 트래킹모드에 쿠키가 없더라도 세션이 유지되도록 세션 파라미터를 넘겨준다.
			/WebStudy01/06/memoView.jsp;jsessionid=8917069F45FE82E657173367E2EFA678
			<a href="<c:url value='/10/jstlDesc.jsp'/>">세션 유지</a>
			
<%-- 			<c:redirect url="/06/memoView.jsp"></c:redirect> --%>
			- redirect도 아래처럼 request.getContextPath()를 적지 않아도 된다. 
			<%--
				String location = request.getContextPath() + "/06/memoView.jsp";
				response.sendRedirect(location);
			--%>
			
		표현구조 : out -> 태그를 자동으로 이스케이프 시켜준다.
			
			<c:out value="<h4>출력값</h4>" escapeXml="true"/> 
			escapeXml값의 기본값은 true
			escapeXml값을 false로 하면 태그가 적용된다.
			
</pre>
- 크로일링 1단계
- 프록시리터
<%-- <jsp:include page=""></jsp:include> -> 같은 어플리케이션 안에 있을경우에만 사용 가능하다. 그래서 page --%>
<c:import url="https://www.naver.com" var="naver"/> -> 위에랑 똑같은 동적 래퍼지만 컨텍스트에 제한이 없다.
-> var로만 사용할 경우 출력되지 않고 page에 저장만 되어있다.
<c:out value="${naver }" escapeXml="true" />

</body>
</html>