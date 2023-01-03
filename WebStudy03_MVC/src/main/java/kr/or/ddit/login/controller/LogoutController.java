package kr.or.ddit.login.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.or.ddit.mvc.annotation.RequestMethod;
import kr.or.ddit.mvc.annotation.stereotype.Controller;
import kr.or.ddit.mvc.annotation.stereotype.RequestMapping;

@Controller
public class LogoutController{
	@RequestMapping(value="/login/logout.do", method=RequestMethod.POST)
	public String process(HttpSession session) {
		// session.removeAttribute("authMember"); <- 아래껄로 다 없애준다.
		// 알아서 세션 정보를 싹다 지워주는 함수. 현재 세션은 강제로 만료해준다 (invalidate());
		session.invalidate();
		String viewName = "redirect:/";
		
		return viewName;
	}
}
