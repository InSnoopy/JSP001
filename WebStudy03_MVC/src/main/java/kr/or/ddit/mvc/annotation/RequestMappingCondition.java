package kr.or.ddit.mvc.annotation;

import lombok.EqualsAndHashCode;
import lombok.Getter;
/**
 * immutable 객체 형태로 값을 변경하지 않음.
 * 기본 생성자도 없고 Getter뿐..
 */
@Getter
@EqualsAndHashCode // 나중에 key로 사용되는 객체임으로 이걸 꼭 써줘야한다.
public class RequestMappingCondition {
	private String url;
	private RequestMethod method;
	public RequestMappingCondition(String url, RequestMethod method) {
		super();
		this.url = url;
		this.method = method;
	}
	@Override
	public String toString() {
		return String.format("%s[%s]", url, method);
	}
	
}
