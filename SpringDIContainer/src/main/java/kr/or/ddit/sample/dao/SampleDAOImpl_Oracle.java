package kr.or.ddit.sample.dao;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository("daoOracle") 
public class SampleDAOImpl_Oracle implements SampleDAO {
	
	public void init1() {
		log.info("{} 객체 초기화", getClass().getSimpleName());
	}
	
	public void destroy2() {
		log.info("{} 객체 소멸", getClass().getSimpleName());
	}
	
	private Map<String, String> dummyDB;
	
	// Parent-Context에 있는 Map name을 가져옴
	// Required를 붙이면 이 set는 반드시 필요하다라고 설정! setter의 단점을 보완 : 꼭 필요한 주입이다 라고 제한을 걸어줌
	@Required // 예외 발생했을 떄 알려주기 위함.. 예외를 더 자세히 알려준다.
	@Resource(name="oracleDB")
	public void setDummyDB(Map<String, String> dummyDB) {
		this.dummyDB = dummyDB;
		log.info("{} 객체 생성, setter 주입으로 dummyDB 객체 주입.", getClass().getSimpleName());
	}

	@Override
	public String selectRawData(String primaryKey) {
		return dummyDB.get(primaryKey);
	}

}

















