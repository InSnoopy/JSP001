package kr.or.ddit.board.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import kr.or.ddit.board.dao.BoardDAO;
import kr.or.ddit.board.vo.AttatchVO;
import kr.or.ddit.board.vo.BoardVO;
import kr.or.ddit.vo.PagingVO;

@Service
public class BoardServiceImpl implements BoardService{

	@Inject
	private BoardDAO boardDAO; // 진짜 객체가 아니다 프록시가 만들어줌

//	private WebApplicationContext context;
	
	@Override
	public int createBoard(BoardVO board) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void retrieveBoardList(PagingVO<BoardVO> pagingVO) {
		pagingVO.setTotalRecord(boardDAO.selectTotalRecord(pagingVO));
		pagingVO.setDataList(boardDAO.selectBoardList(pagingVO));
	}

	@Override
	public BoardVO retrieveBoard(int boNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int modifyBoard(BoardVO board) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int removeBoard(int boNo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public AttatchVO retrieveForDownload(int attNo) {
		// TODO Auto-generated method stub
		return null;
	}

}
