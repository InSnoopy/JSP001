package kr.or.ddit.servlet01;

import java.io.*;
import javax.servlet.http.*;
import javax.servlet.*;
import java.util.*;

public class ImageStreamingServlet extends HttpServlet{

	private String imageFolder;
	private ServletContext application;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		// imageFolder = config.getInitParameter("imageFolder");
		application = getServletContext();
		imageFolder = application.getInitParameter("imageFolder");
		System.out.printf("받은 파라미터 : %s\n", imageFolder);
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{

		// 이 객체는 싱글톤 (가장 먼저 생성되고 가장 오래 살아남는 객체, 가장 범위가 넓은 저장소)
		// 여기에다가 저장하게되면 어플리케이션 모든 범위에 공유 가능
		ServletContext application = getServletContext(); 
		
		String imageName = req.getParameter("imgChoice");
		if(imageName == null || imageName.isEmpty()) {
			// 상태 코드를 개발자가 임의로 지정 가능
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		String mimeType = application.getMimeType(imageName);
		resp.setContentType(mimeType);
		
		File imageFile = new File(imageFolder, imageName);
		if(!imageFile.exists()) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		FileInputStream fis = null;
		OutputStream os = null;
		// 중간에 예외가 발생된다면 예외를 던진다. 그렇다면 close(); 안 닫친다.
		// try로 그래서 묶었다.
		try {
			fis = new FileInputStream(imageFile);
			os = resp.getOutputStream();
			int tmp = -1;
			while((tmp=fis.read()) !=-1){
				os.write(tmp);
			}
		}finally {
			if(fis!=null)
				fis.close();
			if(os!=null)
				os.close();
		}
	}

}