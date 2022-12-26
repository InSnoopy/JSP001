package kr.or.ddit.libraray;

import org.apache.commons.text.CaseUtils;
import org.junit.Test;

public class CommonsTest {
	@Test
	public void testText() {
		String snake = "MEM_ID";
		// 카멜 표기법으로 변경해주는 메서드 (라이브러리 사용)
		System.out.println(CaseUtils.toCamelCase(snake, true, '_'));
	}
}
