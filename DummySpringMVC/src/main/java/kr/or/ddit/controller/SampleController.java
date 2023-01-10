package kr.or.ddit.controller;

import java.util.Date;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.or.ddit.service.SampleService;

@Controller
public class SampleController {
	@Inject // 이걸로 서비스가 들어와있다~
	private SampleService service;
	
	@RequestMapping("/sample2")
	public String commandHandler(HttpServletRequest req) {
		String info = service.retrieveInfo();
		req.setAttribute("info", info);
		return "sample/view2";
	}
	
	@RequestMapping(value="/sample", method=RequestMethod.GET)
	public String sample(HttpServletRequest req) {
		req.setAttribute("now", new Date());
		
		return "sample/view";
	}
}
