package kr.or.ddit.sample.dao;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SampleDAOImpl_Postgre implements SampleDAO {
   
	public void init() {
		log.info("{} 객체 초기화", getClass().getSimpleName());
	}
	
	// destroy1로 해도되고 pojo형식으로 아무렇게 이름 지어도 된다.
	public void destroy() {
		log.info("{} 객체 소멸", getClass().getSimpleName());
	}
   
	private Map<String, String> dummyDB;
   
   public SampleDAOImpl_Postgre() {
      super();
//    log.info("{} 객체 생성",getClass().getSimpleName());
//    dummyDB = new HashMap<>();
//    int idx = 0;
//    dummyDB.put("PK_"+ ++idx, "PostgreSQL 레코드 "+idx);
//    dummyDB.put("PK_"+ ++idx, "PostgreSQL 레코드 "+idx);
//    dummyDB.put("PK_"+ ++idx, "PostgreSQL 레코드 "+idx);
   }

   public void setDummyDB(Map<String, String> dummyDB) {
	   this.dummyDB = dummyDB;
	   log.info("dummyDB를 setter 주입함.");
   }
   
   @Override
   public String selectRawData(String primaryKey) {
      return dummyDB.get(primaryKey);
   }

}