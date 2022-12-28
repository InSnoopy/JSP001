package kr.or.ddit.member.service;

import java.util.List;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.exception.UserNotFoundException;
import kr.or.ddit.login.service.AuthenticateService;
import kr.or.ddit.login.service.AuthenticateServiceImpl;
import kr.or.ddit.member.dao.MemberDAO;
import kr.or.ddit.member.dao.MemberDAOImpl;
import kr.or.ddit.vo.MemberVO;

public class MemberServiceImpl implements MemberService {
	// 결합력 최상
	private MemberDAO memberDAO = new MemberDAOImpl();
	private AuthenticateService authService = new AuthenticateServiceImpl();
	@Override
	public ServiceResult createMember(MemberVO member) {
		// 아이디 중복으로 인한 실패(PKDUPLICATED), 가입 성공(OK), 가입 실패(FAIL)
		ServiceResult result = null;
		try{
			retriveMember(member.getMemId());
			result = ServiceResult.PKDUPLICATED;
		}catch (UserNotFoundException e) {
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
	public List<MemberVO> retriveMemberList() {
		List<MemberVO> list = memberDAO.selectMemberList();
		return list;
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
		// 존재 부(NOTEXIST), 비번 인증 실패(INVALIDPASSWORD), 성공(OK), 실패(FAIL)
		ServiceResult result = null;
		
		String memPass = member.getMemPass();
		MemberVO member2 = retriveMember(member.getMemId());
		
		if(!(memPass.equals(member2.getMemPass()))) {
			result = ServiceResult.INVALIDPASSWORD;
		}else if(memPass.equals(member2.getMemPass())){
			int cnt = memberDAO.deleteMember(member.getMemId());
			if(cnt>0) {					
				result = ServiceResult.OK;				
			}else {
				result = ServiceResult.FAIL;					
			}
		}else {								
			result = ServiceResult.PKDUPLICATED;
		}
		
		return result;
	}

}
