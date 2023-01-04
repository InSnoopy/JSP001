package kr.or.ddit.prod.service;

import java.util.List;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ProdVO;

public interface ProdService {
	/**
	 * @param prodId
	 * @return 존재하지 않은 경우, RuntimeException 발생.
	 */
	public ProdVO retriveProd(String prodId);
	
	/**
	 * call by reference 구조에 따라 totalRecord와 dataList를 pagingVO에 넣어줌
	 * @param pagingVO
	 */
	public void retrieveProdList(PagingVO<ProdVO> pagingVO);
	
	// PROD_ID는 개발자가 만들기 때문에 중복될 일이 없다.
	/**
	 * 상품 등록
	 * @param prod
	 * @return 성공(OK), 실패(FAIL)
	 */
	public ServiceResult createProd(ProdVO prod);
	
	/**
	 * 상품 수정
	 * @param prod
	 * @return 존재하지 않는 경우, RuntimeException 발생, 성공(OK), 실패(FAIL)
	 */
	public ServiceResult modifyProd(ProdVO prod);
}
