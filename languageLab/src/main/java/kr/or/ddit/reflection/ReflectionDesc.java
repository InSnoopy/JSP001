package kr.or.ddit.reflection;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.ObjectInputStream.GetField;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import kr.or.ddit.reflect.ReflectionTest;
import kr.or.ddit.vo.MemberVO;

/**
 * Reflection (java.lang.reflect)
 * 	: 객체의 타입, 객체의 속성(상태, 행동) 들을 역으로 추적하는 작업.
 */
public class ReflectionDesc {
	public static void main(String[] args) {
		// 객체의 타입을 모른다.
		// 상태 정보를 거꾸로 꺼낸다? 아래 기술이 리플렉션이라는 기술이다.
		Object dataObj = ReflectionTest.getObject();
		System.out.println(dataObj);
		Class<?> objType = dataObj.getClass();
		System.out.println(objType.getName());
		
		// 전역 변수를 다 불러와준다.
		Field[] fields = objType.getDeclaredFields();
//		Arrays.stream(fields).forEach(System.out::println);
		
		// get,set 정보 가져오기
		Method[] methods = objType.getDeclaredMethods();
//		Arrays.stream(methods).forEach(System.out::println);
		
		// 객체 생성 = 기본생성자 사용 )
		// IllegalAccessException 에러는 생성자가 private일 떄 외부에서 못불러오는 경우 이런 에러가 나온다.
		try {
			Object newObj = objType.newInstance();
			Arrays.stream(fields).forEach(fld->{
//				fld.setAccessible(true); // 강제로 private을 풀어서 값을 가져온다. 캡슐화가 유지가 안되기 때문에 get,set으로 불러오는 방법 사용
				String fldName = fld.getName(); // mem_id ,getMem_id, setMem_id
				// 전역 변수 한개를 가지고있는 객체 (pd)
				try {
					PropertyDescriptor pd = new PropertyDescriptor(fldName, objType);
	                Method getter = pd.getReadMethod(); // getter
	                Method setter = pd.getWriteMethod(); // setter
					// getter
					// dataObj 값 하나
//					Object fldValue = fld.get(dataObj);
					Object fldValue = getter.invoke(dataObj); // 강제로 private를 풀지 않아도 된다.
					// setter
//					fld.set(newObj, fldValue);
					setter.invoke(newObj, fldValue);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IntrospectionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
//			System.out.println(newObj);                                                                                                                 
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
		// 생성되어 있는 타입을 결정해서 모방하고 있는 객체를 생성하겠다.
		// member가 정확히 어떤 타입인지 알아야한다.
//		MemberVO member = new MemberVO();
//		member.getMem_name();
	}
}
