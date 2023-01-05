package kr.or.ddit.mvc.annotation.resolvers;

import java.io.IOException;
import java.lang.reflect.Parameter;
import java.security.Principal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**	
 *	{@link HttpServletRequest}, {@link HttpSession}, {@link Principal} 타입의 핸들러 메소드 인자 해결.
 */
public class ServletRequestMethodArgumentResolver implements HandlerMethodArgumentResolver {

	// 현재 들어오는게 request, session타입만 받는다.
	// 만들어내는 객체의 타입을 결정하고
	@Override
	public boolean supportsParameter(Parameter parameter) {
		Class<?> parameterType = parameter.getType();
		boolean support = HttpServletRequest.class.equals(parameterType)
							||
						  HttpSession.class.equals(parameterType)
						  	||
						  Principal.class.isAssignableFrom(parameterType);
		return support;
	}

	// 위에 파라미터로 받아서 지원이 가능하다면 밑에 메소드로 실행한다.
	// 여기서 객체의 타입을 만든다.
	// 어차피 대부분 req안에 다 들어가있다.
	@Override
	public Object resolveArgument(Parameter parameter, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Class<?> parameterType = parameter.getType();
		Object argumentObject = "";
		if(HttpServletRequest.class.equals(parameterType)) {
			argumentObject = req;
		}else if (HttpSession.class.equals(parameterType)) {
			argumentObject = req.getSession();
		}else {
			argumentObject = req.getUserPrincipal();
		}
		return argumentObject;
	}

}
