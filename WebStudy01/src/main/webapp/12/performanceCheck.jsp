<%@page import="java.sql.SQLException"%>
<%@page import="java.util.ArrayList"%>
<%@page import="kr.or.ddit.vo.MemoVO"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="kr.or.ddit.db.ConnectionFactory"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h4>성능 고려사항</h4>
<pre>
	응답 소요 시간 : latency time(99.999%) + processing time(00.001%)
	case 1 - 30ms : 모든걸 1번 (커넥션 + 쿼리)
	case 2 - 1140ms : 모든걸 100번 (커넥션 + 쿼리)
	case 3 - 42ms : Connection을 빼고 (쿼리)실행만100번
	case 4 - 32ms : (DBCP에 폴링을 하고나서) 모든걸 100번 (커넥션 + 쿼리)
	2,4번은 폴링을 쓰고 안쓰고의 차이다. -
	
	풀링이란?
	풀링 예시) : 하루에 판매할 수 있는 양(칼국수)을 결정해서
			: 국수 면을 만드는 시간이 오래 걸려서 미리 만들어 놓는다.
			: 면(100인분) -> 담아서 관리해야 한다. 손님이오면 (1인분)씩 준다.
			: 담을 수 있는 그릇이 필요하다. 이 그릇을 (Pool)이라고 한다.
			
			커넥션 풀링)
			1. Pool을 만들어서 관리
			2. Pool을 관리할 알바생 필요
			3. Pool일정 커넥션을 만들어서 미리 넣어 놓는다.
			
			* 폴링은 객체를 미리 만들어놓고 재활용도 가능하다는 뜻
			 
	
	<%
		long startTime = System.currentTimeMillis();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT  code, writer, content, \"DATE\"  ");
		sql.append(" FROM   tbl_memo                        ");
		for(int i=1; i<=100; i++){
			try (
				Connection conn = ConnectionFactory.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			) {
				
// 		for(int i=1; i<=100; i++){
		
			ResultSet rs = pstmt.executeQuery();
			List<MemoVO> memoList = new ArrayList<>();
			while (rs.next()) {
				MemoVO memo = new MemoVO();
				memoList.add(memo);
				memo.setCode(rs.getInt("CODE"));
				memo.setWriter(rs.getString("WRITER"));
				memo.setContent(rs.getString("CONTENT"));
				memo.setDate(rs.getString("DATE"));
			}
// 		}
				
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}			
		}
		long endTime = System.currentTimeMillis();
	
	%>
	소요시간 : <%=endTime-startTime %>ms
</pre>
</body>
</html>