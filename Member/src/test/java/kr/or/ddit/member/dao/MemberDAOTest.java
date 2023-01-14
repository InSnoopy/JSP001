package kr.or.ddit.member.dao;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import kr.or.ddit.member.vo.MemberVO;
import kr.or.ddit.vo.PagingVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class) // 제이유닛 생성
@ContextConfiguration("file:webapp/WEB-INF/spring/*-context.xml") // 가상 컨테이션 생성 (상위 컨테이너 생성)
@WebAppConfiguration // 웹용 컨테이너로 생성하기 위함
public class MemberDAOTest{

	@Inject
	private MemberDAO memberDAO;
	private PagingVO<MemberVO> pagingVO;
	
	@Before
	public void setUp() {
		pagingVO = new PagingVO<>();
		pagingVO.setCurrentPage(1);
	}
	
	@Test
	public void testSelectTotalRecord() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testSelectMemberList() {
		List<MemberVO> dataList = memberDAO.selectMemberList(pagingVO);
		assertNotEquals(0, dataList.size());
		
	}

}
