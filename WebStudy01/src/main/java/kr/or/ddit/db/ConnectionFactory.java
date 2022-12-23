package kr.or.ddit.db;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;


/**
 * Factory Object[Method] Pattern
 * 	: 필요 객체의 생성을 전담하는 객체를 별도 운영하는 구조.
 *
 */
public class ConnectionFactory {
	private static String url;
	private static String user;
	private static String password;
	
	// 폴링 만들기?
	private static DataSource ds;
	
	// 어플리케이션 전체 통틀어서 한번만 실행
	// 이걸 통해 메소드를 호출 가능
	static {
		String path = "/kr/or/ddit/db/dbinfo.properties";
		
		try(
			InputStream is = ConnectionFactory.class.getResourceAsStream(path);
		) {
			Properties dbInfo = new Properties();
			dbInfo.load(is);
			
			url = dbInfo.getProperty("url");
			user = dbInfo.getProperty("user");
			password = dbInfo.getProperty("password");
			
//			Class.forName(dbInfo.getProperty("driverClassName"));
			// 폴링
			// 얘가 칼국수 면을 뽑을 주방장이다. 커넥션을 생성할 수 있어야한다. 이 안에서 커넥션 폴링이 일어나고 있다.
			// 데이터 소스의 구현체가 bds다.
			
			BasicDataSource bds = new BasicDataSource();
			bds.setDriverClassName(dbInfo.getProperty("driverClassName"));
			// 커넥션을 생성하고 있다.
			bds.setUrl(url);
			bds.setUsername(user);
			bds.setPassword(password);
			
			// 아래가 폴링 정책이다. 
			// 이걸 하드코딩하지말고 dbinfo.properties에 작성해 놓자.
			// 맨 처음에 커넥션을 5개 만들어 놓는다는 뜻
			bds.setInitialSize(Integer.parseInt(dbInfo.getProperty("initialSize")) );
			// 5개가 반납이 안될 경우 여유분 5개를 만들어 놓는다는 뜻
			bds.setMaxTotal(Integer.parseInt(dbInfo.getProperty("maxIdle")));
			// 10개까지 만들었는데 11개가 왔다면? 2초동안 일단 기다려서 반납오길 기다리라는 뜻
			// 기다려도 안나온다? 그러면 sqlException을 발생해라
			bds.setMaxWaitMillis(Long.parseLong(dbInfo.getProperty("maxTotal")));
			// 최대 5개까지만 놀수 있다. 10개까지 늘어났을 경우 나머지 5개를 죽인다.
			// setInitialSize = setMaxIdle 두개는 똑같해야 한다.
			bds.setMaxIdle(Integer.parseInt(dbInfo.getProperty("maxWait")));
			
			ds = bds;
			
		} catch (Exception e1) {
			throw new RuntimeException(e1);
		}		
	}
	public static Connection getConnection() throws SQLException{
//		Connection conn = DriverManager.getConnection(url, user, password);
		return ds.getConnection();
	}
}
