package cafeProject;

import java.io.BufferedReader;
import java.io.IOException;
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

	public void delete() {
		// Jdbc 객체 생성
		Jdbc jdbc = new Jdbc();

		// bufferedReader
		jdbc.br = new BufferedReader(new InputStreamReader(System.in));

		// 변수 선언
		String sql = "DELETE FROM coffee WHERE ccode = ?"; // sql문
		int rows = 0; // 삭제된 행의 수
		int input2 = 0;

		try {
			// jdbc 드라이버 등록
			Class.forName(jdbc.driver);

			// db 연결
			jdbc.con = DriverManager.getConnection(jdbc.url, jdbc.user, jdbc.password);

			System.out.println("연결 성공"); // 확인용, 추후 삭제

			// prepareStatement 객체 생성
			jdbc.pstmt = jdbc.con.prepareStatement(sql);

			// 검색할 것 입력
			System.out.println("삭제할 커피의 코드 입력>>>");
			input2 = Integer.parseInt(jdbc.br.readLine());

			jdbc.pstmt.setInt(1, input2);

			// sql 문장을 실행하고 결과를 리턴
			rows = jdbc.pstmt.executeUpdate();

			if (rows == 0) {
				System.out.println("삭제할 값이 없습니다");
			} else {
				System.out.println("삭제 된 행의 수: " + rows);
			} // try.if

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

	}// delete()

	// =====================================================================================================================
	// 메뉴선택 번호와 코드를 입력받으면, Product객체를 반환 하는 메서드
	public static Product selectProduct(int code) {
		// Jdbc 객체 생성
		Jdbc jdbc = new Jdbc();

		// 변수 선언
		String sql = ""; // sql문
		Product selectProduct = null; // Product 객체
		int code1 = 0; // 코드 받기
		String name1 = ""; // 이름 받기
		int price1 = 0; // 가격받기
		int menu = new OrderCart().choice.poll(); // 선택 메뉴받기

		switch (menu) {
		case 1:
			// 커피
			sql = "SELECT * FROM coffee";
			break;
		case 2:
			// 음료
			sql = "SELECT * FROM beverage";
			break;
		case 3:
			// 디저트
			sql = "SELECT * FROM dessert";
			break;
		default:
			System.out.println("잘못된 코드입니다.");
			break;
		}
		try {
			// jdbc 드라이버 등록
			Class.forName(jdbc.driver);

			// db 연결
			jdbc.con = DriverManager.getConnection(jdbc.url, jdbc.user, jdbc.password);

			System.out.println("연결 성공"); // 확인용, 추후 삭제

			// prepareStatement 객체 생성
			jdbc.pstmt = jdbc.con.prepareStatement(sql);

			// 입력받은 코드 입력
			jdbc.pstmt.setInt(1, code);

			// sql 문장을 실행하고 결과를 리턴
			jdbc.rs = jdbc.pstmt.executeQuery();

			// Product 객체를 만들어서 담기
			switch (menu) {
			case 1:
				// 커피 메뉴 담기
				if (jdbc.rs.next()) {
					code1 = jdbc.rs.getInt("ccode");
					name1 = jdbc.rs.getString("cname");
					price1 = jdbc.rs.getInt("cprice");

					selectProduct = new Coffee(code1, name1, price1);
				}
				break;
			case 2:
				// 음료 메뉴 담기
				if (jdbc.rs.next()) {
					code1 = jdbc.rs.getInt("bcode");
					name1 = jdbc.rs.getString("bname");
					price1 = jdbc.rs.getInt("bprice");

					selectProduct = new Beverage(code1, name1, price1);
				}
				break;
			case 3:
				// 디저트 메뉴 담기
				if (jdbc.rs.next()) {
					code1 = jdbc.rs.getInt("dcode");
					name1 = jdbc.rs.getString("dname");
					price1 = jdbc.rs.getInt("dprice");

					selectProduct = new Dessert(code1, name1, price1);
				}
				break;
			default:
				break;
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

		// 값을 리턴하고 끝내기
		return selectProduct;

	}// select()

	// 메뉴 출력을 위한 메서드
	public void SelectProductAll() {
		// Jdbc 객체 생성
		Jdbc jdbc = new Jdbc();

		// 변수 선언
		String sql = ""; // sql문
		int menu = 0; // 메뉴선택 코드
		String menuStr = ""; // 선택 메뉴 이름
		Product product = null; // toString
		int code = 0;
		String name = "";
		int price = 0;

		// BufferedReader
		jdbc.br = new BufferedReader(new InputStreamReader(System.in));

		try {
			// 메뉴 선택
			while (true) {
				// 가이드 출력
				System.out.println("1. 커피, 2. 음료, 3.디저트");
				System.out.println("코드를 입력하세요(숫자)>>> ");

				// 메뉴 선택
				menu = Integer.parseInt(jdbc.br.readLine());
				new OrderCart().choice.add(menu);
				
				// 처리
				if (menu == 1) {// 커피
					sql = "SELECT * FROM coffee";
					menuStr = "커피";
				} else if (menu == 2) {// 음료
					sql = "SELECT * FROM beverage";
					menuStr = "음료";
				} else if (menu == 3) {// 디저트
					sql = "SELECT * FROM dessert";
					menuStr = "디저트";
				} else {
					System.out.println("잘못된 코드입니다.");
					System.out.println();
					continue;
				} // while.if

				break;
			} // while

			// jdbc 드라이버 등록
			Class.forName(jdbc.driver);

			// db 연결
			jdbc.con = DriverManager.getConnection(jdbc.url, jdbc.user, jdbc.password);

			System.out.println("연결 성공"); // 확인용, 추후 삭제

			// statement 객체 생성
			jdbc.stmt = jdbc.con.createStatement();

			// sql 문장을 실행하고 결과를 리턴
			jdbc.rs = jdbc.stmt.executeQuery(sql);

			// 가이드 출력
			System.out.printf("============%s============\n", menuStr);
			System.out.printf("| %-4s | %-15s | %-6s |\n", "코드", "이름", "가격");

			// 처리
			while (jdbc.rs.next()) {
				
				//선택
				switch (menu) {
				case 1:
					// 커피 메뉴 담기
					code = jdbc.rs.getInt("ccode");
					name = jdbc.rs.getString("cname");
					price = jdbc.rs.getInt("cprice");
					break;
				case 2:
					// 음료 메뉴 담기
					code = jdbc.rs.getInt("bcode");
					name = jdbc.rs.getString("bname");
					price = jdbc.rs.getInt("bprice");
					break;
				case 3:
					// 디저트 메뉴 담기
					code = jdbc.rs.getInt("dcode");
					name = jdbc.rs.getString("dname");
					price = jdbc.rs.getInt("dprice");
					break;
				default:
					break;
				}//swich
				
				product = new Product(code, name, price);

				System.out.println(product.toString());
			} // while

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

}// Jdbc
