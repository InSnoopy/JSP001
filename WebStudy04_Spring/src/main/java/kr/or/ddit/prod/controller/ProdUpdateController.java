package kr.or.ddit.prod.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.mvc.annotation.RequestMethod;
import kr.or.ddit.mvc.annotation.resolvers.ModelAttribute;
import kr.or.ddit.mvc.annotation.resolvers.RequestParam;
import kr.or.ddit.mvc.annotation.stereotype.Controller;
import kr.or.ddit.mvc.annotation.stereotype.RequestMapping;
import kr.or.ddit.prod.service.ProdService;
import kr.or.ddit.prod.service.ProdServiceImpl;
import kr.or.ddit.validate.UpdateGroup;
import kr.or.ddit.validate.ValidationUtils;
import kr.or.ddit.vo.ProdVO;

@Controller
public class ProdUpdateController {
	private ProdService service = new ProdServiceImpl();
	
	@RequestMapping("/prod/prodUpdate.do")
	public String updateForm(
//			@SessionAttribute("authMember") MemberVO authMember, -> 이 어노테이션을 만들어보자
			@RequestParam("prodId") String prodId
			) {
		ProdVO prod = service.retriveProd(prodId);
		
		return null;
	}
	
	@RequestMapping(value="/prod/prodUpdate.do", method=RequestMethod.POST)
	public String updateProcess(
			@ModelAttribute("prod") ProdVO prod,
			HttpServletRequest req
			) {
		
		Map<String, List<String>> errors = new LinkedHashMap<>();
		req.setAttribute("errors", errors);
				
		String viewName = null;
		
		boolean valid = ValidationUtils.validate(prod, errors, UpdateGroup.class);
				
		if(!valid) {			
			viewName = "member/memberForm";
		}else {			
			ServiceResult result = service.modifyProd(prod);
			switch(result) {
			case FAIL:
				req.setAttribute("message", "서버 오류, 좀따 다시.");
				viewName = "member/memberForm";
				break;
			default:
				viewName = "redirect:/mypage.do";
				break;
			}
		}
		
		return viewName;
	}
	
}
