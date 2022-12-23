package kr.or.ddit.servlet09.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.db.ConnectionFactory;
import kr.or.ddit.vo.DataBasePropertyVO;

public class DataBasePropertyDAOImpl implements DataBasePropertyDAO {

	@Override
	public List<DataBasePropertyVO> selectPropertyList(String propertyName) {
		// driver class loading : oracle.jdbc.driver.OracleDriver
		StringBuffer sql = new StringBuffer();
		sql.append(" select PROPERTY_NAME, PROPERTY_VALUE, description ");
		sql.append(" from database_properties ");
		if(StringUtils.isNotBlank(propertyName)) { // *
			// asd' OR '1'='1 로 입력할 경우.. 해커..
			sql.append(" WHERE PROPERTY_NAME = ? "); // ?가 값 자체다. 그렇기 떄문에 앞뒤에 연산자를 안 넣는다.
		}
		
		try (
			Connection conn = ConnectionFactory.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString()); // 먼저 sql의 쿼리를 달라고 한다. 이후에 변경을 못하게 하기 위해..
			// 실행중에 쿼리를 변경할 수 없다.
//			Statement stmt = conn.createStatement(); // 5번째
		) {

//			StringBuffer sql = new StringBuffer();
//			sql.append(" select PROPERTY_NAME, PROPERTY_VALUE, description ");
//			sql.append(" from database_properties ");
//			if(StringUtils.isNotBlank(PropertyName)) { // *
//				// asd' OR '1'='1 로 입력할 경우.. 해커..
//				sql.append(" WHERE PROPERTY_NAME = '"+PropertyName+"' ");
//			}
//			
//			ResultSet rs = stmt.executeQuery(sql.toString());
			if(StringUtils.isNotBlank(propertyName)) { // *
				pstmt.setString(1, propertyName); // 1부터 위에 ? 의 타입도 정한다.
			}
			
			ResultSet rs = pstmt.executeQuery(); 
			List<DataBasePropertyVO> list = new ArrayList<>();

			// Resultset 결과 얻기
			while (rs.next()) {
				DataBasePropertyVO vo = new DataBasePropertyVO();
				list.add(vo);

				vo.setPropertyName(rs.getString("PROPERTY_NAME"));
				vo.setPropertyValue(rs.getString("PROPERTY_VALUE"));
				vo.setPropertyName(rs.getString("DESCRIPTION"));

			}
			return list;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
