package kr.or.ddit.prod.controller;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.mvc.annotation.RequestMethod;
import kr.or.ddit.mvc.annotation.resolvers.ModelAttribute;
import kr.or.ddit.mvc.annotation.resolvers.RequestParam;
import kr.or.ddit.mvc.annotation.resolvers.RequestPart;
import kr.or.ddit.mvc.annotation.stereotype.Controller;
import kr.or.ddit.mvc.annotation.stereotype.RequestMapping;
import kr.or.ddit.mvc.multipart.MultipartFile;
import kr.or.ddit.prod.dao.OthersDAO;
import kr.or.ddit.prod.dao.OthersDAOImpl;
import kr.or.ddit.prod.service.ProdService;
import kr.or.ddit.prod.service.ProdServiceImpl;
import kr.or.ddit.validate.UpdateGroup;
import kr.or.ddit.validate.ValidationUtils;
import kr.or.ddit.vo.ProdVO;

@Controller
public class ProdUpdateController {
	private ProdService service = new ProdServiceImpl();
	private OthersDAO othersDAO = new OthersDAOImpl();

	private void addAttribute(HttpServletRequest req) {
		req.setAttribute("lprodList", othersDAO.selectLprodList());
		req.setAttribute("buyerList", othersDAO.selectBuyerList(null));
	}
	
	@RequestMapping("/prod/prodUpdate.do")
	public String updateForm(
//			@SessionAttribute("authMember") MemberVO authMember, -> 이 어노테이션을 만들어보자
			@RequestParam("what") String prodId,
			HttpServletRequest req
			) {
		ProdVO prod = service.retriveProd(prodId);
		req.setAttribute("prod", prod);
		addAttribute(req);
		return "prod/prodForm";
	}
	
	@RequestMapping(value="/prod/prodUpdate.do", method=RequestMethod.POST)
	public String updateProcess(
			@ModelAttribute("prod") ProdVO prod, // command Object라고 부른다.
			HttpServletRequest req,
//			@RequestPart("prodImage") MultipartFile prodImage
			@RequestPart(value="prodImage", required=false) MultipartFile prodImage // 상품 등록 있을수도 있고 없을 수도 있다.
			) throws IOException {

		
//		1. 저장
		String saveFolderURL = "/resources/prodImages";
		ServletContext application = req.getServletContext();
		String saveFolderPath = application.getRealPath(saveFolderURL);
		File saveFolder = new File(saveFolderPath);
		if(!saveFolder.exists()) 
			saveFolder.mkdirs();
		prod.saveTo(saveFolder);
		
		
		Map<String, List<String>> errors = new LinkedHashMap<>();
		req.setAttribute("errors", errors);

		String viewName = null;
		
		boolean valid = ValidationUtils.validate(prod, errors, UpdateGroup.class);
				
		if(!valid) {			
			viewName = "prod/prodForm";
		}else {			
			ServiceResult result = service.modifyProd(prod);
			switch(result) {
			case FAIL:
				req.setAttribute("message", "서버 오류, 좀따 다시.");
				viewName = "prod/prodForm";
				break;
			default:
				viewName = "redirect:/prod/prodView.do?what="+prod.getProdId();
				break;
			}
		}
		
		return viewName;
	}
	
}
