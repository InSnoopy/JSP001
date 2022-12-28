package kr.or.ddit.member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import kr.or.ddit.memo.dao.MemoDAO;
import kr.or.ddit.mybatis.MybatisUtils;
import kr.or.ddit.vo.MemberVO;

public class MemberDAOImpl implements MemberDAO {
	
	private SqlSessionFactory sqlSessionFactory = MybatisUtils.getSqlSessionFactory();

	@Override
	public int insertMember(MemberVO member) {
		
		try (SqlSession sqlSession = sqlSessionFactory.openSession();) {
			MemberDAO mapperProxy = sqlSession.getMapper(MemberDAO.class);
			int cnt = mapperProxy.insertMember(member);
			sqlSession.commit();
			return cnt;
		}

	}
	

	@Override
	public List<MemberVO> selectMemberList() {

		try (SqlSession sqlSession = sqlSessionFactory.openSession();) {
			MemberDAO mapperProxy = sqlSession.getMapper(MemberDAO.class);
			return mapperProxy.selectMemberList();
		}
	}

	@Override
	public MemberVO selectMember(String memId) {
		
		try (SqlSession sqlSession = sqlSessionFactory.openSession();) {
			MemberDAO mapperProxy = sqlSession.getMapper(MemberDAO.class);
			return mapperProxy.selectMember(memId);
		}

	}

	@Override
	public int updateMember(MemberVO member) {
			// 트랜잭션 시작
		try (SqlSession sqlSession = sqlSessionFactory.openSession();) {
			MemberDAO mapperProxy = sqlSession.getMapper(MemberDAO.class);
			int cnt = mapperProxy.updateMember(member);
			// 트랜잭션 종료
			sqlSession.commit();
			return cnt;
		}
	}

	@Override
	public int deleteMember(String memId) {
		try (SqlSession sqlSession = sqlSessionFactory.openSession();) {
			MemberDAO mapperProxy = sqlSession.getMapper(MemberDAO.class);
			int cnt = mapperProxy.deleteMember(memId);
			sqlSession.commit();
			return cnt;
		}
	}

}
