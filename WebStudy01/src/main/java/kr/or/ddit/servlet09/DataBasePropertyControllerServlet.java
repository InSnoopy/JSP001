package kr.or.ddit.servlet09;

import java.beans.Statement;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.ddit.servlet09.dao.DataBasePropertyDAO;
import kr.or.ddit.servlet09.dao.DataBasePropertyDAOImpl;
import kr.or.ddit.servlet09.service.DataBasePropertyService;
import kr.or.ddit.servlet09.service.DataBasePropertyServiceImpl;
import kr.or.ddit.vo.DataBasePropertyVO;



@WebServlet("/13/properties.do")
public class DataBasePropertyControllerServlet extends HttpServlet{
	
	// 인터페이스를 사용하는 이유 두가지
	// 1. 협업
	// 2. 실행 코드의 캡슐화
	// -> 인터페이스는 메서드의 내용은 안들어 있기 때문에 실행 코드의 캡슐화라고 한다.
	private DataBasePropertyService service = new DataBasePropertyServiceImpl();
   
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String propertyName = req.getParameter("propertyName");
		
		// 2.
		List<DataBasePropertyVO> list = service.retrievePropertyList(propertyName);
		
		// 3.
		req.setAttribute("list", list);

		// 4.
		String viewName = "/WEB-INF/views/13/jdbcDesc.jsp";

		// 5.
		if (viewName.startsWith("redirect:")) {
			viewName = viewName.substring("redirect:".length());
			resp.sendRedirect(req.getContextPath() + viewName);
		} else {
			req.getRequestDispatcher(viewName).forward(req, resp);
		}

	}
}







