package kr.or.ddit.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.member.service.MemberService;
import kr.or.ddit.validate.DeleteGroup;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.MemberVOWrapper;
import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class MemberDeleteController{

   private final MemberService service;
   
	@ModelAttribute("member")
	public MemberVO member() {
		return new MemberVO();
	}
   
   @PostMapping
   public String memberDelete(
   			@RequestParam(value="memPass", required=true) String memPass
   		   , @SessionAttribute(value="authMember", required=true) MemberVO authMember 
		   , HttpServletRequest req   
		   , HttpSession session
		   , RedirectAttributes redirectAttributes
	   ) {
	  
      String memId = authMember.getMemId();
      
      MemberVO inputDate = new MemberVO();
      inputDate.setMemId(memId);
      inputDate.setMemPass(memPass);
      
      String viewName = null;
  
     ServiceResult result = service.removeMember(inputDate);
     
     switch (result) {
     case INVALIDPASSWORD:
    	 // session에다가 안넣고 이렇게 설정하는것도 가능
    	 redirectAttributes.addFlashAttribute("message", "비번 오류"); // 따로 view에서 삭제할 필요 없다.
        viewName = "redirect://mypage.do";
        break;
     case FAIL:
    	 redirectAttributes.addFlashAttribute("message", "서버 오류");
        viewName = "redirect://mypage.do";
        break;
     default:
    	 session.invalidate();
        viewName = "redirect:/";
        break;
     }
            
      return viewName;
      
   }
}