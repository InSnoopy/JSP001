package kr.or.ddit.servlet04;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/03/props/propsView.do")
public class PropertiesControllerServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String accept = req.getHeader("Accept");
		if(accept.toLowerCase().contains("json")) {
			// native(DataStore.properties)->json : marshalling
			// {"prop1":"value1", ....}
			StringBuffer json = null;
			resp.setContentType("applicaiotn/json;charset=UTF-8");
			try(
			PrintWriter out = resp.getWriter();
			){
				out.print(json);				
			}
		}else {
			String path = "/WEB-INF/views/03/propsView.jsp";
			req.getRequestDispatcher(path).forward(req,resp);			
		}
		
	}
}
