package kr.or.ddit.commons.wrapper;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class CookieHttpServletRequestWrapper extends HttpServletRequestWrapper{

	private Map<String, Cookie> cookieMap;
	// 어뎁터는 어뎁티가 가지고 있지 않은 인터페이스를 추가한다.
	public CookieHttpServletRequestWrapper(HttpServletRequest request) {
		super(request);
		cookieMap = new HashMap<>();
		Cookie[] cookies =  request.getCookies();
		if(cookies!=null){
			for(Cookie tmp : cookies){
				cookieMap.put(tmp.getName(), tmp);
			}			
		}
	}
	
	public Cookie getCookie(String name) {
		return cookieMap.get(name);
	}
	
	public String getCookieValue(String name) {
		Cookie finded = getCookie(name);
		// 없으면 null값 반환
		return Optional.ofNullable(finded)
				.map(cookie->{
					try {
						return URLDecoder.decode(cookie.getValue(), "UTF-8");
					} catch (UnsupportedEncodingException e) {
						// 언으로 변경해야한다? 에러종류를?
						throw new RuntimeException(e);
					}
					
				}) // 여기에서 디코딩을 해줘야한다.
				.orElse(null);
	}

}
