package kr.or.ddit.file.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class FileUploadController {
	
	@RequestMapping(value="/file/upload.do", method=RequestMethod.POST)
//	@PostMapping("/file/upload.do") <- 이렇게도 가능하다. spring에서는
	public String upload(HttpServletRequest req, HttpSession session) throws IOException, ServletException {
		// 버전 3 방식으로 파일 업로드 (@since Servlet 3.0) -> Part라는 인터페이스를 들어가봐라
		// Part는 req에서 body에 mutyfly이 파트 나눈걸 하나 하나 파트를 다 가져올 수 있다.
		// 파라미터가 없지만 3.0버전부터는 없던 파라미터를 만들어준다
		String textPart = req.getParameter("textPart");
		String numPart = req.getParameter("numPart");
		String datePart = req.getParameter("datePart");
		log.info("textPart : {}", textPart);
		log.info("numPart : {}", numPart);
		log.info("datePart : {}", datePart);
		session.setAttribute("textPart", textPart);
		session.setAttribute("numPart", numPart);
		session.setAttribute("datePart", datePart);
		
		
		// /WebStudy03_MVC/src/main/webapp/resources/prodImages
		// /resources/prodImages -> 논리적인 주소
		String saveFolderURL = "/resources/prodImages";
		// 어플리케이션 기본 객체가 들어간다.
		ServletContext application = req.getServletContext();
		String saveFolderPath = application.getRealPath(saveFolderURL);
		File saveFolder = new File(saveFolderPath);
		if(!saveFolder.exists()) {
			// mkdirs와 mkdir의 차이는 s가 붙은건계층 구조로 생성한다.
			saveFolder.mkdirs();
		}
			// 일반 타입은 걸러낸다.
		List<String> metadata = req.getParts().stream()
					// 이미지가 아닌건 걸러낸다.
					.filter((p)->p.getContentType()!=null && p.getContentType().startsWith("image/")) // ContentType 일반 타입은 없다.
					.map((p)->{
						String originalFilename = p.getSubmittedFileName();
						// 파일명은 원본 파일명으로 저장하지 않는다.
						// 1. 빽도어라는 공격에 당할 수 있다. 원본 파일명에 확장자가 포함되어 있어서 확장자명으로 컨트롤 가능하다?
						// 2. 다른 사용자가 똑같은 파일명을 저장하면 다른 하나는 날라간다.
						
						// 유니크 아이디를 만들어주는 API
						String saveFilename = UUID.randomUUID().toString();
						File saveFile = new File(saveFolder, saveFilename);
						// wrtie안에 IO작업이 이루어진다.
						try {
							p.write(saveFile.getCanonicalPath());
							// 꺼내기 쉽게 url주소 타입으로..
							String saveFileURL = saveFolderURL + "/" + saveFilename;
							return saveFileURL;
						} catch (IOException e) {
							throw new RuntimeException(e);
						}
					}).collect(Collectors.toList());
		session.setAttribute("fileMetadata", metadata);
		
		
		// 3가지 형태의 리소스
		// class 리소스
		// 파일 리소스
		// 웹 리소스
		// 주소를 통해 접근하고 싶다면? -> web
		// url통해 접근 불가 -> 파일, class
		// 뷰 페이지에 이미지가 나오려면 -> web
		
		
		
		return "redirect:/fileupload/uploadForm.jsp";
		
	}

}
