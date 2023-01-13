package kr.or.ddit.vo;

import java.io.Serializable;
// Serializable가 없으면 직렬화가 안된다.
// 직렬화 가능한 객체를 직렬화 하려면 그 객체가 가지고 있다는 프로퍼티도 직렬화가 가능해야 한다.
public class MemoVO implements Serializable{
	
//	// Object Type은 직렬화가 안된다. 이런 경우 MemoVO객체가 직렬화가 안된다.
//	// transient를 붙이면 직렬화가 안되는 객체가 들어있어도 직렬화가 가능하다.
//	// 민감한 데이터 같은 경우 이렇게 한다. 직렬화 할 경우 보호가 필요한 데이터
//	private transient Object prop; // transient는 투명하다라는 뜻
//	public Object getProp() {
//		return prop;
//	}
//	public void setProp(Object prop) {
//		this.prop = prop;
//	}
	
	private Integer code;
	private String writer;
	private String content;
	private String date;
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	@Override
	public String toString() {
		return "MemoVO [code=" + code + ", writer=" + writer + ", content=" + content + ", date=" + date + "]";
	}
	
}
