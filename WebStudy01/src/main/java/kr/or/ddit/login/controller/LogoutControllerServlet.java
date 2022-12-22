package kr.or.ddit.login.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login/logout.do")
public class LogoutControllerServlet extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		// session.removeAttribute("authMember"); <- 아래껄로 다 없애준다.
		// 알아서 세션 정보를 싹다 지워주는 함수. 현재 세션은 강제로 만료해준다 (invalidate());
		session.invalidate();

		String viewName = "redirect:/";
		if(viewName.startsWith("redirect:")) {
			// 앞에 redirect:가 붙은걸 때버리는 작업
			viewName = viewName.substring("redirect:".length());
			resp.sendRedirect(req.getContextPath() + viewName);
		}else {
			req.getRequestDispatcher(viewName).forward(req, resp);
		}
	}
}
