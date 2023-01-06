package kr.or.ddit.buyer.controller;

import javax.servlet.http.HttpServletRequest;

import kr.or.ddit.mvc.annotation.resolvers.RequestPart;
import kr.or.ddit.mvc.annotation.stereotype.Controller;
import kr.or.ddit.mvc.annotation.stereotype.RequestMapping;

@Controller
public class BuyerViewController {
	@RequestMapping("/buyer/buyerView.do")
	public String byerView(@RequestPart("what") String buyerId,
			HttpServletRequest req) {
		
		
		
		return "buyer/buyerView";
	}
}
