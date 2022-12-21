<%@page import="kr.or.ddit.vo.MemoVO"%>
<%@page import="java.util.Arrays"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>10/elDesc.jsp</title>
</head>
<body>
<h4>EL(Expression Language)</h4>
<pre>
	: 표현(속성 데이터 출력)을 목적으로 하는 스크립트 형태의 언어 
	-> 제어문의 형태가 없다. 이걸 보안한게 커스텀태그와 함께 사용하면 가능하다. (JSTL?)
	<%
		String data = "데이터";
		pageContext.setAttribute("attr-Data", data);
	%>
	1. 속성( EL 변수 ) 접근 방법 : EL변수는 Scope안에 저장된 데이터
		<%=data %>, 
		${data }, -> 안나온다. 속성이 아니라 변수이기 때문에.. 
		1번 방법 : ${attr-Data } -> 이렇게 속성을 넣어서 출력할 수 있다. EL은 모델2 구조에서 많이 사용된다.
		- EL은 총 4가지의 다이렉터로 scope에 접근할 객체가 있다.
		pageScope, requestScope, sessionScope, applicationScope
			2번 방법 : ${pageScope.attr-Data } -> map에서 키로 접근
			3번 방법 : ${pageScope['attr-Data'] } -> 연산배열로 접근
		
		1번 방법 : 다 뒤져야하기 때문에 속도 면에서 느리다.
		2번 방법 : 
		3번 방법 : attr-Data이렇게 중간에 -가 들어갈 경우 3번 방법만 출력된다. (안전하다)
		
	2. 연산자 종류 
		산술연산자 : +-*/%
			: (숫자로만 산술한다.) 연산자 중심 
			: 속성이 없는 경우 0, 문자는 숫자로 자동으로 파싱, "a"이런 경우 숫자로 파싱하지 못하기 때문에 error뜬다.
			${3+4 }, ${"3"+4 }, ${"3"+"4" }, ${attr+4 }, \${"a"+4 }
			${4/3 }, ${attr*data }
		논리연산자 : &&(and, short-curit), ||(or, short-curkit), !(not)
			: short-curit -> 한쪽만으로 true나 false가 나온 경우 다른 한편에 보지도 않는다.
			: boolean방식 또한 파싱한다.
			: 없다면 null이 아니라 " "화이트 스페이스가 쓰인다.
			${true and true }, ${"true" and true }, ${true or "false" }, ${false or attr }, ${attr }, ${not attr }
		비교연산자 : &gt;(gt), &lt;(lt), &gt;=(ge), &lt;=(le), ==(eq), !=(ne)
			${3 lt 4 }, ${4 ge 3 }, ${4 eq 3 }, ${4 ne 3 }
		단항연산자 : empty (비어있는지), -> jstl에 cif에 많이 사용된다.
			확인 순서 : exists -> null -> length, size check -> 이렇게해서도 비어있는지 확인
			<%
				pageContext.setAttribute("attr", "   "); 
// 				pageContext.setAttribute("listAttr", Arrays.asList()); // asList()는 가변.. 타입이 같으면 여러개를 넣을 수 있다. 
				pageContext.setAttribute("listAttr", Arrays.asList("a","b")); 
			%>
			   : 없으면 true, 속성이 있으면 안에 null인지 확인하고 true, 그 다음에 ""안에 아무것도 없는지 확인하고 true
			   : String type이면 length로 체크해서 1이상이면 비어이지 않다고 판단한다.
			${empty attr }
			list attr : ${not empty listAttr }
			   : ++ (+=), -- (-=) : 이런게 지원이 안된다. =이 없기 때문에. 하지만 EL버전에 따라서 다르다. 요즘 버전은 사용 가능하다고 한다.
			   : 할당연산자가 지원이 되는 버전이 되어야지 증감연산자를 사용 가능하다.
		삼항연산자 : 조건식 ? 참표현 : 거짓표현
			${not empty attr ? "attr 비어있음" : attr } 
			${listAttr }, ${not empty listAttr ? listAttr : "기본값" }
	3. (속성)객체에 대한 접근법
		<%
			MemoVO memo = new MemoVO();
			pageContext.setAttribute("memoAttr", memo);
			memo.setWriter("작성자");
		%>
		${memoAttr }, ${memoAttr.writer }, ${memoAttr['writer'] }
	4. (속성)집합 객체에 대한 접근법 : <a href="elCollection.jsp">elCollection.jsp</a>
	5. EL 기본객체 : <a href="elObject.jsp">elObject.jsp</a>
	
</pre>
</body>
</html>