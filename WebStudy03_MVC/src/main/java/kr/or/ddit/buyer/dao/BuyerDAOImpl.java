package kr.or.ddit.buyer.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import kr.or.ddit.mybatis.MybatisUtils;
import kr.or.ddit.prod.dao.ProdDAO;
import kr.or.ddit.vo.BuyerVO;
import kr.or.ddit.vo.PagingVO;

public class BuyerDAOImpl implements BuyerDAO {

	private SqlSessionFactory sqlSessionFactory = MybatisUtils.getSqlSessionFactory();
	
	@Override
	public BuyerVO selectBuyer(String buyerId) {
		try (SqlSession sqlSession = sqlSessionFactory.openSession();) {
			BuyerDAO mapperProxy = sqlSession.getMapper(BuyerDAO.class);
			return mapperProxy.selectBuyer(buyerId);
		}
	}

	@Override
	public List<BuyerVO> selectBuyerList(PagingVO<BuyerVO> pagingVO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insertBuyer(BuyerVO buyer) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateBuyer(BuyerVO buyer) {
		// TODO Auto-generated method stub
		return 0;
	}

}
