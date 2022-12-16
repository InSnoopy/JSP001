package kr.or.ddit.memo.dao;

import java.util.List;

import kr.or.ddit.vo.MemoVO;

public interface MemoDAO {
	public List<MemoVO> selectMemoList();
	public int insertMemo(MemoVO memo);
	public int updateMemo(MemoVO memo); // 기본키 값 필요
	public int deleteMemo(int code);
}
