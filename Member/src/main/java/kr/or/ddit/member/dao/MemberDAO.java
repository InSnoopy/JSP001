package kr.or.ddit.member.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.ddit.member.vo.MemberVO;
import kr.or.ddit.vo.PagingVO;

/**
 * 회원관리(CRUD)를 위한 Persistence Layer
 */
@Mapper
public interface MemberDAO {

	/**
	 * 회원 전체 수(?)
	 * @param pagingVO
	 * @return
	 */
	public int selectTotalRecord(PagingVO<MemberVO> pagingVO);
	
	/**
	 * 회원 신규 등록
	 * @param member
	 * @return rowcnt > 0 : 성공, rowcnt <= 0 : 실패
	 */
	public int insertMember(MemberVO member);
	
	/**
	 * 회원 목록 조회
	 * @param pagingVO
	 * @return size == 0 인 경우, 조건에 맞는 레코드 없음.
	 */
	public List<MemberVO> selectMemberList(PagingVO<MemberVO> pagingVO);
	
	/**
	 * 회원 상세 조회
	 * @param memId
	 * @return 조건에 맞는 레코드 없는 경우, null 반환
	 */
	public MemberVO selectMember(@Param("memId") String memId);
	
	/**
	 * 회원 정보 수정
	 * @param member
	 * @return rowcnt > 0 : 성공, rowcnt <= 0 : 실패
	 */
	public int updateMember(MemberVO member);
	
	/**
	 * 회원 정보 삭제
	 * @param memId
	 * @return  rowcnt > 0 : 성공, rowcnt <= 0 : 실패
	 */
	public int deleteMember(@Param("memId") String memId);
}
