package kr.or.ddit.sample.service;

import java.util.Calendar;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.Resource;

import kr.or.ddit.sample.dao.SampleDAO;
import kr.or.ddit.sample.dao.SampleDAOFactory;
import kr.or.ddit.sample.dao.SampleDAOImpl_Oracle;
import kr.or.ddit.sample.dao.SampleDAOImpl_Postgre;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SampleServiceImpl implements SampleService, ApplicationContextAware {
	private ConfigurableApplicationContext context;
	// Spring에서는 어떤 스타일이여도 Resource로 가지고 놀 수 있다.
	private Resource log4j2xml;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = (ConfigurableApplicationContext) applicationContext;
		log.info("ApplicationContextAware 구조로 컨테니어 주입됨.");
	}
	
	public void init() {
		log.info("{} 객체 초기화", getClass().getSimpleName());
		log4j2xml = context.getResource("classpath:log4j2.xml");
		log.info("{} 리소스 로딩 완료", log4j2xml);
	}
	
	// destroy1로 해도되고 pojo형식으로 아무렇게 이름 지어도 된다.
	public void destroy() {
		log.info("{} 객체 소멸", getClass().getSimpleName());
	}
   // case1 : 의존객체를 new 키워드로 직접 생성(결합력 최상)
//   private SampleDAO dao = new SampleDAOImpl_Oracle();
//   private SampleDAO dao = new SampleDAOImpl_Postgre();
   
   // case2 : Factory Object[Method] Pattern
//   private SampleDAO dao = SampleDAOFactory.getSampleDAO();
   
   // case3 : Strategy Pattern , 전략 주입자가 필요함.
   private SampleDAO dao;   
   public SampleServiceImpl(SampleDAO dao) {
	   super();
	   this.dao = dao;
	   log.info("{} 객체 생성 및 전략 객체 ({}) 주입", getClass().getSimpleName(), dao.getClass().getSimpleName());
   }
   
   // case4 : DI Container
   
   public SampleServiceImpl() {
      super();
      log.info("기본 생성자로 {} 객체 생성", getClass().getSimpleName());
   }
   
	public void setDao(SampleDAO dao) {
		this.dao = dao;
		log.info("{} 객체 생성 후 setter 를 통해 {} 주입받음"
				, getClass().getSimpleName()
				, dao.getClass().getSimpleName());
	}

   @Override
   public StringBuffer retrieveInformation(String pk) {
      String data = dao.selectRawData(pk);
      StringBuffer infomation = new StringBuffer();
      infomation.append(String.format("%tc에 조회된 데이터를 가공함 - %s", Calendar.getInstance(), data));
      return infomation;
   }

}