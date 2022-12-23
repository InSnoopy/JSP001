package kr.or.ddit.servlet09.service;

import java.util.List;

import kr.or.ddit.servlet09.dao.DataBasePropertyDAO;
import kr.or.ddit.servlet09.dao.DataBasePropertyDAOImpl;
import kr.or.ddit.vo.DataBasePropertyVO;

public class DataBasePropertyServiceImpl implements DataBasePropertyService{
	// 의존 관계 형성
	// 구현체가 없어도 서비스가 가능하다. 결합력이 낮다. 이게 좋다.
	// 의존 관계는 DAO의 인터페이스에 연결되어 있다.
	private DataBasePropertyDAO dao = new DataBasePropertyDAOImpl();
	
	@Override
	public List<DataBasePropertyVO> retrievePropertyList(String PropertyName) {
		List<DataBasePropertyVO> list = dao.selectPropertyList(PropertyName);
		list.stream() // 엘리먼트를 하나하나씩 건들어준다.
			.forEach(System.out::println); 
			// 메소드 레퍼런스 구조 (함수의 파라미터로 어떤 메서드를 쓰겠다고만 넘겨준다.)
			// 함수형 프로그래밍에 대표적
			// vo 파라미터를 하나로 받고  vo-> System.out.println(vo) <- 이코드를 위에 한줄로 쓸 수 있다.
		return list;
	}
	
}
