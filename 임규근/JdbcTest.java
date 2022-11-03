package cafeProject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcTest {

	public static void main(String[] args) {
		Connection conn = null;
		String url = "jdbc:mysql://localhost:3306/jdbctest?characterEncoding=UTF-8&serverTimezone=UTC";
		String user = "test"; // 사용자
		String password = "1234"; // 암호

		/*
		 * jdbc: mysql: -> jdbc 사용하는 dbms가 mysql이다 localhost: ip주소(127.0.0.1), 내 컴퓨터,
		 * 원격으로 연결할 때: ip주소 쓰기 3306: 포트번호, 들어오는 입구 jdbctest: 데이터베이스명
		 */

		try {
			// jdbc 드라이버 등록, 메모리에 로딩, Build Path에서 찾고, 메모리로 로딩(읽어들이기)
			Class.forName("com.mysql.cj.jdbc.Driver");

			// db 연결하기
//			conn = DriverManager.getConnection(
//					"jdbc:mysql://localhost:3306/jdbctest", "test", "1234");
			conn = DriverManager.getConnection(url, user, password);

			System.out.println("연결 성공");
		} catch (ClassNotFoundException e) { // getConnection(url, user, password);
			e.printStackTrace(); // 프로그램이 완료된 후에 반드시 제거 또는 주석
		} catch (SQLException e) {
			e.printStackTrace(); // 프로그램이 완료된 후에 반드시 제거 또는 주석
		} catch (Exception e) {
			e.printStackTrace(); // 프로그램이 완료된 후에 반드시 제거 또는 주석
		} finally { // 무조건 실행되는 코드
			// 연결이 되어 있으면 연결 끊기
			if (conn != null) {
				try {
					// 연결 끊기
					conn.close();
					System.out.println("연결 끊기");
				} catch (SQLException e) {

				} // try.if.try
			} // try.if
		} // try

	}

}
