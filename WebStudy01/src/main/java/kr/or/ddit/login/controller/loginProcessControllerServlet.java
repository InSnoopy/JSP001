package kr.or.ddit.login.controller;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.vo.MemberVO;

/**
 * 1. 검증에 통과하지 못했을 경우, 다시 로그인 폼으로 이동함.
 * 2. 인증에 통과하지 못했을 경우, 다시 로그인 폼으로 이동함.
 *	- 비밀번호 오류 상태를 가정하고, 메시지 전달 -> alert 함수로 메시지 출력.
 *	- 이전에 입력받은 아이디의 상태를 유지함.
 * 3. 인증 완료시에 웰컴 페이지로 이동함.
 */
@WebServlet("/login/loginProcess.do")
public class loginProcessControllerServlet extends HttpServlet{

	// 인증
	// MemberVO를 만들어서 파라미터 값을? 대박이다. 원래 String memId, String memPass 이걸로 넘겼을텐데 ㄷㄷ
	private boolean authenticate(MemberVO member) {
		return member.getMemId().equals(member.getMemPass());
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 1. 
		HttpSession session = req.getSession();
		// session이 최초 요청이라면?
		// 너는 최초로 요청할 일이 없을텐데?? 검증해야한다. 에러 메시지를 보내준다.
		if(session.isNew()) {
			resp.sendError(400, "로그인 폼이 없는데 어떻게 로그인을 하지??");
			return;
		}
		String memId = req.getParameter("memId");
		String memPass = req.getParameter("memPass");

		// 파라미터에 들어오는 값(memId)이랑 setMemId와가 너무 비슷하다.
		// BeanUtils (자바빈을 뜻한다.) : 자바빈의 규약을 지킨 것만 이 라이브러리를 사용 가능했다. 중프때 쓴거.
		MemberVO member = new MemberVO();
		member.setMemId(memId);
		member.setMemPass(memPass);
		
		boolean valid = validate(member);
		String viewName = null;
		
		if(valid) {
		// 2.
			if(authenticate(member)) {
				// authMember로 flash가 아닌 계속 유지되게 해야된다.
				// authMember로 약속한거다. 이게 있다면 로그인 한 사람을 뜻한다.
				session.setAttribute("authMember", member);
				// welcome page index.jsp라서..
				// redirect: -> 앞에 이걸 써주면 
				viewName = "redirect:/";				
			}else {				
				session.setAttribute("validId", memId);
				session.setAttribute("message", "비밀번호 오류");
				// 인증 구조에서는 getRequestDispatcher 방식을 사용하지 않는다.
				// viewName = "/login/loginForm.jsp";
				viewName = "redirect:/login/loginForm.jsp";
			}
			
		}else {
			// req가 아닌 session
			// req.setAttribute("message", "아이디나 비밀번호 누락");
			session.setAttribute("message", "아이디나 비밀번호 누락");
			viewName = "redirect:/login/loginForm.jsp";
		}
		
		// 5. view 이동
		// viewName에 앞에 redirect: 가 있다면..
		if(viewName.startsWith("redirect:")) {
			// 앞에 redirect:가 붙은걸 때버리는 작업
			viewName = viewName.substring("redirect:".length());
			resp.sendRedirect(req.getContextPath() + viewName);
		}else {
			req.getRequestDispatcher(viewName).forward(req, resp);
		}
	}
	
	// 검증
	private boolean validate(MemberVO member) {
		boolean valid = true;
//		if(member.getMemId()==null || member.getMemId().isEmpty()) {
//			valid = false;
//		}
		// isBlank(), isEmpty() 차이는?
		// isEmpty()는 " "-> 빈 공간이 아니라고 친다.
		// isBlank()는 " "-> 빈 공간이라고 친다.
		if(StringUtils.isBlank(member.getMemId())) {
			valid = false;
		}
		if(StringUtils.isBlank(member.getMemPass())) {
			valid = false;
		}
		return valid;
	}

}
