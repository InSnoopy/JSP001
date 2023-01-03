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
import kr.or.ddit.vo.MemoVO;

@WebServlet("/memo")
public class MemoControllerServlet extends HttpServlet {
	private static final Logger log = LoggerFactory.getLogger(MemoControllerServlet.class);
	
	// private MemoDAO dao = FileSystemMemoDAOImpl.getInstance();
	// private MemoDAO dao = DataBaseMemoDAOImpl.getInstance();
	// 객체 생성에 대한 의존방식 컨테이너가 필요하다. (이것이 스프링) 구현체에 대한 이름이 없다.
	// private MemoDAO dao;
	private MemoDAO dao = new MemoDAOImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 1.요청분석
		String accept = req.getHeader("Accept");
		log.info("accept header : {}", accept);
		System.out.println(accept);
		if (accept.contains("xml")) {
			resp.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE);
			return;
		}
		// 2.모델 확보
//		List<MemoVO> listMemo = dao.selectMemoList();
		List<MemoVO> listMemo = dao.selectMemoList();
		// 3.모델 공유 (setAttribute)
		req.setAttribute("listMemo", listMemo);
		// 4. 뷰 선택
		String viewName = null;
		int statusCode = HttpServletResponse.SC_OK;
		if (accept.contains("json")) {
			viewName = "/jsonView.do";
		} else if (accept.contains("xml")) {
			viewName = "/xmlView.do";
		} else if (accept.contains("plain")) {
			viewName = "/WEB-INF/views/04/plainView.jsp";
		} else {
			statusCode = HttpServletResponse.SC_NOT_ACCEPTABLE;
		}

		if (statusCode == HttpServletResponse.SC_OK) {
			// 5. 뷰로 이동
			req.getRequestDispatcher(viewName).forward(req, resp);
		} else {
			resp.sendError(statusCode);
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Post-Redirect-Get : PRG pattern
		MemoVO memo = getMemoFromRequest(req);
		dao.insertMemo(memo);

		// 추가된 메모를 포함해서 list를 출력하기 위해
		resp.sendRedirect(req.getContextPath() + "/memo");
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

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

}
