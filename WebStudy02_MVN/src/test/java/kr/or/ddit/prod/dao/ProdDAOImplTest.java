package kr.or.ddit.prod.dao;

import static org.junit.Assert.*;

import org.junit.Test;

import kr.or.ddit.member.dao.MemberDAO;
import kr.or.ddit.member.dao.MemberDAOImpl;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.ProdVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProdDAOImplTest {

	private ProdDAO dao = new ProdDAOImpl();
	private ProdVO prod;
	
	@Test
	public void test() {
		prod = new ProdVO();
		prod.setProdId("P101000001");
	}
	
	@Test
	public void testSelectMember() {
		ProdVO prod = dao.selectProd("P101000001");
		assertNotNull(prod);
		log.info("buyer : {}", prod.getBuyer());
		prod.getMemberSet().stream().forEach(user->{
				log.info("구매자 : {}", user);
			});
	}

}
