package kr.or.ddit.vo;

import java.security.Principal;

// MemberVO를 건들지 않고 Principal을 implements를 하기 위함
public class MemberVOWrapper implements Principal{
	private MemberVO realMember;

	public MemberVOWrapper(MemberVO realMember) {
		super();
		this.realMember = realMember;
	}
	
	public MemberVO getRealMember() {
		return realMember;
	}

	@Override
	public String getName() {
		return realMember.getMemId();
	}
}
