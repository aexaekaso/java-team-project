package cafeProject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Jdbc {
	Connection con = null; // 데이터 베이스와 연결을 위한 객체
	ResultSet rs = null; // sql 질의에 의해 생성된 테이블을 저장하는 객체
	PreparedStatement pstmt = null; // sql 문을 데이터베이스에 보내기 위한 객체, ?로 처리
	Statement stmt = null; // sql 문을 데이터베이스에 보내기 위한 객체, 전체를 받을 때
	BufferedReader br = null; // 입력받기 위한 객체
	
	String url = "jdbc:mysql://localhost:3306/cafedb?characterEncoding=UTF-8&serverTimezone=UTC";
	String user = "root"; // 사용자
	String password = "1234"; // 암호
	String driver = "com.mysql.cj.jdbc.Driver";

	// 기본생성자
	public Jdbc() {

	}

	// 메서드
	public void coffeeAllSelect() {
		// Jdbc 객체 생성
		Jdbc jdbc = new Jdbc();

		// 변수 선언
		String sql = "SELECT * FROM coffee"; // 커피

		try {
			// jdbc 드라이버 등록
			Class.forName(jdbc.driver);

			// db 연결
			jdbc.con = DriverManager.getConnection(jdbc.url, jdbc.user, jdbc.password);

			System.out.println("연결 성공"); // 확인용, 추후 삭제

			// statement 객체 생성
			jdbc.stmt = jdbc.con.createStatement();

			// sql 문장을 실행하고 결과를 리턴
			jdbc.rs = jdbc.stmt.executeQuery(sql);

			System.out.println("=========커피===========");
			System.out.println("코드\t종류\t가격");
			while (jdbc.rs.next()) {
				int ccode = jdbc.rs.getInt("ccode");
				String cname = jdbc.rs.getString("cname");
				int cprice = jdbc.rs.getInt("cprice");

				System.out.printf("%d\t%s\t%d\n\n", ccode, cname, cprice);
			}

		} catch (ClassNotFoundException e) { // getConnection(url, user, password);
			e.printStackTrace(); // 프로그램이 완료된 후에 반드시 제거 또는 주석
		} catch (SQLException e) {
			e.printStackTrace(); // 프로그램이 완료된 후에 반드시 제거 또는 주석
		} catch (Exception e) {
			e.printStackTrace(); // 프로그램이 완료된 후에 반드시 제거 또는 주석
		} finally { // 무조건 실행되는 코드
			// 연결이 되어 있으면 연결 끊기

			// 사용순서 반대로 닫기
			if (jdbc.rs != null) {
				try {
					jdbc.rs.close();
				} catch (SQLException e) {
					e.printStackTrace(); // 프로그램이 완료된 후에 반드시 제거 또는 주석
				}
			} // if

			if (jdbc.stmt != null) {
				try {
					jdbc.stmt.close();
				} catch (SQLException e) {
					e.printStackTrace(); // 프로그램이 완료된 후에 반드시 제거 또는 주석
				}
			} // if

			if (jdbc.con != null) {
				try {
					jdbc.con.close();
				} catch (SQLException e) {
					e.printStackTrace(); // 프로그램이 완료된 후에 반드시 제거 또는 주석
				}
			}

		} // try

	}// coffeeAllSelect

	public void select() {
		// Jdbc 객체 생성
		Jdbc jdbc = new Jdbc();

		//bufferedReader
		jdbc.br = new BufferedReader(new InputStreamReader(System.in));
		
		// 변수 선언
		String sql = "SELECT * FROM coffee WHERE ccode = ?"; // sql문
		int input2 = 0;
		
		try {
			// jdbc 드라이버 등록
			Class.forName(jdbc.driver);

			// db 연결
			jdbc.con = DriverManager.getConnection(jdbc.url, jdbc.user, jdbc.password);

			System.out.println("연결 성공"); // 확인용, 추후 삭제

			// prepareStatement 객체 생성
			jdbc.pstmt = jdbc.con.prepareStatement(sql);
			
			//검색할 것 입력
			System.out.println("코드 입력>>>");
			input2 = Integer.parseInt(jdbc.br.readLine());
			
			jdbc.pstmt.setInt(1, input2);
			
			// sql 문장을 실행하고 결과를 리턴
			jdbc.rs = jdbc.pstmt.executeQuery();

			System.out.println("코드\t종류\t가격");
			while (jdbc.rs.next()) {
				int ccode = jdbc.rs.getInt("ccode");
				String cname = jdbc.rs.getString("cname");
				int cprice = jdbc.rs.getInt("cprice");

				System.out.printf("%d\t%s\t%d\n\n", ccode, cname, cprice);
			}

		} catch (ClassNotFoundException e) { // getConnection(url, user, password);
			e.printStackTrace(); // 프로그램이 완료된 후에 반드시 제거 또는 주석
		} catch (SQLException e) {
			e.printStackTrace(); // 프로그램이 완료된 후에 반드시 제거 또는 주석
		} catch (Exception e) {
			e.printStackTrace(); // 프로그램이 완료된 후에 반드시 제거 또는 주석
		} finally { // 무조건 실행되는 코드
			// 연결이 되어 있으면 연결 끊기

			// 사용순서 반대로 닫기
			if (jdbc.rs != null) {
				try {
					jdbc.rs.close();
				} catch (SQLException e) {
					e.printStackTrace(); // 프로그램이 완료된 후에 반드시 제거 또는 주석
				}
			} // if

			if (jdbc.pstmt != null) {
				try {
					jdbc.pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(); // 프로그램이 완료된 후에 반드시 제거 또는 주석
				}
			} // if

			if (jdbc.con != null) {
				try {
					jdbc.con.close();
				} catch (SQLException e) {
					e.printStackTrace(); // 프로그램이 완료된 후에 반드시 제거 또는 주석
				}
			}

		} // try

	}//select()
	
	public void delete() {
		// Jdbc 객체 생성
		Jdbc jdbc = new Jdbc();

		//bufferedReader
		jdbc.br = new BufferedReader(new InputStreamReader(System.in));
		
		// 변수 선언
		String sql = "DELETE FROM coffee WHERE ccode = ?"; // sql문
		int rows= 0; //삭제된 행의 수
		int input2 = 0;
		
		try {
			// jdbc 드라이버 등록
			Class.forName(jdbc.driver);

			// db 연결
			jdbc.con = DriverManager.getConnection(jdbc.url, jdbc.user, jdbc.password);

			System.out.println("연결 성공"); // 확인용, 추후 삭제

			// prepareStatement 객체 생성
			jdbc.pstmt = jdbc.con.prepareStatement(sql);
			
			//검색할 것 입력
			System.out.println("삭제할 커피의 코드 입력>>>");
			input2 = Integer.parseInt(jdbc.br.readLine());
			
			jdbc.pstmt.setInt(1, input2);
			
			// sql 문장을 실행하고 결과를 리턴
			rows = jdbc.pstmt.executeUpdate();

			if(rows==0) {
				System.out.println("삭제할 값이 없습니다");
			}else {
				System.out.println("삭제 된 행의 수: " + rows);
			}//try.if

		} catch (ClassNotFoundException e) { // getConnection(url, user, password);
			e.printStackTrace(); // 프로그램이 완료된 후에 반드시 제거 또는 주석
		} catch (SQLException e) {
			e.printStackTrace(); // 프로그램이 완료된 후에 반드시 제거 또는 주석
		} catch (Exception e) {
			e.printStackTrace(); // 프로그램이 완료된 후에 반드시 제거 또는 주석
		} finally { // 무조건 실행되는 코드
			// 연결이 되어 있으면 연결 끊기

			// 사용순서 반대로 닫기
			if (jdbc.rs != null) {
				try {
					jdbc.rs.close();
				} catch (SQLException e) {
					e.printStackTrace(); // 프로그램이 완료된 후에 반드시 제거 또는 주석
				}
			} // if

			if (jdbc.pstmt != null) {
				try {
					jdbc.pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(); // 프로그램이 완료된 후에 반드시 제거 또는 주석
				}
			} // if

			if (jdbc.con != null) {
				try {
					jdbc.con.close();
				} catch (SQLException e) {
					e.printStackTrace(); // 프로그램이 완료된 후에 반드시 제거 또는 주석
				}
			}

		} // try
		
		
	}//select()
}
