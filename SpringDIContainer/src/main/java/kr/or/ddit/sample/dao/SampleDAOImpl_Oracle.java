package kr.or.ddit.sample.dao;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SampleDAOImpl_Oracle implements SampleDAO {
	
	public void init() {
		log.info("{} 객체 초기화", getClass().getSimpleName());
	}
	
	// destroy1로 해도되고 pojo형식으로 아무렇게 이름 지어도 된다.
	public void destroy() {
		log.info("{} 객체 소멸", getClass().getSimpleName());
	}
	
   private Map<String, String> dummyDB;
   
   public SampleDAOImpl_Oracle(Map<String, String> dummyDB) {
      super();
      log.info("{} 객체 생성, 생성자 주입으로 dummyDB 객체 주입.",getClass().getSimpleName());
      this.dummyDB = dummyDB;
//    dummyDB = new HashMap<>();
//    int idx = 0;
//    dummyDB.put("PK_"+ ++idx, "Oracle 레코드 "+idx);
//    dummyDB.put("PK_"+ ++idx, "Oracle 레코드 "+idx);
//    dummyDB.put("PK_"+ ++idx, "Oracle 레코드 "+idx);
   }
   

   @Override
   public String selectRawData(String primaryKey) {
      return dummyDB.get(primaryKey);
   }
}