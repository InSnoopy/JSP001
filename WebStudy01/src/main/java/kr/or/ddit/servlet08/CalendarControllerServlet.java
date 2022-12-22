package kr.or.ddit.servlet08;

import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

import static java.util.Calendar.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/10/calendar.do")
public class CalendarControllerServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 1. 요청 분석 (검증 필수)
		// 검증을 통과하지 못했을 때 나오는 에러 코드는 대부분 400코드
		
		String yearParam = req.getParameter("year");
		String monthParam = req.getParameter("month");
		String language = req.getParameter("language");
		
		Locale clientLocale = req.getLocale();
		if(language!=null && !language.isEmpty()) {
			clientLocale = Locale.forLanguageTag(language); // 선택한 언어가 있다면 이 언어로 수정하는 메서드;
		};
		
		Calendar calendar =  Calendar.getInstance(); // 현재 날짜에 해당되는 캘린더 객체를 가져온다.
		
		// 방법 1
		// NumberFormatException (uncheckd) -> 밑에 파싱이 안된다면 나오는 에러
		// 예외 처리도 하나의 조건문처럼 사용 가능하다.
		try {
			if(yearParam!=null && monthParam!=null) { // parameter가 null이 아니라면 파싱해라				
				int year = Integer.parseInt(yearParam);
				int month = Integer.parseInt(monthParam);	
				calendar.set(YEAR, year);
				calendar.set(MONTH, month);
			}
			
			// req.setAttribute("calendar", calendar); // request, page, application, session 여기서 해당되는 영역을 확실히 선택하는게 좋다.
			req.setAttribute("calendar", new CalendarWrapper(calendar, clientLocale)); // 위에 요리가 마음에 안들어서 요리를 더 잘 만들기 위함 Wrapper(어뎁터)로 재료를 다시 넘긴다. 
			
			String viewName = "/WEB-INF/views/calendar.jsp";
			req.getRequestDispatcher(viewName).forward(req, resp); // 최소한의 영역을 선택해야한다.
			
		}catch(NumberFormatException e) {
			// 이렇게 설정하지 않으면 그냥 500으로 상태 코드가 나온다. (서버잘못이 아니라 클라이언트 잘못이기에 400)
			resp.sendError(400, e.getMessage()); // 2번쨰 파라미터에는 정확히 어떤 에러인지 메세지를 보낼 수 있다.
			return;
		}
		
		// 방법 2
//		if(req.getParameter("year") != null && !yearParam.matches("\\d{4}")) { // matches()로 마지막에 정규식 표현법으로 숫자4글자가 아니라면!
//			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
//			return;
//		}
		
		
		
	}
}
