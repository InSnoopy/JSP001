package kr.or.ddit.annotation.dummy;

import kr.or.ddit.annotation.Controller;
import kr.or.ddit.annotation.RequestMapping;
import kr.or.ddit.vo.MemberVO;
 
@Controller // 프론트 뒤에서 동작하는 클래스
public class DummyCommandHandler {
	
	private String dummy() {
		return null;
	}
	
	@RequestMapping("/testInsert")
	public String form() {
		return "test/form";
	}
	
	@RequestMapping(value="/testInsert", method="post")
	public String process() {
		// Request가 아니라 MemberVO로 받으면 바로 서비스로 ..?
		// 커멘드 오브젝트가 파라미터로 넘어온다. 백엔드와 프론트 사이의 결합력을 없애준다.
		return "redirect:/testInsert";
	}
}
