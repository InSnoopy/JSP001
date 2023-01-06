package kr.or.ddit.filter;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BlindFilter implements Filter{
	
	private Map<String, String> blindMap;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		log.info("{}초기화", this.getClass().getName());
		blindMap = new LinkedHashMap<>();
		blindMap.put("127.0.0.1", "나니까 블라인드");
		blindMap.put("0:0:0:0:0:0:0:1", "나니까 블라인드");
		blindMap.put("192.168.35.37", "나니까 블라인드");
		blindMap.put("192.168.35.37", "나니까 블라인드");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		log.info("blind filter 동작시작");
		// 1. 클라이언트 아이피코드 찾기
		// 2.
		// 3. 블라인드 대상자가 아니면 정상적으로 서비스 이동
		// 4. 블라인드 대상자라면 통과시키지마 블라인드 타입 알려주면서 ~한 이유로 블라인드 처리 됐다고 메세지 출력
		
		// 클라이언트 아이피 찾기
		String ipAddress = request.getRemoteAddr();
		if(blindMap.containsKey(ipAddress)) {
			String reason = blindMap.get(ipAddress);
			String message = String.format("당신은 %s 사유로 블라인드 처리 되었습니다.", reason);
			request.setAttribute("message", message);
			String viewName = "/WEB-INF/views/commons/messageView.jsp";
			request.getRequestDispatcher(viewName).forward(request, response);
		}else {
			chain.doFilter(request, response);
		}
		
		
		log.info("blind filter 동작종료");
		
	}

	@Override
	public void destroy() {
		log.info("{}초기화", this.getClass().getName());
	}

}
