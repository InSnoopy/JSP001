package kr.or.ddit.member.controller;

import java.io.IOException;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.ddit.member.service.MemberService;
import kr.or.ddit.vo.MemberVO;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class MemberViewController{
	@Inject
	private final MemberService service;
	
//	void 리턴 타입으로 logical view name이 생략된 경우,
//	HandlerAdapter는 RequestToViewNameTranslator를 이용해 view를 검색함.
	@RequestMapping("/member/memberView.do")
	public void process(
		@RequestParam(value="who", required=true) String memId
		,Model model
	) throws IOException{

		MemberVO member = service.retriveMember(memId);

		model.addAttribute("member", member);

//		String viewName = "/member/memberView";
//
//		return viewName;
	}
}
