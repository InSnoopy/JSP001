package kr.or.ddit.board.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.board.vo.BoardVO;
import kr.or.ddit.vo.PagingVO;

@Mapper
public interface BoardDAO {
	public int insertBoard(BoardVO board);
	
	/**
	 * 게시판 목록 조회
	 * @param pagingVO TODO
	 * @return size == 0 인 경우, 조건에 맞는 레코드 없음. 
	 */
	public List<BoardVO> selectBoardList(PagingVO<BoardVO> pagingVO);
	
	/**
	 * 검색 조건에 맞는 레코드 수 반환
	 * @param pagingVO
	 * @return
	 */
	public int selectTotalRecord(PagingVO<BoardVO> pagingVO);
	
	public BoardVO selectBoard(int boNo);
	public int updateBoard(BoardVO board);
	public int deleteBoard(int boNo);
}
