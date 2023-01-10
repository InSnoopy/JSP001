package kr.or.ddit.sample.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository("postgreDAO")
public class SampleDAOImpl_Postgre implements SampleDAO {
	public void init1() {
		log.info("{} 객체 초기화", getClass().getSimpleName());
	}
	
	public void destroy2() {
		log.info("{} 객체 소멸", getClass().getSimpleName());
	}
	
	private Map<String, String> dummyDB;
	
	public SampleDAOImpl_Postgre() {
		super();
		log.info("{} 객체 생성", getClass().getSimpleName());
//		dummyDB = new HashMap<>();
//		int idx = 0;
//		dummyDB.put("PK_"+ ++idx, "PostgreSQL 레코드 "+idx);
//		dummyDB.put("PK_"+ ++idx, "PostgreSQL 레코드 "+idx);
//		dummyDB.put("PK_"+ ++idx, "PostgreSQL 레코드 "+idx);
	}
	
	public void setDummyDB(Map<String, String> dummyDB) {
		this.dummyDB = dummyDB;
		log.info("dummyDB 를 setter 주입함.");
	}

	@Override
	public String selectRawData(String primaryKey) {
		return dummyDB.get(primaryKey);
	}

}

















