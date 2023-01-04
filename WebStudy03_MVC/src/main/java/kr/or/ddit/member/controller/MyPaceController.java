package kr.or.ddit.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import kr.or.ddit.member.service.MemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.mvc.annotation.stereotype.Controller;
import kr.or.ddit.mvc.annotation.stereotype.RequestMapping;
import kr.or.ddit.vo.MemberVO;

@Controller
public class MyPaceController{
	private MemberService service = new MemberServiceImpl();
	
	@RequestMapping("/mypage.do")
	public String myPageView(
			HttpSession session
			, HttpServletRequest req){
		
		MemberVO authMember = (MemberVO) session.getAttribute("authMember");
		MemberVO member = service.retriveMember(authMember.getMemId());
		
		req.setAttribute("member", member);
		
		return "member/memberView";
	}
}
