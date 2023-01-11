package kr.or.ddit.auth;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.MemberVOWrapper;

public class GeneratePrincipalFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession(false);
		MemberVO authMember = null;
		if(session!=null) {
			authMember = (MemberVO) session.getAttribute("authMember");			
		}
		if(authMember!=null) {
//			MemberVOWrapper wrapper = new MemberVOWrapper(authMember);
			// 요청을 적절하게 변경해준다. (필터)
			// req의 요청을 변경하지 못한다. 이럴 떄 wrapper를 만들어서 내 입맛대로 조절한다.
			// Wrapper는 기본 생성자를 생성하지 못한다.
			// 익명객체 생성해서 하는 방법도 있다.
//			new HttpServletRequestWrapper(req) {
			// 1번 필터에서 원본 데이터가 온다면 그 이후에는 wrapper로 변질된 데이터로 넘어간다.
			// getUserPrincipal을 쓰기 위해 req를 변질시킴
			HttpServletRequest modifiedReq = new HttpServletRequestWrapper(req) {
				@Override
				public Principal getUserPrincipal() {
					HttpServletRequest adaptee = (HttpServletRequest) getRequest();
					HttpSession session = adaptee.getSession(false); // 혹시 session이 없다면 만들지마!
					if(session!=null) {						
						MemberVO realMember = (MemberVO) session.getAttribute("authMember");
						return new MemberVOWrapper(realMember);
					}else {
						return super.getUserPrincipal();
					}
				}
			};
			// 변질된 req를 보낸다.
			chain.doFilter(modifiedReq, response);
		}else {			
			chain.doFilter(request, response);
		}
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
