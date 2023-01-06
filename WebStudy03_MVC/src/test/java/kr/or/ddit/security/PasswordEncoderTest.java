package kr.or.ddit.security;

import org.junit.Test;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PasswordEncoderTest {
	PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
	String password = "java";
	String mem_pass = "{bcrypt}$2a$10$9kmz9TcY4ode714mP5/3Zu/9QHruTwa.CQ3SEN27yIiywnZ1KT00O";
	
	@Test
	public void encodeTest() {
		String encoded = encoder.encode(password);
		log.info("mem_Pass : {}", encoded);
		// 단방향 -> java를 암호화해서 비교
	}
	
	@Test
	public void matchTest() {
		// 아래는 오류가 난다. 시간에 따라서 다르게 만들어 준다.
//		String encoded = encoder.encode(password);
//		log.info("match result : {}", encoded.equals(mem_pass));
		
		// matches로 인증해야 true나온다.
		log.info("match result : {}", encoder.matches(password, mem_pass));
	}
}
