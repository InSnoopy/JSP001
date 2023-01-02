package kr.or.ddit.login.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.or.ddit.mvc.view.AbstractController;

public class LogoutController implements AbstractController{
	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		// session.removeAttribute("authMember"); <- 아래껄로 다 없애준다.
		// 알아서 세션 정보를 싹다 지워주는 함수. 현재 세션은 강제로 만료해준다 (invalidate());
		session.invalidate();
		String viewName = "redirect:/";
		
		return viewName;
	}
}
