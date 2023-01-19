package kr.or.ddit;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Controller
@RequestMapping("/index.do")
public class TableSourceReadAndSaveController{
	private static Logger log = LoggerFactory.getLogger(TableSourceReadAndSaveController.class);
	@Inject
	private WebApplicationContext container;
	private ServletContext application;
	private File tblSrc;
	@Value("#{appInfo.savePath}")
	private String savePath;
	
	@Resource(name="wsList")
	private List<WebSocketSession> sessionList;
	
	@PostConstruct
	public void init() throws IOException{
		log.info("주입된 경로 : {}", savePath);
		application = container.getServletContext();
		tblSrc = new File(savePath, "table.source");
		if(!tblSrc.exists()) tblSrc.createNewFile();
	}
	
    @GetMapping  
	public String doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CharSequence src = null;
		synchronized (tblSrc) {
			if(!tblSrc.exists()) tblSrc.createNewFile();
			else src = FileUtils.readFileToString(tblSrc, "UTF-8");
		}
		request.setAttribute("tableSource", src);
		request.setAttribute("today", String.format("%1$tY년%1$tm월%1$td일", Calendar.getInstance()));
		return "/WEB-INF/index.jsp";
	}

    @PostMapping(produces=MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
	public String doPost(@RequestBody String table) throws IOException{
		String result = "FAIL";
		if(table!=null && !table.isEmpty()) {
			synchronized (tblSrc) {
				FileUtils.write(tblSrc, table, "UTF-8");
			}
			result = application.getContextPath()+"/";
		}
		for(WebSocketSession session : sessionList) {
			session.sendMessage(new TextMessage("RELOAD"));
		}
		return result;
	}
	
	@PutMapping
	@ResponseBody
	public String doPut() throws IOException {
		synchronized (tblSrc) {
			FileUtils.deleteQuietly(tblSrc);
		}
		for(WebSocketSession session : sessionList) {
			session.sendMessage(new TextMessage("RELOAD"));
		}
		return application.getContextPath()+"/";
	}
}



