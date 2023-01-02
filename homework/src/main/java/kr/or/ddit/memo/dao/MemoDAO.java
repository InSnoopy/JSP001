package kr.or.ddit.memo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.or.ddit.vo.MemoVO;

public interface MemoDAO {
	public List<MemoVO> selectMemoList();
	public int insertMemo(MemoVO memo);
	public int updateMemo(MemoVO memo); // 기본키 값 필요
	public int deleteMemo(@Param("code") int code); // Memo.xml에서 이름을 정해놓을 경우 Param이용(어노테이션)
}
