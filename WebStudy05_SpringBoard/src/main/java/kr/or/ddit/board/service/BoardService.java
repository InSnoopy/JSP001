package kr.or.ddit.board.service;

import kr.or.ddit.board.vo.AttatchVO;
import kr.or.ddit.board.vo.BoardVO;
import kr.or.ddit.vo.PagingVO;


public interface BoardService {
	/**
	 * 게시글 작성
	 * @param board
	 * @return 
	 */
	public int createBoard(BoardVO board); // enum이 없는 경우 확실하게 int의 값을 주석처리 해줘야한다.
	public void retrieveBoardList(PagingVO<BoardVO> pagingVO);
	
	/**
	 * 게시글 검색
	 * @param boNo
	 * @return 존재여부(NotExistBoardException)
	 */
	public BoardVO retrieveBoard(int boNo); // 첨부파일도 가져와야하기 때문에 테이블 조인이 필요
	
	/**
	 * 게시글 수정
	 * @param board
	 * @return 존재여부, 인증성공여부(AuthenticationException), rowcnt
	 */
	public int modifyBoard(BoardVO board); // 비밀번호 틀린 경우 enum으로 안가져오는 경우 예외로 처리하는 방법도 있다.
	
	/**
	 * 게시글 삭제
	 * @param boNo
	 * @return 존재여부, 인증성공여부(AuthenticationException), rowcnt
	 */
	public int removeBoard(int boNo);

	public AttatchVO retrieveForDownload(int attNo);
}
