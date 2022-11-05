package cafeProject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Admin {
	// 필드
	static int couponAdmin; // 쿠폰당 가격차감 금액

	// 생성자
	public Admin(int couponAdmin) {
		this.couponAdmin = couponAdmin;
	}

	// method
	// 1. 메뉴관리 메서드
	private static void productChange() throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); // 입력 받을 준비

		// 변수 선언
		int menuSelectNum = 0; // 입력 받을 숫자
		String menuSelectStr = ""; // 입력 받을 코드
		String guidTmp = "============================";
		String guidChange = "U. 가격변경\\nD. 메뉴삭제\\nI. 메뉴 추가\\n(U,D,I)코드를 입력해주세요>>> ";
		String guidErr = "잘못된 입력입니다.";

		while (true) {
			// 안내 출력
			System.out.println("===메뉴 관리===\n1. 커피관리\n2. 음료 관리\n3. 디저트 관리\n(1~3)숫자를 입력해주세요>>>");
			//
			menuSelectNum = Integer.parseInt(br.readLine());

			if (menuSelectNum == 1) {// 커피 관리

				while (true) {
					// 커피 메뉴 출력 메소드 해야할것************************************************

					// 안내 출력
					System.out.println(guidTmp);
					System.out.println(guidChange);

					// 코드 입력받기
					menuSelectStr = br.readLine();

					if (menuSelectStr.equalsIgnoreCase("U")) { // 업데이트
						productUpdate();
					} else if (menuSelectStr.equalsIgnoreCase("D")) { // 삭제
						productDelete();
					} else if (menuSelectStr.equalsIgnoreCase("I")) { // 추가
						productInsert();
					} else {// 코드 다시 입력받기
						System.out.println("guidErr");
						continue;
					} // while.if

					break; // 선택 완료후 빠져나오기
				} // while 커피 메뉴

			} else if (menuSelectNum == 2) {// 음료 관리

				while (true) {
					// 음료 메뉴 출력 메소드

					// 안내 출력
					System.out.println(guidTmp);
					System.out.println(guidChange);

					// 코드 입력받기
					menuSelectStr = br.readLine();

					if (menuSelectStr.equalsIgnoreCase("U")) { // 업데이트
						productUpdate();
					} else if (menuSelectStr.equalsIgnoreCase("D")) { // 삭제
						productDelete();
					} else if (menuSelectStr.equalsIgnoreCase("I")) { // 추가
						productInsert();
					} else {// 코드 다시 입력받기
						System.out.println("guidErr");
						continue;
					} // while.if

					break; // 선택 완료후 빠져나오기
				} // while 커피 메뉴

			} else if (menuSelectNum == 3) {// 디저트 관리

				while (true) {
					// 디저트 메뉴 출력 메소드

					// 안내 출력
					System.out.println(guidTmp);
					System.out.println(guidChange);

					// 코드 입력받기
					menuSelectStr = br.readLine();

					if (menuSelectStr.equalsIgnoreCase("U")) { // 업데이트
						productUpdate();
					} else if (menuSelectStr.equalsIgnoreCase("D")) { // 삭제
						productDelete();
					} else if (menuSelectStr.equalsIgnoreCase("I")) { // 추가
						productInsert();
					} else {// 코드 다시 입력받기
						System.out.println("guidErr");
						continue;
					} // while.if

					break; // 선택 완료후 빠져나오기
				} // while 커피 메뉴

			} else {// 번호 다시 입력받기
				System.out.println("guidErr");
				continue;
			} // while.if

			br.close(); // 입력 종료
			break; // 선택 완료후 빠져나오기
		} // while

	}// productChange()

	// 1.1 메뉴 update
	private static void productUpdate() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); // 입력 받을 준비

		// 변수 선언
		int updateCode = 0; // 메뉴의 코드
		int updatePrice = 0; // 변경할 가격
		boolean run = false;

		// 입력 받고 확인하기, 추후변경*****
		while (true) {
			System.out.println("가격을 변경할 메뉴의 코드를 입력하세요>>> "); // 입력 안내
			updateCode = Integer.parseInt(br.readLine());

			// 확인하고 없으면 다시 돌리기, 추후변경*****
			do {
				productCodeCheck(updateCode);
			} while (run);

		} // while

		System.out.println(); // 안내
		updatePrice = Integer.parseInt(br.readLine());

		// update 메서드 해야할것************************************************

		br.close();
	} // productUpdate()

	// 1.2 메뉴 insert
	private static void productInsert() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); // 입력 받을 준비

		// 변수 선언
		String InsertName = ""; // 추가할 메뉴의 이름
		int InsertPrice = 0; // 추가할 메뉴의 가격

		// 입력 받기
		System.out.println("추가할 메뉴를 입력하세요>>> ");
		InsertName = br.readLine();

		System.out.println("추가할 메뉴의 가격을 입력하세요>>> ");
		InsertPrice = Integer.parseInt(br.readLine());

		// 추가하기 해야할것************************************************

		br.close();
	}

	// 1.3 메뉴 delete
	private static void productDelete() throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); // 입력 받을 준비

		// 변수 선언
		int deleteCode = 0; // 삭제할 메뉴의 코드
		boolean run = false;

		// 입력 받고 확인하기
		while (true) {
			System.out.println("삭제할 메뉴의 코드를 입력하세요>>> "); // 입력 안내
			deleteCode = Integer.parseInt(br.readLine());

			// 확인하고 없으면 다시 돌리기, 추후변경*****
			do {
				productCodeCheck(deleteCode);
			} while (run);

		} // while

		// 삭제하기 해야할것************************************************

		br.close();
	} // productDelete()

	// 메뉴 코드 확인 메서드, 추후변경*****
	private static boolean productCodeCheck(int productCode) {
		
			//검사 해야할것************************************************
			if() {
				return true;
			}else {
				System.out.println("잘못된 코드입니다.");
				return false;
			}//if

	}// productCodeCheck(int productCode)
	
	// 회원정보를 불러오고, 삭제가 가능함
	private static void customerChange() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); // 입력 받을 준비

		// 변수선언
		int checkNum = 0; // 숫자 입력

		// 해야할것************************************************

	}// customerChange()
	
	// 판매금액 전부 불러오기 메서드
	private static void income() { // 해야할것************************************************
		// Jdbc 객체 생성
		Jdbc jdbc = new Jdbc();

		// 변수 선언
		String sql = "SELECT *"; // sql문
		Product selectProduct = null; // Product 객체
		String name = ""; // 상품 이름 받기
		String id = ""; // id 받기
		int price = 0; // 가격받기
		int code = 0; // 코드 받기
		int amount = 0; // 수량 받기
		Date date = null; //date 받기
		

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

			if (jdbc.rs.next()) {
				code1 = jdbc.rs.getInt(jdbc.mysqlCode[0]); // code
				name1 = jdbc.rs.getString(jdbc.mysqlCode[1]); // name
				price1 = jdbc.rs.getInt(jdbc.mysqlCode[2]); // price

				switch (menu) {
				case 1:
					// 커피 메뉴 담기
					selectProduct = new Coffee(code1, name1, price1);
					break;
				case 2:
					// 음료 메뉴 담기
					selectProduct = new Beverage(code1, name1, price1);
					break;
				case 3:
					// 디저트 메뉴 담기
					selectProduct = new Dessert(code1, name1, price1);
					break;
				default:
					break;
				}// if.switch

			} // if

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
	} // income()

	private static void couponValue() {// 해야할것************************************************

	} // couponValue()

}
