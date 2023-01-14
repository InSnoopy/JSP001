package kr.or.ddit.member.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import kr.or.ddit.member.service.MemberService;
import kr.or.ddit.member.vo.MemberVO;
import kr.or.ddit.vo.PagingVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MemberListController {
	
	@Inject
	private MemberService service;
	
	@RequestMapping("/member/memberList.do")
	public ModelAndView memberList(
		@RequestParam(value="page", required=false, defaultValue="1") int currentPage	
	) {
		PagingVO<MemberVO> pagingVO = new PagingVO<>(4,2); 
		pagingVO.setCurrentPage(currentPage);
		
		ModelAndView mav = new ModelAndView();
		
		List<MemberVO> memberList = service.retrieveMemberList(pagingVO);
		mav.addObject("pagingVO" , pagingVO);
		log.info("paging data : {}", pagingVO);
		mav.setViewName("member/memberList");
		
		return mav;
	}
	
	
}
