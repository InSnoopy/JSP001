package kr.or.ddit.mvc.annotation;

import java.lang.reflect.Method;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
// value이기 때문에 eqluas가 필요 없다.
public class RequestMappingInfo {
	private RequestMappingCondition mappingCondition;
	private Object commandHandler; // 컨트롤러의 구현체들이 들어 있다.
	private Method handleMethod; // 메소드 정보
	
	public RequestMappingInfo(RequestMappingCondition mappingCondition, Object commandHandler, Method handleMethod) {
		super();
		this.mappingCondition = mappingCondition;
		this.commandHandler = commandHandler;
		this.handleMethod = handleMethod;
	}
}
