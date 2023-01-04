package kr.or.ddit.memo.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import kr.or.ddit.memo.dao.DataBaseMemoDAOImpl;
import kr.or.ddit.memo.dao.MemoDAO;
import kr.or.ddit.memo.dao.MemoDAOImpl;
import kr.or.ddit.mvc.annotation.RequestMethod;
import kr.or.ddit.mvc.annotation.resolvers.RequestParam;
import kr.or.ddit.mvc.annotation.stereotype.Controller;
import kr.or.ddit.mvc.annotation.stereotype.RequestMapping;
import kr.or.ddit.vo.MemoVO;

@Controller
public class MemoController{
	private static final Logger log = LoggerFactory.getLogger(MemoController.class);
	
	// private MemoDAO dao = FileSystemMemoDAOImpl.getInstance();
	// private MemoDAO dao = DataBaseMemoDAOImpl.getInstance();
	// 객체 생성에 대한 의존방식 컨테이너가 필요하다. (이것이 스프링) 구현체에 대한 이름이 없다.
	// private MemoDAO dao;
	private MemoDAO dao = new MemoDAOImpl();

	@RequestMapping("/memo")
	public String doGet(
//			@RequestHeader("Accept") String accept,
			HttpServletRequest req, 
			HttpServletResponse resp
		) throws ServletException, IOException {

		String accept = req.getHeader("Accept");
		log.info("accept header : {}", accept);
		System.out.println(accept);
		if (accept.contains("xml")) {
			resp.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE);
			return null;
		}

		List<MemoVO> listMemo = dao.selectMemoList();
		req.setAttribute("listMemo", listMemo);
		return "forward:/jsonView.do";

	}

	@RequestMapping(value="/memo", method=RequestMethod.POST)
	public String doPost(HttpServletRequest req
			) throws ServletException, IOException {
		// Post-Redirect-Get : PRG pattern
		MemoVO memo = getMemoFromRequest(req);
		dao.insertMemo(memo);
		return "redirect:/memo";
	}

	private MemoVO getMemoFromRequest(HttpServletRequest req) throws IOException {

		// request안에 메모 객체가 없기 떄문에?
		// req에 memo객체가 있다면?
		// 파라미터로 아니라 json으로 보낸다면?
		// 먼저 역직렬화를 먼저하고
		// 그 다음에 언마샬링을 한다.

		MemoVO memo = null;
		String contentType = req.getContentType();
		if (contentType.contains("json")) {
			try (BufferedReader br = req.getReader(); // body content read용 입력 스트림
			) {
				memo = new ObjectMapper().readValue(br, MemoVO.class);
			}
		} else if (contentType.contains("xml")) {
			try (BufferedReader br = req.getReader(); // body content read용 입력 스트림
			) {
				memo = new XmlMapper().readValue(br, MemoVO.class);
			}
		} else {
			memo = new MemoVO();
			String writer = req.getParameter("writer");
			String date = req.getParameter("date");
			String content = req.getParameter("content");

			memo.setWriter(writer);
			memo.setDate(date);
			memo.setContent(content);
		}
		return memo;
	}

	@RequestMapping(value="/memo", method=RequestMethod.PUT)
	public String doPut(HttpServletRequest req) throws ServletException, IOException {
		return null;
	}

	@RequestMapping(value="/memo", method=RequestMethod.DELETE)
	public String doDelete(HttpServletRequest req) throws ServletException, IOException {
		return null;
	}

}
