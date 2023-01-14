package kr.or.ddit.member.service;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.member.dao.MemberDAO;
import kr.or.ddit.member.vo.MemberVO;
import kr.or.ddit.vo.PagingVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MemberServiceImpl implements MemberService {
	
	@Inject
	private MemberDAO memberDAO;
	
	
	@PostConstruct
	public void init() {
		log.info("주입된 객체 : {}", memberDAO);
	}
	
	@Override
	public ServiceResult createMember(MemberVO member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MemberVO> retrieveMemberList(PagingVO<MemberVO> pagingVO) {
		pagingVO.setTotalRecord(memberDAO.selectTotalRecord(pagingVO));
		
		List<MemberVO> memberList = memberDAO.selectMemberList(pagingVO);
		
		return memberList;
	}

	@Override
	public MemberVO retrieveMember(String memId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceResult modeifyMember(MemberVO member) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceResult removeMember(MemberVO member) {
		// TODO Auto-generated method stub
		return null;
	}

}
