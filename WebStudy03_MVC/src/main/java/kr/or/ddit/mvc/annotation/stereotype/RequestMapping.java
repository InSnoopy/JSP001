package kr.or.ddit.mvc.annotation.stereotype;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import kr.or.ddit.mvc.annotation.RequestMethod;

/**
 * single value(GET handler), multi value
 * commandler handler 내의 핸들러 메소드에
 * 어떤 요청(주소, 메소드)에 대해 동작하는지를 표현. 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RequestMapping {
	String value(); // 속성
	RequestMethod method() default RequestMethod.GET; 
	// single value annotation
	// multi value annotation
	// 두개다 사용 가능;
	// 만약에 value도 default ""; 라면 marker annotation로도 가능
	// 기본값이 설정된 속성
}
