package kr.or.ddit.prod.service;

import java.util.List;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.prod.dao.ProdDAO;
import kr.or.ddit.prod.dao.ProdDAOImpl;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ProdVO;



public class ProdServiceImpl implements ProdService{
	private ProdDAO prodDAO = new ProdDAOImpl();

	@Override
	public ProdVO retriveProd(String prodId) {
		ProdVO prod = prodDAO.selectProd(prodId);
		if(prod==null)
			throw new RuntimeException(String.format("%s 는 없는 상품", prodId));
		return prod;
	}
	@Override
	// call by reference
	// parameter로 전달받은 pagingVO에 다 셋팅한다.
	public void retrieveProdList(PagingVO<ProdVO> pagingVO) {
		pagingVO.setTotalRecord(prodDAO.selectTotalRecord(pagingVO));
		
		List<ProdVO> prodList = prodDAO.selectProdList(pagingVO);
		pagingVO.setDataList(prodList);
	}
	@Override
	public ServiceResult createProd(ProdVO prod) {
		ServiceResult result = null;
		// 성공(OK), 실패(FAIL)
		int cnt = prodDAO.insertProd(prod);
		if(cnt>0) {
			result = ServiceResult.OK;
		}else {
			result = ServiceResult.FAIL;
		}
		return result;
	}
	@Override
	public ServiceResult modifyProd(ProdVO prod) {
		// 존재하지 않는 경우, RuntimeException 발생, 성공(OK), 실패(FAIL)
		retriveProd(prod.getProdId()); // 존재하지 않는 경우
		ServiceResult result = null;
		int cnt = prodDAO.updateProd(prod);
		if(cnt>0) {
			result = ServiceResult.OK;
		}else {
			result = ServiceResult.FAIL;
		}
		return result;
	}

}
