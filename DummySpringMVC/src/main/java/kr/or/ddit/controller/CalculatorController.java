package kr.or.ddit.controller;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;

@Controller
public class CalculatorController {
	@RequestMapping(method=RequestMethod.GET, value="/calcute")
	public String calForm() {
		
		return "cal/form";
	}
	
	// 비동기 방식 case4번
	// 모델에 대한 정보를 보낸다. view에 대한 정보는 없다.
	@RequestMapping(method=RequestMethod.POST, value="/calcute")
	public String calProcess(
			@RequestParam int left,
			@RequestParam int right,
			HttpSession session,
			Model model
			) throws StreamWriteException, DatabindException, IOException {
	
		int result = left + right;
		model.addAttribute("result", result);
		// jsonView servlet-context에 생성함
		return "jsonView";
	}
	
	
//	// 비동기 방식 case3번
//	// 모델에 대한 정보를 보낸다. view에 대한 정보는 없다.
//	@RequestMapping(method=RequestMethod.POST, value="/calcute")
//	@ResponseBody // 핸들러 어뎁터가 알아서 직렬화를 해준다?
//	public Map<String, Object> calProcess(
//			@RequestParam int left,
//			@RequestParam int right,
//			HttpSession session
//			) throws StreamWriteException, DatabindException, IOException {
//	
//		int result = left + right;
//		Map<String, Object> target = Collections.singletonMap("result", result);
//		return target;
//	}
	
//	// 비동기 방식 case2번
//	@RequestMapping(method=RequestMethod.POST, value="/calcute")
//	public void calProcess(
//			@RequestParam int left,
//			@RequestParam int right,
//			HttpSession session,
//			OutputStream os
//			) throws StreamWriteException, DatabindException, IOException {
//		
//		int result = left + right;
//		Map<String, Object> target = Collections.singletonMap("result", result);
//		ObjectMapper mapper = new ObjectMapper();
//		mapper.writeValue(os, target); // 마샬링 + 직렬화
//	}
	
	
//  // 비동기 방식 case1번
//	@RequestMapping(method=RequestMethod.POST, value="/calcute")
//	public void calProcess(
//			@RequestParam int left,
//			@RequestParam int right,
//			HttpSession session,
//			HttpServletResponse resp
//			) throws StreamWriteException, DatabindException, IOException {
//		
//		int result = left + right;
//		Map<String, Object> target = Collections.singletonMap("result", result);
////		1. marshalling (마샬링)
////		2. serialization (직렬화)
//		ObjectMapper mapper = new ObjectMapper();
////		mapper.reader() // 언마샬링
////		mapper.writer() // 마샬링
//		mapper.writeValue(resp.getOutputStream(), target); // 마샬링 + 직렬화
//		
////		session.setAttribute("result", result);
////		String viewName = "redirect:/calcute";		
////		return viewName;
//	}
	
	
// 동기 방식
//	@RequestMapping(method=RequestMethod.POST, value="/calcute")
//	public String calProcess(
//			@RequestParam int left,
//			@RequestParam int right
//			){
//		
//		int result = left + right;		
////		session.setAttribute("result", result);
////		String viewName = "redirect:/calcute";		
////		return viewName;
//	}
	
}
