package kr.or.ddit.mvc.multipart;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * Part -> MultipartFile
 *
 */
public class MultipartHttpServletRequest extends HttpServletRequestWrapper{
	
	// req.getParameter("") 이걸보면 Map형식으로 쓰인다.
	// 우리도 Map으로 쓰고 key는 Park name으로 쓸거다.
	// MutipartFile에 이름이 같은 경우가 있어서 List로 관리한다.
	// getParameterMap처럼 쓰려고.. 만들었다.
	private Map<String, List<MultipartFile>> fileMap;

	public MultipartHttpServletRequest(HttpServletRequest request) throws IOException, ServletException {
		super(request);
		parseRequest(request);
	}

	private void parseRequest(HttpServletRequest request) throws IOException, ServletException {
		fileMap = new LinkedHashMap<>();
		request.getParts().stream()
					.filter((p)->p.getContentType()!=null)
					.forEach((p)->{
						String partName = p.getName();
						MultipartFile file = new StandardServletMultipartFile(p);
						List<MultipartFile> fileList = Optional.ofNullable(fileMap.get(partName))
																.orElse(new ArrayList<>());
						fileList.add(file);
						fileMap.put(partName, fileList);
					});
	}


	public Map<String, List<MultipartFile>> getFileMap() {
		return fileMap;
	}
	public MultipartFile getFile(String name){
		List<MultipartFile> files = fileMap.get(name);
		if(files!=null && !files.isEmpty())
			return files.get(0);
		else
			return null;
	}
	
	public List<MultipartFile> getFiles(String name){
		return fileMap.get(name);
	}
	
	// Enumration 이랑 Iterator은 같은 접근 방법을 쓰였다??
	public Enumeration<String> getFileNames(){
		Iterator<String> names = fileMap.keySet().iterator();
		// interface는 객체를 생성하지 못하지만 익명객체로 사용한다.
		return new Enumeration<String>() {
			
			@Override
			public boolean hasMoreElements() {
				return names.hasNext();
			}

			@Override
			public String nextElement() {
				return names.next();
			}
		};
	}
	


}
