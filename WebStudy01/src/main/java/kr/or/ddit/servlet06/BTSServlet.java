package kr.or.ddit.servlet06;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value="/bts", loadOnStartup=1) // loadOnStartUp=1은 이 서블릿부터 먼저 생성하기 위함
public class BTSServlet extends HttpServlet{
	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		ServletContext application = config.getServletContext(); // 톰캣이 가지고있는 정보를 가져올 수 있다.
		application.setAttribute("btsMembers", getBtsMemberList()); // 어플리케이션 전체에서 사용 가능
	}
	
	public Map<String, String[]> getBtsMemberList() {
		Map<String, String[]> members = new LinkedHashMap<>();
		members.put("B001", new String[] {"RM","/WEB-INF/views/bts/rm.jsp"});
		members.put("B002", new String[] {"뷔","/WEB-INF/views/bts/bui.jsp"});
		return members;
	}
	
	public String[] getMemberContent(String code) {
		 String[] content = getBtsMemberList().get(code);
		 return content;
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 1) view화면에 bts리스트를 다 출력하는 view 만들기 (동적)
		
		// 1. 요청 분석
		String accept = req.getHeader("Accept");
		
		// 2. 모델 확보
		Map<String, String[]> bts = getBtsMemberList();
		
		bts.forEach((k,v) -> System.out.println("key : " + k + ", value : " + v));
		
		// 3. 모델 공유
		req.setAttribute("bts", bts);
		
		// 3. 뷰 선택		
		String viewName = null;
		if(accept.contains("json")) {
			viewName = "/jsonView.do";
		}else {
			viewName = "/08/btsForm.jsp";
		}
		// 4. 이동
		req.getRequestDispatcher(viewName).forward(req, resp);		

		
	}
}
