package kr.or.ddit.servlet01;

import java.io.*;
import javax.servlet.http.*;
import javax.servlet.*;
import java.util.*;

public class ImageList extends HttpServlet{

	private String imageFolder;
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{

		StringBuffer html = new StringBuffer();
		
		html.append("<html>\n");
		html.append("<body>\n");
		html.append("<form action=\'imageListServlet.do\' method=\'get\' id=\'myForm\'>\n");
		html.append("<input type=\"submit\">");
		html.append("</form>");
		html.append("<select name='imgChoice' form='myForm'>\n");
		html.append("<option value='cat1.jpg'>cat1</option>\n");
		html.append("<option value='cat2.png'>cat2</option>\n");
		html.append("<option value='cat3.png'>cat3</option>\n");
		html.append("<option value='cat4.png'>cat4</option>\n");
		html.append("<option value='cat5.png'>cat5</option>\n");
		html.append("<option value='cat6.png'>cat6</option>\n");
		html.append("<option value='cat7.png'>cat7</option>\n");
		html.append("<option value='cute1.png'>cute1</option>\n");
		html.append("<option value='cute3.jpg'>cute2</option>\n");
		html.append("<option value='cute4.jpg'>cute3</option>\n");
		html.append("<option value='cute5.jpg'>cute4</option>\n");
		html.append("<option value='cute7.jpg'>cute5</option>\n");
		html.append("<option value='cute8.jpg'>cute6</option>\n");
		html.append("<option value='cute9.jpg'>cute7</option>\n");
		html.append("</select>\n");
		html.append("<script>\n");				
		html.append("</script>\n");
		html.append("</body>\n");
		html.append("</html>\n");
		
		resp.getWriter().println(html);
		
	}

}