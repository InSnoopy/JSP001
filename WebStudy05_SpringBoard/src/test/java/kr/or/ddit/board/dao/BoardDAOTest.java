package kr.or.ddit.board.dao;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import kr.or.ddit.board.vo.BoardVO;
import kr.or.ddit.vo.PagingVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class) // 제이유닛 생성
@ContextConfiguration("file:webapp/WEB-INF/spring/*-context.xml") // 가상 컨테이션 생성 (상위 컨테이너 생성)
@WebAppConfiguration // 웹용 컨테이너로 생성하기 위함
public class BoardDAOTest {
	
	@Inject
	private BoardDAO boardDAO;
	private PagingVO<BoardVO> pagingVO;
	private BoardVO board;

	@Before // text 전에 실행
	public void setUp() {
		pagingVO = new PagingVO<>();
		pagingVO.setCurrentPage(1);

//		board.setBoNo(442);
//		board.setBoTitle("첫글");
//		board.setBoWriter("박인수");
//		board.setBoIp("123");
//		board.setBoMail("insoo");
//		board.setBoPass("0000");
//		board.setBoContent("하이");
//		board.setBoDate("20220301");
//		board.setBoHit(3);
	}

	@Test
	public void testInsertBoard() {
		board = new BoardVO();
		board.setBoTitle("첫글");
		board.setBoWriter("박인수");
		board.setBoIp("123");
		board.setBoMail("insoo");
		board.setBoPass("0000");
		board.setBoContent("hi");
		
//		log.info("board : {}", board);
		int rowcnt = boardDAO.insertBoard(board);
		assertNotEquals(0, rowcnt);
	}

	@Test
	public void testSelectBoardList() {
		List<BoardVO> dataList = boardDAO.selectBoardList(pagingVO);
		assertNotEquals(0, dataList.size());
	}

	@Test
	public void testSelectTotalRecord() {
		BoardVO board = boardDAO.selectBoard(104);
		assertNotNull(board);
		board.getAttatchList()
			.stream().forEach(System.out::println);
		
//		log.info("board : {}", board);
	}

	@Test
	public void testSelectBoard() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateBoard() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteBoard() {
		fail("Not yet implemented");
	}

}
