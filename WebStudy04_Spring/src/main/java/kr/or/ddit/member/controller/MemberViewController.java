package kr.or.ddit.member.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import kr.or.ddit.member.service.MemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.mvc.annotation.resolvers.RequestParam;
import kr.or.ddit.mvc.annotation.stereotype.Controller;
import kr.or.ddit.mvc.annotation.stereotype.RequestMapping;
import kr.or.ddit.vo.MemberVO;

@Controller
public class MemberViewController{
	private MemberService service = new MemberServiceImpl();
	
	@RequestMapping("/member/memberView.do")
	// RequestParam은 who라는 parameter를 찾아서 memId에 넣어준다.
	// required=true라고 필수 파라미터라고 정하고 안넘어오면 에러를 알아서 발생시켜준다. resp도 필요 없어진다.
	public String process(
		@RequestParam(value="who", required=true) String memId
		,HttpServletRequest req
	) throws IOException{
//		1. 요청 분석 (검증)
//		String memId = req.getParameter("who");
//		if(StringUtils.isBlank(memId)) {
//			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
//			return null;
//		}
//		2. 모델 확보
		MemberVO member = service.retriveMember(memId);
//		3. 모델 공유
		req.setAttribute("member", member);
//		4. 뷰 선택
		String viewName = "/member/memberView";

		return viewName;
	}
}
