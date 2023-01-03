package kr.or.ddit.mvc.annotation.resolvers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
/**
 *	핸들러 메소드의 인자 하나를 Command Object로 받을 때 사용. (MemberVO), (ProdVO)
 */
public @interface ModelAttribute {
	String value() default "";
}
