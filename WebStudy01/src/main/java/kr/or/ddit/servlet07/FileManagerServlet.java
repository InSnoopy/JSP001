package kr.or.ddit.servlet07;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/browsing/fileManager")
public class FileManagerServlet extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		int sc = validate(req);
		Map<String, Object> modelMap = (Map<String, Object>) req.getAttribute("modelMap");
		if(sc==200) {
			boolean result = fileManage(modelMap);
			req.setAttribute("result", result);
			String viewName = "/jsonView.do";
			req.getRequestDispatcher(viewName).forward(req, resp);
		}else {
			resp.sendError(sc);
		}
	}
	
	private boolean fileManage(Map<String, Object> modelMap) throws IOException{

		File sourceFile = (File) modelMap.get("sourceFile");
		File destinationFolder = (File) modelMap.get("destinationFolder");
//		File destFile = new File(destinationFolder, sourceFile.getName());
		// Path는 인터페이스 Paths로만 객체를 생성할 수 있다. , Path는 경로만을 가지고 있다.
		Path destFilePath = Paths.get(destinationFolder.getCanonicalPath(), sourceFile.getName());
		String command = (String) modelMap.get("command");
		
		Files.copy(sourceFile.toPath(), destFilePath, StandardCopyOption.REPLACE_EXISTING);
//		Files에서 copy뿐만 아니라 이동 삭제 가능하다.
//		Files.move(source, target, options)
//		Files.delete(path);
		
		return true;
	}

	private int validate(HttpServletRequest req) {
		String srcFile = req.getParameter("srcFile");
		String destFolder = req.getParameter("destFolder");
		String command = req.getParameter("command");
		
		// map은 vo의 역할을 한다.
		Map<String, Object> modelMap = new HashMap<>();
		req.setAttribute("modelMap", modelMap);
		
		int sc = 200;
		
		if(srcFile==null || srcFile.isEmpty() 
				|| destFolder==null || destFolder.isEmpty() 
				|| command==null || command.isEmpty()) {
				sc = 400;
			}else {
				ServletContext application = req.getServletContext();
				String srcPath = application.getRealPath(srcFile);
				File sourceFile = new File(srcPath);
				if(!sourceFile.exists()) {
					sc = 404;
				}else if(sourceFile.isDirectory()) {
					sc = 400;
				}else {
					modelMap.put("sourceFile", sourceFile);
				}
				String destPath = application.getRealPath(destFolder);
				File destinationFolder = new File(destPath);
				if(!destinationFolder.exists()) {
					sc = 404;
				}else if(destinationFolder.isFile()) {
					sc = 400;
				}else {
					modelMap.put("destinationFolder", destinationFolder);
				}
				if(! "COPY".equals(command)) {
					sc = 404;
				}else {
					modelMap.put("command", command);
				}
			}
		
		return sc;
	}
}
