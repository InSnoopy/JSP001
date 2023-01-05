package kr.or.ddit.member.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import kr.or.ddit.mvc.multipart.MultipartHttpServletRequest;
import kr.or.ddit.validate.InsertGroup;
import kr.or.ddit.validate.ValidationUtils;
import kr.or.ddit.vo.MemberVO;
/**
 *	Backend controller(command handler) --> POJO (Plain Old Java Object)
 */
@Controller
public class MemberInsertController{
	private MemberService service = new MemberServiceImpl();
	
	@RequestMapping("/member/memberInsert.do")
	public String memberForm() {
		return "member/memberForm"; // logical view name
	}
	@RequestMapping(value="/member/memberInsert.do", method=RequestMethod.POST)
	public String memberInsert(
		HttpServletRequest req
		, @ModelAttribute("member") MemberVO member
		, @RequestPart(value="memImage", required=false) MultipartFile memImage
	) throws ServletException, IOException {
	
		// 위에 @RequestPart 이걸 추가해서 밑에 한줄로 된다.
//		if(req instanceof MultipartHttpServletRequest) {
//			MultipartFile memImage = ((MultipartHttpServletRequest) req).getFile("memImage");
			member.setMemImage(memImage);
//		}
		
		Map<String, List<String>> errors = new LinkedHashMap<>();
		req.setAttribute("errors", errors);
		
		boolean valid = ValidationUtils.validate(member, errors, InsertGroup.class);
		
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
