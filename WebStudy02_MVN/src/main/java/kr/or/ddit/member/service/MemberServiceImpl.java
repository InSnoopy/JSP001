package kr.or.ddit.member.service;

import java.util.List;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.exception.UserNotFoundException;
import kr.or.ddit.member.dao.MemberDAO;
import kr.or.ddit.member.dao.MemberDAOImpl;
import kr.or.ddit.vo.MemberVO;

public class MemberServiceImpl implements MemberService {
	// 결합력 최상
	private MemberDAO memberDAO = new MemberDAOImpl();

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

	@Override
	public ServiceResult modifyMember(MemberVO member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceResult removeMember(MemberVO member) {
		// TODO Auto-generated method stub
		return null;
	}

}
