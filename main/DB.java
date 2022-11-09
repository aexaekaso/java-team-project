package cafe;

import java.io.BufferedReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DB {
	Connection CN = null; // 데이터 베이스와 연결을 위한 객체
	PreparedStatement PS = null; // sql 문을 데이터베이스에 보내기 위한 객체, ?로 처리
	Statement stmt = null; // sql 문을 데이터베이스에 보내기 위한 객체, 전체를 받을 때
	ResultSet RS = null; // sql 질의에 의해 생성된 테이블을 저장하는 객체
	BufferedReader br = null; // 입력받기 위한 객체

	String url = "jdbc:mysql://localhost:3306/cafedb?characterEncoding=UTF-8&serverTimezone=UTC";
	String user = "test"; // 사용자
	String password = "1234"; // 암호
	String driver = "com.mysql.cj.jdbc.Driver";
	String[] mysqlCode = new String[3]; //메뉴에 따른 SQL문 저장리스트

	// 기본생성자
	public DB() {

	}
	
	//데이터베이스 연결
	public void connectDB() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/cafedb?characterEndofing=UTF-8&serverTimezone=UTC";
			CN = DriverManager.getConnection(url, "test", "1234");
//			System.out.println("*** 데이터베이스 연결 성공 ***");  //나중에 제거
			
		} catch(ClassNotFoundException e) {
			System.out.println("JDBC Driver 로드 실패");
		} catch(SQLException e) {
			System.out.println("데이터베이스 연결 실패");
		} catch (Exception e) {
			System.out.println("에러 발생");
		}
	}
	
	
}