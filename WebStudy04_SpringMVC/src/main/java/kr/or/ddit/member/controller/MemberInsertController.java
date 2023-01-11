package kr.or.ddit.member.controller;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.member.service.MemberService;
import kr.or.ddit.validate.InsertGroup;
import kr.or.ddit.vo.MemberVO;
import lombok.extern.slf4j.Slf4j;
/**
 *	Backend controller(command handler) --> POJO (Plain Old Java Object)
 */
@Slf4j
@Controller
@RequestMapping("/member/memberInsert.do")
public class MemberInsertController{
	
	@Inject
	private MemberService service;
	
	// get, post.. 모든 요청보다 이게 먼저 실행된다.
	// 여기있는 MemberVO는 계속 재활용 된다. (장점)
	@ModelAttribute("member")
	public MemberVO member() {
		log.info("@ModelAttribute 메소드 실행 -> member 속성 생성");
		return new MemberVO();
	}
	
	@GetMapping
	public String memberForm() {
		return "member/memberForm";
	}
	
	@PostMapping
	public String memberInsert(
		HttpServletRequest req
		, @Validated(InsertGroup.class) @ModelAttribute("member") MemberVO member // InsertGroup.class 이런거 넣으려면
		, Errors errors
		
	) throws ServletException, IOException {	

		boolean valid = !errors.hasErrors();
		
		String viewName = null;
		
		if(!valid) {			
			viewName = "/member/memberForm";
		}else {
			ServiceResult result = service.createMember(member);
			
			switch (result) {
			case PKDUPLICATED:
				req.setAttribute("message", "아이디 중복");
				viewName = "/member/memberForm";
				break;
			case FAIL:
				req.setAttribute("message", "서버에 문제 있음. 이따 다시 하세요");
				viewName = "/member/memberForm";
				break;
			default:
				viewName = "redirect:/";
				break;
			}
		}

		return viewName;
	}

}
