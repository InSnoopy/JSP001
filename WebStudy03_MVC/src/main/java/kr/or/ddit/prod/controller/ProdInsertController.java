package kr.or.ddit.prod.controller;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.mvc.annotation.RequestMethod;
import kr.or.ddit.mvc.annotation.resolvers.ModelAttribute;
import kr.or.ddit.mvc.annotation.resolvers.RequestPart;
import kr.or.ddit.mvc.annotation.stereotype.Controller;
import kr.or.ddit.mvc.annotation.stereotype.RequestMapping;
import kr.or.ddit.mvc.multipart.MultipartFile;
import kr.or.ddit.mvc.multipart.MultipartHttpServletRequest;
import kr.or.ddit.prod.dao.OthersDAO;
import kr.or.ddit.prod.dao.OthersDAOImpl;
import kr.or.ddit.prod.service.ProdService;
import kr.or.ddit.prod.service.ProdServiceImpl;
import kr.or.ddit.validate.InsertGroup;
import kr.or.ddit.validate.ValidationUtils;
import kr.or.ddit.vo.ProdVO;

@Controller
public class ProdInsertController{

	private ProdService service = new ProdServiceImpl();
	private OthersDAO othersDAO = new OthersDAOImpl();

	private void addAttribute(HttpServletRequest req) {
		req.setAttribute("lprodList", othersDAO.selectLprodList());
		req.setAttribute("buyerList", othersDAO.selectBuyerList(null));
	}
	
	@RequestMapping("/prod/prodInsert.do")
	public String prodForm(HttpServletRequest req) {
		addAttribute(req);
		return "prod/prodForm";
	}

	@RequestMapping(value="/prod/prodInsert.do", method=RequestMethod.POST)
	public String insertProcess(
		// command Object -> ProdVO로 자동 맵핑해주는?
		HttpServletRequest req,
		@RequestPart("prodImage") MultipartFile prodImage,
		@ModelAttribute("prod") ProdVO prod
	) throws IOException, ServletException {
		addAttribute(req);
		
		prod.setProdImage(prodImage);
				
		
//		1. 저장
		String saveFolderURL = "/resources/prodImages";
		ServletContext application = req.getServletContext();
		String saveFolderPath = application.getRealPath(saveFolderURL);
		File saveFolder = new File(saveFolderPath);
		if(!saveFolder.exists()) 
			saveFolder.mkdirs();
		prod.saveTo(saveFolder);
		
		// 아래 코드가 위에 코드로 줄여짐
//		if(prodImage!=null && !prodImage.isEmpty()) {
////			1. 저장
//			String saveFolderURL = "/resources/prodImages";
//			ServletContext application = req.getServletContext();
//			String saveFolderPath = application.getRealPath(saveFolderURL);
//			File saveFolder = new File(saveFolderPath);
//			if(!saveFolder.exists()) 
//				saveFolder.mkdirs();
////			2. metadata 추출
//			String saveFileName = UUID.randomUUID().toString();
//			prodImage.transferTo(new File(saveFolder, saveFileName));
////			3. DB 저장 : prodImg
//			prod.setProdImg(saveFileName);
//		}
		
		
		Map<String, List<String>> errors = new LinkedHashMap<>();
		req.setAttribute("errors", errors);
		boolean valid = ValidationUtils.validate(prod, errors, InsertGroup.class);
		String viewName = null;
		if(!valid) {			
			viewName = "prod/prodForm";
		}else {
			ServiceResult result = service.createProd(prod);
			
			switch (result) {
			case FAIL:
				req.setAttribute("message", "서버에 문제 있음. 이따 다시 하세요");
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
