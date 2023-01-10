package kr.or.ddit.di;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VariousDITestView {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = 
				new ClassPathXmlApplicationContext("kr/or/ddit/di/conf/VariousDI-Context.xml");
		context.registerShutdownHook();
		
		VariousDIVO vo1 = context.getBean("vo1", VariousDIVO.class);
		VariousDIVO vo2 = context.getBean("vo2", VariousDIVO.class);
		log.info("주입된 객체 : {}", vo1);
		log.info("주입된 객체 : {}", vo2);
	}
}
