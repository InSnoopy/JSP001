package kr.or.ddit.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.member.service.MemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.vo.MemberVO;

@WebServlet("/member/memberDelete.do")
public class MemberDeleteControllerServlet extends HttpServlet{
	private MemberService service = new MemberServiceImpl();
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		1. 요청 분석
		req.setCharacterEncoding("UTF-8");
		
		String memPass = req.getParameter("memPass");
		MemberVO member = new MemberVO();	
		
		HttpSession session = req.getSession();
		MemberVO authMember = (MemberVO) session.getAttribute("authMember");
		MemberVO member2 = service.retriveMember(authMember.getMemId());
		req.setAttribute("member", member2);
		System.out.println("insoo: "+member2.getMemId());
		
		member.setMemId(member2.getMemId());
		member.setMemPass(memPass);
		
		System.out.println(member.getMemId());
		System.out.println(member.getMemPass());
		
		String viewName = null;
		
		ServiceResult result = service.removeMember(member);
		switch (result) {
		case NOTEXIST:
			req.setAttribute("message", "존재하지 않는 회원");
			viewName = "/WEB-INF/views/member/memberForm.jsp";
			break;
		case INVALIDPASSWORD:
			req.setAttribute("message", "비버 인증 실패");
			viewName = "/WEB-INF/views/member/memberForm.jsp";
			break;
		case FAIL:
			req.setAttribute("message", "서버에 문제 있음. 이따 다시 하세요");
			viewName = "/WEB-INF/views/member/memberForm.jsp";
			break;
		default:
			viewName = "redirect:/";
			session.removeAttribute("authMember");
			break;
		}
		
//		5.
		if(viewName.startsWith("redirect:")) {
			viewName = viewName.substring("redirect:".length());
			resp.sendRedirect(req.getContextPath() + viewName);
		}else {
			req.getRequestDispatcher(viewName).forward(req, resp);
		}
	}
}
