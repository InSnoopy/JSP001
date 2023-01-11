package kr.or.ddit.login.service;

import java.lang.reflect.InvocationTargetException;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.exception.UserNotFoundException;
import kr.or.ddit.member.dao.MemberDAO;
import kr.or.ddit.member.dao.MemberDAOImpl;
import kr.or.ddit.vo.MemberVO;

@Service
public class AuthenticateServiceImpl implements AuthenticateService {
	
	private MemberDAO memberDAO;
	@Inject
	public AuthenticateServiceImpl(MemberDAO memberDAO) {
		super();
		this.memberDAO = memberDAO;
	}

	// 얘는 강제로 풀어서 리플렉션으로 가져온다.
	// 이때는 빈이 등록되어 있어야한다.
	// 패스워드 인코드가 여러개 등록되어있어도 이렇게 특정해서 등록 가능.
	@Resource(name="passwordEncoder")
	private PasswordEncoder encoder;
	
	
	@Override
	public ServiceResult authenticate(MemberVO member) {
		MemberVO savedMember = memberDAO.selectMember(member.getMemId());
		// savedMember.getMemDelete()!=null 이걸 추가함으로써 delete에 1인 유저는 로그인을 못한다.
		if(savedMember==null || savedMember.isMemDelete()) // null거나 true -> boolean 타입이면 isMemDelete()로 비교해야한다.
			throw new UserNotFoundException(String.format("%s 사용자 없음.", member.getMemId()));
		String inputPass = member.getMemPass();
		String savedPass = savedMember.getMemPass();

		ServiceResult result = null;
		if(encoder.matches(inputPass, savedPass)) {
			// call by 레퍼런스
			//member.setMemName(savedMember.getMemName());
			try {
				BeanUtils.copyProperties(member, savedMember);
				result = ServiceResult.OK;
			} catch (IllegalAccessException | InvocationTargetException e) {
				throw new RuntimeException(e);
			}
		}else {
			result = ServiceResult.INVALIDPASSWORD;
		}		
		

		return result;
	}

}
