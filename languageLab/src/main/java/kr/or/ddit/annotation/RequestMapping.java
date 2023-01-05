package kr.or.ddit.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RequestMapping {
	String value(); // 속성
	String method() default "get"; 
	// single value annotation
	// multi value annotation
	// 두개다 사용 가능;
	// 만약에 value도 default ""; 라면 marker annotation로도 가능
	// 기본값이 설정된 속성
}
