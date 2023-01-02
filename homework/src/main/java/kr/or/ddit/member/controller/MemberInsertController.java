package kr.or.ddit.member.controller;

import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.member.service.MemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.mvc.annotation.RequestMethod;
import kr.or.ddit.mvc.view.AbstractController;
import kr.or.ddit.mvc.view.InternalResourceViewResolver;
import kr.or.ddit.validate.InsertGroup;
import kr.or.ddit.validate.ValidationUtils;
import kr.or.ddit.vo.MemberVO;
/**
 *	Backend controller(command handler) --> POJO (Plain Old Java Object)
 */
public class MemberInsertController implements AbstractController{
	private static final Logger log = LoggerFactory.getLogger(MemberInsertController.class);
	private MemberService service = new MemberServiceImpl();
	
	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String method = req.getMethod();
		RequestMethod requestMethod = RequestMethod.valueOf(method.toUpperCase());
		String viewName = null;
		if(requestMethod==RequestMethod.GET) {
			viewName = memberForm(req, resp);
		}else if(requestMethod==RequestMethod.POST) {
			viewName = memberInsert(req, resp);
		}else {
			resp.sendError(405, method+"는 지원하지 않음.");
		}
		return viewName;
	}
	
	public String memberForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		return "member/memberForm"; // logical view name
	}
	
	public String memberInsert(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		// command object - 검증 대상
		MemberVO member = new MemberVO();
		req.setAttribute("member", member);
		
//		meber.setMemId(req.getParameter("memId"));
		Map<String, String[]> parameterMap = req.getParameterMap();
		try {
			BeanUtils.populate(member, parameterMap);
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new ServletException(e);
		}
		// 검증
		Map<String, List<String>> errors = new LinkedHashMap<>();
		req.setAttribute("errors", errors);
		
		boolean valid = ValidationUtils.validate(member, errors, InsertGroup.class);
		
		String viewName = null;
		
		if(!valid) {			
			viewName = "/WEB-INF/views/member/memberForm.jsp";
		}else {
			ServiceResult result = service.createMember(member);
			
			switch (result) {
			case PKDUPLICATED:
				req.setAttribute("message", "아이디 중복");
				viewName = "/WEB-INF/views/member/memberForm.jsp";
				break;
			case FAIL:
				req.setAttribute("message", "서버에 문제 있음. 이따 다시 하세요");
				viewName = "/WEB-INF/views/member/memberForm.jsp";
				break;
			default:
				viewName = "redirect:/";
				break;
			}
		}

		return viewName;
	}


	
}
