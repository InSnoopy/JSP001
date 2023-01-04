package kr.or.ddit.prod.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import kr.or.ddit.member.dao.MemberDAO;
import kr.or.ddit.member.dao.MemberDAOImpl;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ProdVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProdDAOImplTest {

	private ProdDAO dao = new ProdDAOImpl();
	private PagingVO<ProdVO> pagingVO;
	private ProdVO prod;
	
	@Before
	public void setUp() {
		pagingVO = new PagingVO<>();
		pagingVO.setCurrentPage(1);
		
		prod = new ProdVO();
		prod.setProdId("P101000008");
		prod.setProdName("모니터 삼성전자15인치칼라");
		prod.setProdLgu("P101");
		prod.setProdBuyer("P10101");
		prod.setProdCost(210000);
		prod.setProdPrice(290000);
		prod.setProdSale(230000);
		prod.setProdOutline("평면모니터의");
		prod.setProdImg("P101000001.gif");
		prod.setProdTotalstock(0);
		prod.setProdProperstock(0);
	}
	
	@Test
	public void testSelectTotalRecord() {
		int tr = dao.selectTotalRecord(pagingVO);
		assertNotEquals(0, tr);
	}
	
	@Test
	public void testSelectProdList() {
		List<ProdVO> prodList = dao.selectProdList(pagingVO);
		assertEquals(10, prodList.size());
		log.info("prodList : {}", prodList);
	}
	
	@Test
	public void testInsertProd() {
		int cnt = dao.insertProd(prod);
		assertNotEquals(0, cnt);
	}
	
//	@Test
//	public void test() {
////		prod = new ProdVO();
////		prod.setProdId("P101000001");
//	}
	
	
	

	
	


}
