package kr.or.ddit.sample.view;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import kr.or.ddit.sample.service.SampleService;

@Component
public class SampleView {
	public static void main(String[] args) {
//		SampleDAO dao = new SampleDAOImpl_Postgre();
//		SampleService service = new SampleServiceImpl(dao);
		ApplicationContext context = 
				// 계층 구조 아니고 2개의 xml를 받아올 수 있다.
				new ClassPathXmlApplicationContext(
						"kr/or/ddit/sample/conf/Parent-Context.xml"
						,"kr/or/ddit/sample/conf/auto/SampleAutoDI-Context.xml"
				);
		SampleService service = context.getBean(SampleService.class);
		StringBuffer model = service.retrieveInformation("PK_2");
		System.out.println(model);
	}
}
