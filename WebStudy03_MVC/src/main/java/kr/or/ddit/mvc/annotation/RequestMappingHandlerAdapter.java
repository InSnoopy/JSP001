package kr.or.ddit.mvc.annotation;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.ddit.mvc.annotation.resolvers.HandlerMethodArgumentResolver;
import kr.or.ddit.mvc.annotation.resolvers.ModelAttributeMethodProcessor;
import kr.or.ddit.mvc.annotation.resolvers.RequestParamMethodArgumentResolver;
import kr.or.ddit.mvc.annotation.resolvers.RequestParamMethodArgumentResolver.BadRequestException;
import kr.or.ddit.mvc.annotation.resolvers.RequestPartMethodArgumentResolver;
import lombok.extern.slf4j.Slf4j;
import kr.or.ddit.mvc.annotation.resolvers.ServletRequestMethodArgumentResolver;
import kr.or.ddit.mvc.annotation.resolvers.ServletResponseMethodArgumentResolver;

@Slf4j
public class RequestMappingHandlerAdapter implements HandlerAdapter {

	// request에 파라미터로 들어오는걸 해결하기 위함..
	private List<HandlerMethodArgumentResolver> argumentResolvers;
	{
		argumentResolvers = new ArrayList<>();
		argumentResolvers.add(new ServletRequestMethodArgumentResolver());
		argumentResolvers.add(new ServletResponseMethodArgumentResolver());
		argumentResolvers.add(new RequestParamMethodArgumentResolver());
		argumentResolvers.add(new ModelAttributeMethodProcessor());
		argumentResolvers.add(new RequestPartMethodArgumentResolver());
	}
	
	// argument가 여러개로 들어올 경우 어떤건지 알아내는 메소드
	private HandlerMethodArgumentResolver findArgumentResolver(Parameter param) {
		HandlerMethodArgumentResolver finded = null;
		// argumentResolvers로 아규먼트에 대한 타입을 알려다줬다. 위에서! 위에서 해당되는지 찾는거다.
		for(HandlerMethodArgumentResolver resolver : argumentResolvers) {
			if(resolver.supportsParameter(param)) {
				finded = resolver;
				break;
			}
		}
		return finded;
	}
	
	@Override
	public String invokeHandler(RequestMappingInfo mappingInfo, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// 컨트롤러가 어떤 컨트롤러인지
		Object handlerObject = mappingInfo.getCommandHandler();
		// post방식인지 get방식인지
		Method handlerMethod = mappingInfo.getHandleMethod();
		// 파라미터로 몇개가 들어오는지
		int parameterCount = handlerMethod.getParameterCount();
		try {
			String viewName = null; 
			if(parameterCount>0) {
				// ex) 찾은 컨트롤러의 post방식의 파라미터들..
				Parameter[] parameters = handlerMethod.getParameters();
				Object[] arguments = new Object[parameterCount];
				for(int i=0; i<parameterCount; i++) {
					Parameter param = parameters[i];
					// 파라미터를 꺼내오면 그걸 위에 메소드로 보낸다.
					HandlerMethodArgumentResolver findedResolver = findArgumentResolver(param);
					if(findedResolver==null) {
						throw new RuntimeException(String.format("$s 타입의 메소드 인자는 현재 처리 가능한 resolver가 없음.",param.getType()));
					}else {		
						// 파라미터를 담은 객체에 HandlerMethodArgumentResolver에 있는 resolveArgument를 실행
						arguments[i] = findedResolver.resolveArgument(param, req, resp);
					}
				}
				// get? post/ 어떤 컨트롤러 / 인자값이 어떤지 MemberVO인지 String인지 int인지..
				viewName = (String) handlerMethod.invoke(handlerObject, arguments);
			}else { // 파라미터가 없는 경우				
				viewName = (String) handlerMethod.invoke(handlerObject);
			}
			return viewName;			
		}catch(BadRequestException e) {
			log.error("handler adapter가 handler method argument resolver 사용 중 문제 발생", e);
			resp.sendError(400, e.getMessage());
			return null;
		}catch(Exception e) {
			throw new ServletException(e);
		}
	}



}
