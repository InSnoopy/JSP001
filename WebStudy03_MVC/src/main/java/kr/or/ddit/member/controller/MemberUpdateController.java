package kr.or.ddit.member.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.member.service.MemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.mvc.annotation.RequestMethod;
import kr.or.ddit.mvc.annotation.resolvers.ModelAttribute;
import kr.or.ddit.mvc.annotation.resolvers.RequestPart;
import kr.or.ddit.mvc.annotation.stereotype.Controller;
import kr.or.ddit.mvc.annotation.stereotype.RequestMapping;
import kr.or.ddit.mvc.multipart.MultipartFile;
import kr.or.ddit.mvc.view.InternalResourceViewResolver;
import kr.or.ddit.validate.InsertGroup;
import kr.or.ddit.validate.UpdateGroup;
import kr.or.ddit.validate.ValidationUtils;
import kr.or.ddit.vo.MemberVO;

@Controller
public class MemberUpdateController{
	private MemberService service = new MemberServiceImpl();
	
	@RequestMapping(value="/member/memberUpdate.do", method=RequestMethod.GET)
	public String updateForm(
			HttpSession session,
//			@SessionAttribute("authMember") MemberVO authMember, -> 이 어노테이션을 만들어보자
			HttpServletRequest req){
		
		MemberVO authMember = (MemberVO) session.getAttribute("authMember");
		MemberVO member = service.retriveMember(authMember.getMemId());
		req.setAttribute("member", member);

		return "member/memberForm"; // logical view name
	}

	@RequestMapping(value="/member/memberUpdate.do", method=RequestMethod.POST)
	public String updateProcess(
			@ModelAttribute("member") MemberVO member,
			HttpServletRequest req, 
			HttpServletResponse resp,
			@RequestPart(value="memImage", required=false) MultipartFile memImage,
			HttpSession session) throws IOException{
		
		member.setMemImage(memImage);

		String viewName = null;
		
		// 검증
		Map<String, List<String>> errors = new LinkedHashMap<>();
		req.setAttribute("errors", errors);
				
		boolean valid = ValidationUtils.validate(member, errors, UpdateGroup.class);
				
		if(!valid) {			
			viewName = "member/memberForm";
		}else {			
			ServiceResult result = service.modifyMember(member);
			switch(result) {
			case INVALIDPASSWORD:
				req.setAttribute("message", "비밀번호 오류");
				viewName = "member/memberForm";
				break;
			case FAIL:
				req.setAttribute("message", "서버 오류, 좀따 다시.");
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
