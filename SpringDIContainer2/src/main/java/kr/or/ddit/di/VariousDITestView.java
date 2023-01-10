package kr.or.ddit.di;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VariousDITestView {
	public static void main(String[] args) {
		// 컨테이너 객체 생성
		// 필요 없으면 자동 소멸
		// 라이프 사이클 콜백을 가지고 있어야 한다.
		// 초기화, 소멸이 되었다는 log가 있어야 한다.
		// 등록한 빈을 메인메소드에서 주입을 받아
		// 그 빈의 프로퍼티의 상태를 출력한다.
		
		// 컨테이너 객체 생성
		ConfigurableApplicationContext context = 
				new GenericXmlApplicationContext("/kr/or/ddit/di/conf/VariousDI-Context.xml");
		
		// 필요 없으면 자동 소멸
		context.registerShutdownHook();
		VariousDIVO vo1 = context.getBean("vo1", VariousDIVO.class);
		VariousDIVO vo2 = context.getBean("vo2", VariousDIVO.class);
		log.info("주입된 객체 : {}", vo1);
		log.info("주입된 객체 : {}", vo2);
	}
}
