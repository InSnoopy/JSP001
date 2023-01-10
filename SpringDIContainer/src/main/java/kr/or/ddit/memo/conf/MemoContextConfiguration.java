package kr.or.ddit.memo.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

import kr.or.ddit.memo.MemoTestView;
import kr.or.ddit.memo.dao.FileSystemMemoDAOImpl;
import kr.or.ddit.memo.dao.MemoDAO;
import kr.or.ddit.memo.service.MemoService;

// 여기 안에 등록된 Bean은 다 등록된다.
@Lazy // 이 안에 등록된 모든 Bean들을 대상으로 설정하기
@ComponentScan("kr.or.ddit.memo")
// 자바는 어노테이션 컴피그는 사용할 필요가 없다.
public class MemoContextConfiguration {
//	@Bean
//	@Scope("prototype") // 기본값이 싱글톤인데 이런식으로 변경 가능
//	public MemoDAO memoDAO() {
//		return new FileSystemMemoDAOImpl();
//	}
//	
//	// 의존 관계를 이런식으로 설정하기도 한다.
//	// 알아서 주입해준다.
//	// 스프링이 빈을 관리할때 싱글톤으로 관리한다.
//	@Bean
//	public MemoService generateService(MemoDAO dao) {
//		return new MemoService(dao);
//	}
//	
//	@Bean("testView") // 기본 id,name은 메서드 명이지만 다른 명으로 변경하고 싶으면 이렇게 사용한다.
//	public MemoTestView testView(MemoService service) {
//		MemoTestView view = new MemoTestView();
//		view.setService(service);
//		return view;
//	}
}
