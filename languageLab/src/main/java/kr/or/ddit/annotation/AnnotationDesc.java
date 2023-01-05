package kr.or.ddit.annotation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import kr.or.ddit.ReflectionUtils;

/**
 * annotation ? (개발자와 시스템에게 일정 정보를 제공하는 주석의 한 형태.
 *  comment
 *	주석과 annotation의 차이
 *	이 주석의 대상은 사람이다.
 *	annotation은 대상은 사람과 시스템이다.
 *	marker 인터페이스?
 *	marker annotation : @Override 속성이 없는 어노테이션
 *	single value annotation : @WebServlet("/member/memberInsert.do")
 *	multi value annotation : @WebServlet(value="/member/memberInsert.do", loadOnStartup=1)
 *
 *	custom annotation
 *	1. @interface 키워드로 선언된 클래스 정의
 *	2. RetentionPolicy를 통해 어노테이션의 사용 범위 설정.
 *	3. Target 으로 어노테이션 적용
 */
public class AnnotationDesc {
	// comment : 실제 런타임에는 존재하지 않는다.
	// @Override : 어노테이션이 컴파일러에게 정보를 제공해준다.
	// @WebServlet : 톰캣에세 정보를 제공해준다.
	public static void main(String[] args) {
		String basePackages = "kr.or.ddit";
		List<Class<?>> classList = ReflectionUtils.getAllClassesAtBasePackages(basePackages);
//		classList.stream().forEach(System.out::println);
		Map<Class<?>, Controller>classMap = ReflectionUtils.getClassesWithAnnotationAtBasePackages(Controller.class, basePackages);
		classMap.forEach((handlerClass,anno)->{
			try {	
				Object handlerObject = handlerClass.newInstance();
				System.out.printf("=====>%s : %s\n", handlerClass.getName(), anno);
//				ReflectionUtils.getMethodsAtClass(clz, String.class).stream().forEach(System.out::println);
				Map<Method, RequestMapping> methodMap = ReflectionUtils.getMethodsWithAnnotationAtClass(handlerClass, RequestMapping.class, String.class);
				methodMap.forEach((handlerMethod, requestMapping)->{
					String url = requestMapping.value();
					String method = requestMapping.method();
					try {
						String logicalViewName = (String)handlerMethod.invoke(handlerObject);
						System.out.printf("url : %s, method : %s 요청 매핑 핸들러 : %s\n", url, method, handlerMethod);
						System.out.printf("핸들러 메소드에서 결정된 논리적인 뷰 네임 : %s\n", logicalViewName);
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						
					}
				});
			}catch(Exception e) {
				
			}
		 
		});
		
	}
}
