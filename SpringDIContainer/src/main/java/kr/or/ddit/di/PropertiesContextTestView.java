package kr.or.ddit.di;

import java.util.Properties;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PropertiesContextTestView {
	public static void main(String[] args) {
		System.getProperties().forEach((k,v)->{
			System.out.printf("%s : %s\n", k, v);
		});
		System.getenv().forEach((k,v)->{			
			System.err.printf("%s : %s\n", k, v);
		});


		ConfigurableApplicationContext context =
				new GenericXmlApplicationContext("kr/or/ddit/di/conf/Properties-Context.xml");
		context.registerShutdownHook();
		Properties dbInfo = context.getBean("dbInfo", Properties.class);
		DBInfoVO dbInfo1 = context.getBean("dbInfo1", DBInfoVO.class);
		DBInfoVO dbInfo2 = context.getBean("dbInfo2", DBInfoVO.class);
		log.info(" 주입된 객체 : {}", dbInfo1);
		log.info(" 주입된 객체 : {}", dbInfo2);
		log.info(" 주입된 객체 : {}", dbInfo);
	}
}
