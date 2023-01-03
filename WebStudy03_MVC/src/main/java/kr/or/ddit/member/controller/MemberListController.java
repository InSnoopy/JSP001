package kr.or.ddit.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.member.service.MemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.mvc.annotation.resolvers.ModelAttribute;
import kr.or.ddit.mvc.annotation.resolvers.RequestParam;
import kr.or.ddit.mvc.annotation.stereotype.Controller;
import kr.or.ddit.mvc.annotation.stereotype.RequestMapping;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.SearchVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
// 프론트 뒤에서 동작하는 커멘드라는 뜻
@Controller
public class MemberListController{
//	의존관계 형성	
	private MemberService service = new MemberServiceImpl();

	// 개발자의 자유도가 높아졌다.
	@RequestMapping("/member/memberList.do")
	public String memberList(
		@RequestParam(value="page", required=false, defaultValue="1") int currentPage
//		, @RequestParam(value="searchType", required=false) String searchType
//		, @RequestParam(value="searchWord", required=false) String searchWord
//		위에 2개의 파람을 아래처럼 한번에 받는 방법으로 또 업그레이드
		, @ModelAttribute SearchVO simpleCondition
		, HttpServletRequest req
	){

		PagingVO<MemberVO> pagingVO = new PagingVO<>(4,2); 
		pagingVO.setCurrentPage(currentPage);
		pagingVO.setSimpleCondition(simpleCondition);
		
		service.retriveMemberList(pagingVO);
		
		req.setAttribute("pagingVO", pagingVO);
		log.info("paging data : {}", pagingVO);

		String viewName = "member/memberList"; // logical view name
		
		return viewName;
	}
}
