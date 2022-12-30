package kr.or.ddit.prod.dao;

import org.apache.ibatis.annotations.Param;

import kr.or.ddit.vo.ProdVO;

public interface ProdDAO {
	/**
	 * @param prodId
	 * @return 존재하지 않으면, null 반환
	 */
	public ProdVO selectProd(@Param("prodId") String prodId);
}
