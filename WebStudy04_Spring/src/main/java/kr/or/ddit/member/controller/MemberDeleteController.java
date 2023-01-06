package kr.or.ddit.member.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.member.service.MemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.mvc.annotation.RequestMethod;
import kr.or.ddit.mvc.annotation.resolvers.RequestParam;
import kr.or.ddit.mvc.annotation.stereotype.Controller;
import kr.or.ddit.mvc.annotation.stereotype.RequestMapping;
import kr.or.ddit.mvc.view.InternalResourceViewResolver;
import kr.or.ddit.validate.DeleteGroup;
import kr.or.ddit.validate.ValidationUtils;
import kr.or.ddit.vo.MemberVO;


@Controller
public class MemberDeleteController{

   private MemberService service = new MemberServiceImpl();
   
   @RequestMapping(value="/member/memberDelete.do", method=RequestMethod.POST)
   public String memberDelete(
   			@RequestParam("memPass") String memPass
		   , HttpServletRequest req   
		   , HttpSession session
	   ) {
      MemberVO authMember = (MemberVO)session.getAttribute("authMember");
      String memId = authMember.getMemId();
      
      MemberVO inputDate = new MemberVO();
      inputDate.setMemId(memId);
      inputDate.setMemPass(memPass);
      
      Map<String, List<String>> errors = new LinkedHashMap<>();
      Boolean valid = ValidationUtils.validate(inputDate, errors, DeleteGroup.class);
      
      String viewName = null;
      if(valid) {
         ServiceResult result = service.removeMember(inputDate);
         
         switch (result) {
         case INVALIDPASSWORD:
            session.setAttribute("message", "비번 오류");
            viewName = "redirect://mypage.do";
            break;
         case FAIL:
            session.setAttribute("message", "서버 오류");
            viewName = "redirect://mypage.do";
            break;
         default:
            session.invalidate();
            viewName = "redirect:/";
            break;
         }
      }
      else {
         session.setAttribute("message", "아이디나 비밀번호 누락");
         viewName = "redirect:/mypage.do";
      }
      
      return viewName;
      
   }
}