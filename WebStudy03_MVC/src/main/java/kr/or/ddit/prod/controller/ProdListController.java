package kr.or.ddit.prod.controller;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;

import kr.or.ddit.mvc.annotation.resolvers.RequestParam;
import kr.or.ddit.mvc.annotation.stereotype.Controller;
import kr.or.ddit.mvc.annotation.stereotype.RequestMapping;
import kr.or.ddit.prod.dao.OthersDAO;
import kr.or.ddit.prod.dao.OthersDAOImpl;
import kr.or.ddit.prod.service.ProdService;
import kr.or.ddit.prod.service.ProdServiceImpl;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ProdVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ProdListController{
	private ProdService service = new ProdServiceImpl();
	private OthersDAO othersDAO = new OthersDAOImpl();

	private void addAttribute(HttpServletRequest req) {
		req.setAttribute("lprodList", othersDAO.selectLprodList());
		req.setAttribute("buyerList", othersDAO.selectBuyerList(null));
	}

	private String listUI(HttpServletRequest req) {
		addAttribute(req);
//		4. 뷰 선택
		return "prod/prodList";
	}
	
	private String listData( int currentPage, HttpServletRequest req ) throws ServletException {
//		1. 요청 분석
//		String pageParam = req.getParameter("page");
		
		ProdVO detailCondition = new ProdVO();
		req.setAttribute("detailCondition", detailCondition);
		try {
			BeanUtils.populate(detailCondition, req.getParameterMap());
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new ServletException(e);
		}

		PagingVO<ProdVO> pagingVO = new PagingVO<>(5, 2);
		pagingVO.setCurrentPage(currentPage);
		pagingVO.setDetailCondition(detailCondition);

		service.retrieveProdList(pagingVO);
		req.setAttribute("pagingVO", pagingVO);
		return "forward:/jsonView.do";
	}
	
	@RequestMapping("/prod/prodList.do")
	public String prodList(
			@RequestParam(value="page", required=false, defaultValue="1") int currentPage
			, HttpServletRequest req
	) throws ServletException{
		String accept = req.getHeader("Accept");
		String viewName = null;
		if(accept.contains("json")) {
			viewName = listData(currentPage,req);
		}else {
			viewName = listUI(req);
		}

		return viewName;
	}
}
