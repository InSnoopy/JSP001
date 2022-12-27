package kr.or.ddit.member.controller;

import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.member.service.MemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.vo.MemberVO;

@WebServlet("/member/memberInsert.do")
public class MemberInsertControllerServlet extends HttpServlet{
	private MemberService service = new MemberServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		4.
		String viewName = "/WEB-INF/views/member/memberForm.jsp";
//		5.
		if(viewName.startsWith("redirect:")) {
			viewName = viewName.substring("redirect:".length());
			resp.sendRedirect(req.getContextPath() + viewName);
		}else {
			req.getRequestDispatcher(viewName).forward(req, resp);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		1.
		req.setCharacterEncoding("UTF-8");
		MemberVO member = new MemberVO();
		req.setAttribute("member", member);
		
//		meber.setMemId(req.getParameter("memId"));
		Map<String, String[]> parameterMap = req.getParameterMap();
		try {
			BeanUtils.populate(member, parameterMap);
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new ServletException(e);
		}

		String viewName = null;

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
//		5.
		if(viewName.startsWith("redirect:")) {
			viewName = viewName.substring("redirect:".length());
			resp.sendRedirect(req.getContextPath() + viewName);
		}else {
			req.getRequestDispatcher(viewName).forward(req, resp);
		}
	}
	
	private MemberVO getMemberFormRequest(HttpServletRequest req) {
		
		MemberVO member = new MemberVO();
		
		String memId = req.getParameter("memId");
		String memPass = req.getParameter("memPass");
		String memName = req.getParameter("memName");
		String memRegno1 = req.getParameter("memRegno1");
		String memRegno2 = req.getParameter("memRegno2");
		String memBir = req.getParameter("memBir");
		String memZip = req.getParameter("memZip");
		String memAdd1 = req.getParameter("memAdd1");
		String memAdd2 = req.getParameter("memAdd2");
		String memHometel = req.getParameter("memHometel");
		String memComtel = req.getParameter("memComtel");
		String memHp = req.getParameter("memHp");
		String memMail = req.getParameter("memMail");
		String memJob = req.getParameter("memJob");
		String memLike = req.getParameter("memLike");
		String memMemorial = req.getParameter("memMemorial");
		String memMemorialday = req.getParameter("memMemorialday");
		int memMileage = Integer.parseInt(req.getParameter("memMileage")); 
		String memDelete = req.getParameter("memDelete");
		
		member.setMemId(memId);
		member.setMemPass(memPass);
		member.setMemName(memName);
		member.setMemRegno1(memRegno1);
		member.setMemRegno2(memRegno2);
		member.setMemBir(memBir);
		member.setMemZip(memZip);
		member.setMemAdd1(memAdd1);
		member.setMemAdd2(memAdd2);
		member.setMemHometel(memHometel);
		member.setMemComtel(memComtel);
		member.setMemHp(memHp);
		member.setMemMail(memMail);
		member.setMemJob(memJob);
		member.setMemLike(memLike);
		member.setMemMemorial(memMemorial);
		member.setMemMemorialday(memMemorialday);
		member.setMemMileage(memMileage);
		member.setMemDelete(memDelete);
		
		return member;
	}
}
