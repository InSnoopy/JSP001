package kr.or.ddit.mvc.annotation.resolvers;

import java.io.IOException;
import java.lang.reflect.Parameter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.ctc.wstx.util.StringUtil;

/**
 * @RequestParam 을 가지고 있으며, 기본형 타입인 인자를 해결.
 *
 */
public class RequestParamMethodArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(Parameter parameter) {
		Class<?> parameterType = parameter.getType();
		RequestParam requestParam = parameter.getAnnotation(RequestParam.class);
		boolean support = requestParam != null
					&&
				(		// 기본형 타입인지 아닌지 확인해주는 메소드
						parameterType.isPrimitive()
						||
						// 기본형이거나 String타입인 경우
						String.class.equals(parameterType)
						||
						// 배열안에 하나하나씩 꺼내는 메소드는 getComponentType
						// 배열안에 있는 요소가 기본타입이거나 String타입인 경우
						(
								parameterType.isArray() 
								&& 
								(
									parameterType.getComponentType().isPrimitive() 
									|| 
									parameterType.getComponentType().equals(String.class)
								)
						)
				);
		return support;
	}

	@Override
	public Object resolveArgument(Parameter parameter, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Class<?> parameterType = parameter.getType();
		RequestParam requestParam = parameter.getAnnotation(RequestParam.class);
		
		String requestParameterName = requestParam.value();
		boolean required = requestParam.required();
		String defaultValue = requestParam.defaultValue();
		
		String[] requestParameterValues = req.getParameterValues(requestParameterName);
		if(required && ( requestParameterValues==null 
						|| requestParameterValues.length==0
						|| StringUtils.isBlank(requestParameterValues[0])
				)) {
			throw new BadRequestException(requestParameterName + " 이름의 필수 파라미터 누락");
		}
		if(requestParameterValues==null || requestParameterValues.length==0 || StringUtils.isBlank(requestParameterValues[0])) {
			requestParameterValues = new String[] {defaultValue};
		}
		
		Object argumentObject = null;
		if(parameterType.isArray()) {
			Object[] argumentObjects = new Object[requestParameterValues.length];
			for(int i=0; i<argumentObjects.length; i++) {
				argumentObjects[i] = 
						singleValueGenerate(parameterType.getComponentType(), requestParameterValues[i]);
			}
			argumentObject = argumentObjects;
		}else {
			argumentObject = singleValueGenerate(parameterType, requestParameterValues[0]);
		}			
		return argumentObject;
	}
	
	private Object singleValueGenerate(Class<?> singleValueType, String requestParameter) {
		Object singleValue = null;
		// 원래 byte, shot .. 6가지 이상 해야하지만 시간 상 3개만 했다.
		if(int.class.equals(singleValueType)) {
			singleValue = Integer.parseInt(requestParameter);
		}else if(boolean.class.equals(singleValueType)) {
			singleValue = Boolean.parseBoolean(requestParameter);
		}else {
			singleValue = requestParameter;
		}
		return singleValue;
	}

	public static class BadRequestException extends RuntimeException{
		public BadRequestException(String message) {
			super(message);
		}
	}
}
