package kr.or.ddit.buyer.service;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.vo.BuyerVO;
import kr.or.ddit.vo.PagingVO;

public interface BuyerService {
	/**
	 * @param buyerId
	 * @return 존재하지 않을 경우, RuntimeException 발생.
	 */
	public BuyerVO retriveBuyer(String buyerId);
	
	/**
	 * call by reference 구조에 따라 totalRecord와 dataList를 pagingVO에 넣어줌
	 * @param pagingVO
	 */
	public void retrieveBuyerList(PagingVO<BuyerVO> pagingVO);
	
	/**
	 * 거래처 등록
	 * @param buyer
	 * @return 성공(OK), 실패(FAIL)
	 */
	public ServiceResult createBuyer(BuyerVO buyer);
	
	/**
	 * 거래처 수정
	 * @param buyer
	 * @return 존재하지 않는 경우, RuntimeException 발생, 성공(OK), 실패(FAIL)
	 */
	public ServiceResult modifyBuyer(BuyerVO buyer);
	
	
}
