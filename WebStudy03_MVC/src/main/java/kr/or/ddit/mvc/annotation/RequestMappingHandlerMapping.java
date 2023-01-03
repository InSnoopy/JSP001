package kr.or.ddit.mvc.annotation;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.ddit.mvc.annotation.stereotype.Controller;
import kr.or.ddit.mvc.annotation.stereotype.RequestMapping;
import kr.or.ddit.mvc.utils.ReflectionUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RequestMappingHandlerMapping implements HandlerMapping {
	private Map<RequestMappingCondition, RequestMappingInfo> handlerMap;
	
	public RequestMappingHandlerMapping(String... basePackages) {
		handlerMap = new LinkedHashMap<>();
		scanBasePackages(basePackages);
	}
	
	private void scanBasePackages(String[] basePackages) {
		// Controller라는 어노테이션이 붙어있는 class를 찾는다.
		ReflectionUtils.getClassesWithAnnotationAtBasePackages(Controller.class, basePackages).
			forEach((handlerClass, controller)->{
				try {					
					// 해당되는 class를 찾았으면 하나의 인스턴스를 생성해준다.
					Object commandHandler = handlerClass.newInstance();
					// 이제는 RequestMapping이라는 어노테이션이 들어간 메소드를 찾는다.
					ReflectionUtils.getMethodsWithAnnotationAtClass(
							handlerClass, RequestMapping.class, String.class
							// 밑에 request, response 제약
//							, HttpServletRequest.class, HttpServletResponse.class
						).forEach((handlerMethod, requestMapping)->{
							// key - url, method
							RequestMappingCondition mappingCondition = 
									new RequestMappingCondition(requestMapping.value(), requestMapping.method());
							// value
							RequestMappingInfo mappingInfo = 
									new RequestMappingInfo(mappingCondition, commandHandler, handlerMethod);
							handlerMap.put(mappingCondition, mappingInfo);
							log.info("수집된 핸들러 정보 : {}", mappingInfo);
						});
				}catch (Exception e) {
					log.error("핸들러 클래스 스캔 중 문제 발생", e);
				}
			});		
	}

	@Override
	public RequestMappingInfo findCommandHandler(HttpServletRequest request) {
		//request를 RequestMappingCondition로 객체화
		String url = request.getServletPath(); // 디스팩쳐가 하던일을 여기서 한다.
		RequestMethod method = RequestMethod.valueOf(request.getMethod().toUpperCase());
		RequestMappingCondition mappingCondition = new RequestMappingCondition(url, method);
		// null인 경우 에러코드 404가 나가야한다.
		return handlerMap.get(mappingCondition); // 위에있는Map에 키의 값을 넣으면 value로 꺼낼 수 있기에..
	}

}
