package kr.or.ddit.servlet06;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value="/bts/*", loadOnStartup=2)
public class BTSMemberServlet extends HttpServlet{
	// 어플리케이션 전체를 통틀어서 1개
	private ServletContext application;
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		application = config.getServletContext();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String requestURI = req.getRequestURI();
		String code = Optional.of(requestURI)
								.map(uri->uri.substring(req.getContextPath().length()))
								.map(uri->uri.substring("/bts/".length()))
								.get();
		//scope 공유를 사용하는 이유는 다른 전역변수를 가져올 수 없다.
		//BTSServlet이 먼저 실행되어야 한다. 서블릿 생성 순서
		Map<String, String[]> members = (Map) application.getAttribute("btsMembers");
		String[] contents = members.get(code);
		if(contents==null) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		String contentPage = contents[1];
		req.setAttribute("contentPage", contentPage);
		req.getRequestDispatcher("/WEB-INF/views/bts/btsLayout.jsp").forward(req,resp);
	}
}
