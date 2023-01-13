package kr.or.ddit.member.service;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.exception.UserNotFoundException;
import kr.or.ddit.login.service.AuthenticateService;
import kr.or.ddit.member.dao.MemberDAO;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.PagingVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MemberServiceImpl implements MemberService {
	// 결합력 최상
	@Inject
	private MemberDAO memberDAO;
	@Inject
	private AuthenticateService authService;
	@Inject
	private PasswordEncoder encoder;
	
	@PostConstruct
	public void init() {
		log.info("주입된 객체 : {}, {}, {}", memberDAO, authService, encoder);
	}
	
	@Override
	public ServiceResult createMember(MemberVO member) {
		// 아이디 중복으로 인한 실패(PKDUPLICATED), 가입 성공(OK), 가입 실패(FAIL)
		ServiceResult result = null;
		try{
			retriveMember(member.getMemId());
			result = ServiceResult.PKDUPLICATED;
		}catch (UserNotFoundException e) {
			String encoded = encoder.encode(member.getMemPass());
			member.setMemPass(encoded);
			int cnt = memberDAO.insertMember(member);
			if(cnt>0) {
				result = ServiceResult.OK;
			}else {
				result = ServiceResult.FAIL;
			}			
		}
		return result;
	}

	@Override
	public List<MemberVO> retriveMemberList(PagingVO<MemberVO> pagingVO) {
		pagingVO.setTotalRecord(memberDAO.selectTotalRecord(pagingVO));
		
		List<MemberVO> memberList = memberDAO.selectMemberList(pagingVO);
		pagingVO.setDataList(memberList);
		return memberList;
	}

	@Override
	public MemberVO retriveMember(String memId) {
		MemberVO member = memberDAO.selectMember(memId);
		if(member==null)
			throw new UserNotFoundException(String.format(memId+"에 해당하는 사용자 없음."));
		return member;
	}

   //회원 정보 수정    
   @Override
   public ServiceResult modifyMember(MemberVO member) {
      //안에 유지를 하되, id , password만 넘길 수 있어야해 
      MemberVO inputData = new MemberVO();
      inputData.setMemId(member.getMemId());
      inputData.setMemPass(member.getMemPass());
      
      //member와 분리 
      ServiceResult result = authService.authenticate(inputData);
         if(ServiceResult.OK.equals(result)) {
         int rowcnt = memberDAO.updateMember(member);
         result = rowcnt > 0 ? ServiceResult.OK : ServiceResult.FAIL;
      }
      return result;
   }

   @Override
   public ServiceResult removeMember(MemberVO member) {
      //있나없나 비번인증 통과후 삭제 처리 
      //인증과 관련된 코드를 따로 빼놔서 여기는 수정을 안해도됨. 
      ServiceResult result = authService.authenticate(member);
      
      if(ServiceResult.OK.equals(result)) {
         int rowcnt = memberDAO.deleteMember(member.getMemId());
         result = rowcnt > 0 ? ServiceResult.OK : ServiceResult.FAIL;
      }
      return result;
   }


}
