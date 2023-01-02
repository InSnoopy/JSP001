package kr.or.ddit.memo.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import kr.or.ddit.mybatis.MybatisUtils;
import kr.or.ddit.vo.MemoVO;

public class MemoDAOImpl implements MemoDAO {
	// 의존 관계 형성
	private SqlSessionFactory sqlSessionFactory = MybatisUtils.getSqlSessionFactory();
	
	@Override
	public List<MemoVO> selectMemoList() {
		// try()로 자동 닫기
		try(
			SqlSession sqlSession = sqlSessionFactory.openSession();
		){
			MemoDAO mapperProxy = sqlSession.getMapper(MemoDAO.class);
			return mapperProxy.selectMemoList();
//			return sqlSession.selectList("kr.or.ddit.memo.dao.MemoDAO.selectMemoList");
		}
	}

	@Override
	public int insertMemo(MemoVO memo) {
		try(
			SqlSession sqlSession = sqlSessionFactory.openSession();
		){
			// 구현체 생성 - 아래 방법으로 할 경우 insertMemo에 파라미터로 무조건 넘겨서 안넘기면 error가 생기도록 한다.
			MemoDAO mapperProxy = sqlSession.getMapper(MemoDAO.class);
			int rowcnt = mapperProxy.insertMemo(memo);
//			int rowcnt = sqlSession.insert("kr.or.ddit.memo.dao.MemoDAO.insertMemo", memo);
			sqlSession.commit();
			return rowcnt;
		}
	}

	@Override
	public int updateMemo(MemoVO memo) {
		try(
			SqlSession sqlSession = sqlSessionFactory.openSession();
		){
			MemoDAO mapperProxy = sqlSession.getMapper(MemoDAO.class);
			int rowcnt = mapperProxy.updateMemo(memo);
//			int rowcnt = sqlSession.insert("kr.or.ddit.memo.dao.MemoDAO.updateMemo", memo);
			sqlSession.commit();
			return rowcnt;
		}
	}

	@Override
	public int deleteMemo(int code) {
		try(
			SqlSession sqlSession = sqlSessionFactory.openSession();
		){
			MemoDAO mapperProxy = sqlSession.getMapper(MemoDAO.class);
			int rowcnt = mapperProxy.deleteMemo(code);
//			int rowcnt = sqlSession.insert("kr.or.ddit.memo.dao.MemoDAO.deleteMemo", code);
			sqlSession.commit();
			return rowcnt;
		}
	}

}
