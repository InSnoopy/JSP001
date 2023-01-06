package kr.or.ddit.member.controller;

import javax.servlet.http.HttpServletRequest;

import kr.or.ddit.member.service.MemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.mvc.annotation.stereotype.Controller;
import kr.or.ddit.mvc.annotation.stereotype.RequestMapping;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.MemberVOWrapper;

@Controller
public class MyPaceController{
	private MemberService service = new MemberServiceImpl();
	
	@RequestMapping("/mypage.do")
	public String myPageView(
//			HttpSession session
			HttpServletRequest req,
			MemberVOWrapper principal){
//		MemberVOWrapper principal = (MemberVOWrapper) req.getUserPrincipal();
//		MemberVO authMember = (MemberVO) session.getAttribute("authMember");
		MemberVO authMember = principal.getRealMember();
		MemberVO member = service.retriveMember(authMember.getMemId());
		
		req.setAttribute("member", member);
		
		return "member/memberView";
	}
}
