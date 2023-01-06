package kr.or.ddit.buyer.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.or.ddit.vo.BuyerVO;
import kr.or.ddit.vo.PagingVO;

public interface BuyerDAO {

	/**
	 * @param buyerId
	 * @return 존재하지 않으면, null 반환
	 */
	public BuyerVO selectBuyer(@Param("buyerId") String buyerId);
		
	/**
	 * 검색 조건과 현재 페이지에 맞는 거래처 목록 조회
	 * @param pagingVO
	 * @return
	 */
	public List<BuyerVO> selectBuyerList(PagingVO<BuyerVO> pagingVO);
	
	/**
	 * 신규 거래처 등록
	 * @param 거래처
	 * @return 등록된 거래처 수
	 */
	public int insertBuyer(BuyerVO buyer);
	
	/**
	 * buyer 수정
	 * @param 거래처
	 * @return 수정된 거래처 수
	 */
	public int updateBuyer(BuyerVO buyer);
}
