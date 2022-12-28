package kr.or.ddit.object;

public class StringPlayground {
	public static void main(String[] args) {
		String str1 = "SAMPLE"; // 상수 메모리		
		String str2 = "SAMPLE"; // 상수 메모리
		String str3 = new String("SAMPLE"); // hip 메모리에 저장 - 주소값
		String str4 = new String(str1); // hip 메모리에 저장 - 주소값

//		1.번 방법
		str1 = str1 + "append"; // 이렇게 사용하지 말자 그 이유는? 
		// 1. str1은 그대로 있다.
		// 2. append 메모리 추가
		// 3. SAMPLE append 메모리 추가
		// 이렇게 3개가 다 상수 메모리에 저장
		
//		2.번 방법
		StringBuffer original = new StringBuffer("SAMPLE"); // hip 메모리에 생성
		original.append("append"); // 그 주소에 append만 추가 : hip메모리에 있는 값 하나에만 사용된다.
		
		System.out.printf("str1 == str2 : %b \n", str1 == str2); // true
		System.out.printf("str2 == str3 : %b \n", str2 == str3); // false
		System.out.printf("str3 == str4 : %b \n", str3 == str4); // false
		System.out.printf("str4 == str1 : %b \n", str4 == str1); // false
		System.out.printf("str4 == str2 : %b \n", str4.intern() == str1); // true : intern은 객체안에 숨겨진 string을 가져온다.
		System.out.printf("str4 == str3 : %b \n", str4.intern() == str1); // true
	}
}
