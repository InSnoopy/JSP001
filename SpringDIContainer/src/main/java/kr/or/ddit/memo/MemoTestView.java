package kr.or.ddit.memo;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import kr.or.ddit.memo.conf.MemoContextConfiguration;
import kr.or.ddit.memo.service.MemoService;

@Controller
public class MemoTestView {
	private MemoService service;
	
	@Required
	@Inject
	public void setService(MemoService service) {
		this.service = service;
	}
	public void printMemoList() {
		service.retrieveMemoList().forEach(System.out::println);
	}
	
	public static void main(String[] args) {
// 상속 관계
//		// 상위
//		ConfigurableApplicationContext context =
//				new GenericXmlApplicationContext("classpath:kr/or/ddit/di/conf/auto/root-context.xml");
//		context.registerShutdownHook();
//		
//		// 하위
//		ConfigurableApplicationContext childContext =
//				new ClassPathXmlApplicationContext(
//					new String[] {"kr/or/ddit/di/conf/auto/servlet-context.xml"}	
//					, context
//				);
//		context.registerShutdownHook();
//		childContext.registerShutdownHook();
		
		// 이제 xml이 아니기 때문에!
		ConfigurableApplicationContext context = 
				new AnnotationConfigApplicationContext(MemoContextConfiguration.class);
		context.registerShutdownHook();
		
		MemoTestView view = context.getBean(MemoTestView.class);
		view.printMemoList();
//		
	}
}














