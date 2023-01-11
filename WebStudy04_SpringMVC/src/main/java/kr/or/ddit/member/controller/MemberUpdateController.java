package kr.or.ddit.member.controller;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.member.service.MemberService;
import kr.or.ddit.validate.UpdateGroup;
import kr.or.ddit.vo.MemberVO;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member/memberUpdate.do")
public class MemberUpdateController{
	private final MemberService service;
	
	@GetMapping
	public String updateForm(
			Model model
			, @SessionAttribute("authMember") MemberVO authMember // 세션은 파라미터 안에 넣어 주지 않는다.
			){
		MemberVO member = service.retriveMember(authMember.getMemId());
		model.addAttribute("member", member);
		return "member/memberForm"; 
	}

	@PostMapping
	public String updateProcess(
			// 현재 공유되는 속성중에 member라고 있으면 가져온다.
			// 없다면 새로운 객체를 만들고 데이터를 바인딩해서 넣어준다.
			@Validated(UpdateGroup.class) @ModelAttribute("member") MemberVO member,
//			Errors errors, // Validated의 결과를 담는다.
			BindingResult errors, // 이 친구도 errors다. 이걸 더 많이 쓴다. 이름이 명확하기 때문에
			Model model,
			HttpSession session // 세션에 값을 넣는다면 필요하다. 
			) throws IOException{
		
		String viewName = null;
				
		if(errors.hasErrors()) {			
			viewName = "member/memberForm";
		}else {			
			ServiceResult result = service.modifyMember(member);
			switch(result) {
			case INVALIDPASSWORD:
				model.addAttribute("message", "비밀번호 오류");
				viewName = "member/memberForm";
				break;
			case FAIL:
				model.addAttribute("message", "서버 오류, 좀따 다시.");
				viewName = "member/memberForm";
				break;
			default:
				// 수정이 완료되면 session자체를 바로 변경해준다.
				MemberVO modifiedMember = service.retriveMember(member.getMemId());
				session.setAttribute("authMember", modifiedMember);
				viewName = "redirect:/mypage.do";
				break;
			}
		}
		
		return viewName;
	}
}
