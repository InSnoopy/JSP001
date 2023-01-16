package kr.or.ddit.board.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.ddit.board.vo.BoardVO;
import kr.or.ddit.vo.PagingVO;

@Mapper
public interface BoardDAO {
	/**
	 * 게시판 작성
	 * @param board
	 * @return 등록된 레코드 수 (rowcnt) > 0 : 성공, <= 0 : 실패 
	 */
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
	
	/**
	 * 게시판 조회
	 * @param boNo
	 * @return BoardVO
	 */
	public BoardVO selectBoard(@Param("boNo") int boNo);
	
	/**
	 * 게시판 조회 증가
	 * @param boNo
	 */
	public void incrementHit(int boNo);
	
	public int updateBoard(BoardVO board);
	public int deleteBoard(int boNo);
}
