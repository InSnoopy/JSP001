package kr.or.ddit.servlet02;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("*.tmpl")
public class TmplServlet extends HttpServlet{
	private ServletContext application;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		application = getServletContext();
	}
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		
		StringBuffer tmplSrc = readTemplate(req, resp);
		
		if(tmplSrc==null) {
			System.err.println("템플릿 소스를 읽지 못했음.");
			return;
		}
		
//		Map<String, Object> scope = new HashMap<>();
//		scope.put("now", new Date());
		
		String html = evaluateVariables(tmplSrc, req);
		
		PrintWriter out = resp.getWriter();
		out.println(html);
	}

	private String evaluateVariables(StringBuffer tmplSrc, HttpServletRequest scope) {
		String evalPattern = "#([a-zA-Z0-9_]+)#";
		Pattern regex = Pattern.compile(evalPattern);
		Matcher matcher = regex.matcher(tmplSrc);
		StringBuffer finalHtml = new StringBuffer();
		while(matcher.find()) {
			String varName = matcher.group(1); // now
			Object value = scope.getAttribute(varName);
			matcher.appendReplacement(finalHtml, Objects.toString(value, "")); // Objects는 Object 업그레이드 버전 : toString() -> null이뜬다면 ""로 
		};
		// 마지막 남은 html 소스
		matcher.appendTail(finalHtml);
		return finalHtml.toString();
	}

	private StringBuffer readTemplate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		String tmplPath = req.getServletPath();
		String realPath = application.getRealPath(tmplPath);
		File tmplFile = new File(realPath);
		if(!tmplFile.exists()) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND, tmplPath+"를 찾을 수 없음.");
			return null;
		}
		
		FileReader reader = new FileReader(tmplFile);
		BufferedReader br = new BufferedReader(reader);
		String temp = null;
		StringBuffer tmplSrc = new StringBuffer();
		while((temp=br.readLine())!=null) {
			tmplSrc.append(temp+"\n");
		}
		return tmplSrc;
	}
}
