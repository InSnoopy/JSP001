package kr.or.ddit.memo.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.memo.dao.MemoDAO;
import kr.or.ddit.vo.MemoVO;

@Service
public class MemoService{
	
	private MemoDAO dao; 
	
	// 꼭 생성자를 호출해야 한다면 ?~ 여기다가 해라
	@Inject
	public MemoService(MemoDAO dao) {
		super();
		this.dao = dao;
	}


	public List<MemoVO> retrieveMemoList(){
		return dao.selectMemoList();
	}
}












