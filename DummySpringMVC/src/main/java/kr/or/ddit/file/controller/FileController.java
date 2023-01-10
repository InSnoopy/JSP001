package kr.or.ddit.file.controller;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.vo.FileTestVO;

@Controller
@RequestMapping("/fileUpload") // get, post 주소를 다 생략 가능
public class FileController {

	@Inject
	private WebApplicationContext context; // ApplicationContext 안에 있다 뭘 해도 상관없다.
	private Resource saveFolderRes;
	private File saveFolder;
	
	@PostConstruct
	public void init() throws IOException {
		saveFolderRes = context.getResource("classpath:/kr/or/ddit/file");
		saveFolder = saveFolderRes.getFile();
	}
	
	@GetMapping
	public String fileForm() {
		return "file/uploadForm";
	}
	
	@PostMapping
	public String fileProcessCase2(
			@ModelAttribute("fileVO") FileTestVO commandObject,
			RedirectAttributes redirectAttributes
		) throws IllegalStateException, IOException {
		commandObject.file1SaveTo(saveFolder);
		commandObject.file2SaveTo(saveFolder);
		redirectAttributes.addFlashAttribute("result", commandObject);
		return "redirect:/fileUpload";
	}
	
//	@PostMapping
	public String fileProcessCase1(

		@RequestParam String textParam,
		@RequestParam String dateParam,
		@RequestPart MultipartFile file1,
		@RequestPart MultipartFile[] file2,
//		HttpSession session
		RedirectAttributes redirectAttributes // redirect로 속성 저장
	) throws IllegalStateException, IOException {
		
		Map<String, Object> result = new LinkedHashMap<>();
		result.put("textParam", textParam);
		result.put("dateParam", dateParam);
		
		if(!file1.isEmpty()) {		
			File dest = new File(saveFolder, UUID.randomUUID().toString());
			file1.transferTo(dest);
			result.put("file1", Collections.singletonMap("savename", dest.getName()));
		}
		
		result.put("file2", Arrays.stream(file2)
								.filter((f)->!f.isEmpty())
								.map((f)->{
									try {
										File dest = new File(saveFolder, UUID.randomUUID().toString());
										f.transferTo(dest);
										return Collections.singletonMap("savename", dest.getName());
									} catch (IOException e) {
										throw new RuntimeException(e);
									}
								}).collect(Collectors.toList()) // 값을 쪼갠걸(위에서 f) 리스트 형태로 다시 모은다. 
					);
		// session.setAttribute("result", result);
		// redirect로도 이렇게 집어 넣을 수 있다. 꺼내는 순간 바로 삭제된다.
		redirectAttributes.addFlashAttribute("result", result);
		return "redirect:/fileUpload";
	}
}
