package kr.or.ddit.exception;

import java.io.IOException;
import java.sql.SQLException;

/**
 * 에러나 예외 (Throwable): 예상하지 못했던 비정상적인 처리 상황.
 * 		- Error : 개발자가 직접 처리하지 않는 에러 계열.
 * 		- Exception : 개발자가 직접 처리할 수 있는 예외 계열.
 * 			- checked exception (Exception) : 반드시 처리해야만 하는 예외.
 * 				ex) IOException, SQLException
 * 			- unchecked exception (RuntimeException) : 처리하지 않더라도 메소드 호출구조를 통해 JVM에게 제어권이 전달되는 예외. + 돌려봐야 예상이 가능한 예외.
 * 				ex) NullPointerException, IndexOutOfBoundException
 *
 *	** 예외 처리 방법
 *	직접처리(능동처리) : try(closable object)~catch~finally
 *	위임처리(수동처리) : 메소드의 호출자에게 throws로 예외 제어권 위임.
 *
 *	** 커스텀 예외 사용 방법
 *	1. Exception이나 RuntimeException의 자식 클래스 정의(예외 클래스)
 *	2. throw 예외 인스턴스 생성
 */
public class ExceptionDesc {
	// 버츄얼머신은 main 호출자다.
	public static void main(String[] args){ // throws IOException  2. 이 오류는 버츄얼 머신으로 떠 넘긴다.
//		try {
//			String data = getSampleData();
//			System.out.println(data);
//		}catch (IOException e) {
//			// System.err.println(e.getMessage()); 
//			e.printStackTrace(); // Stack을 추적한다. 어떤 예외가 발생했다. 그게 어디서 발생했는지에 정보를 보내준다.
//		}
		
		try {			
			System.out.println(getSampleDataWithRE());
		}catch (NullPointerException | UserNotFoundException e) { // 멀티  catch문 버츄열 머신으로도 보내기 싫다면 여기서 처리하고 싶다면
			System.err.println(e.getMessage());
			System.out.println(" 정상 처리 위장 가능");
		}
		
//		System.out.println(getSampleChangeException());
	}
	
	private static String getSampleData() throws IOException{ // 1. 나한테 발생한 오류를 호출자에게 떠 넘긴다.
		String data = "SAMPLE";
		if(1==1)
			throw new IOException("강제 발생 예외"); // checked
		return data;
	}
	
	private static String getSampleDataWithRE(){ // throws RuntimeException가 생략되어있다. 
		String data = "SAMPLERE";
		if(1==1)
			// throw new NullPointerException("강제 발생 예외"); // unchecked -> thorws가 없어도 있는것처럼 사용한다.
			throw new UserNotFoundException("강제 발생 예외");
		return data;
	}
	
	private static String getSampleChangeException() {
		String data = "SAMPLChangeException";
		try {
			if(1==1)
				throw new SQLException("강제 발생 예외");
			return data;
		} catch (SQLException e) {
			throw new RuntimeException(e); // 이걸 던지는 순간 throws가 있는것 처럼 실행된다. 
			// 마지막에 e를 붙임으로써 연관있는 예외를 보내준다.
		}
	}
}
