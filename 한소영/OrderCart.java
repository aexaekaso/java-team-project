package cafe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class OrderCart {
	// 필드 선언
	public static ArrayList<Product> products; // 여기에 담아서 추후에 결제
	public static ArrayList<Integer> amount; // 수량
	public static Queue<Integer> choice = new LinkedList<Integer>();

	// 기본 생성자
	public OrderCart() {

	}

	// 초기화 생성자
	// static이라서 필요가 없음.

	// 메서드
	// 0. 회원 화면
	public void customerPage() {
		try {
			int choice = 0;
			boolean run = true;
			
			while(run) {
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				System.out.println("=======회원 화면=======");
				System.out.println("1. 주문하기");
				System.out.println("2. 장바구니");
				System.out.println("3. 마이 페이지");
				System.out.println("4. 종료");
				System.out.println();
				
				choice = Integer.parseInt(br.readLine());
				
				if(choice==1) {
					SelectProductAll();
				}else if(choice==2) {
					orderCartShow();
				}else if(choice==3) {
					myPage();
				}else if(choice==4) {
					System.out.println("종료합니다.");
					Home.main(null);
					run = false;
				}else {
					System.out.println("잘못된 코드입니다.");
					continue;
				}//while.if
				
			}//while
		} catch (Exception e) {
			
		}
	}
	
	// 1. 메뉴 종류를 선택하고, 출력을 위한 메서드
	public void SelectProductAll() {
		try {
			// Jdbc 객체 생성
			DB jdbc = new DB();

			// 변수 선언
			int menu = 0; // 메뉴선택 코드

			// BufferedReader
			jdbc.br = new BufferedReader(new InputStreamReader(System.in));

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
					SelectCoffeeAll(); // 커피 메뉴 출력
				} else if (menu == 2) {// 음료
					SelectDessertAll(); // 음료 메뉴 출력
				} else if (menu == 3) {// 디저트
					SelectDessertAll();
				} else {
					System.out.println("잘못된 코드입니다.");
					System.out.println();
					continue;
				} // while.if

				break;
			} // while
		} catch (Exception e) {
			
		}
	}// SelectProductAll()

	// 1.1 커피 메뉴 프린트
	public void SelectCoffeeAll() {
		// Jdbc 객체 생성
		DB jdbc = new DB();

		// 변수 선언
		String sql = ""; // sql문
		String menuStr = ""; // 선택 메뉴 이름
		Product product = null; // toString
		int code = 0;
		String name = "";
		int price = 0;

		// BufferedReader
		jdbc.br = new BufferedReader(new InputStreamReader(System.in));

		try {

			sql = "SELECT * FROM coffee";
			jdbc.mysqlCode = new String[] { "ccode", "cname", "cprice" };
			menuStr = "커피";

			// jdbc 드라이버 등록
			Class.forName(jdbc.driver);

			// db 연결
			jdbc.CN = DriverManager.getConnection(jdbc.url, jdbc.user, jdbc.password);

//			System.out.println("연결 성공"); // 확인용, 추후 삭제

			// statement 객체 생성
			jdbc.stmt = jdbc.CN.createStatement();

			// sql 문장을 실행하고 결과를 리턴
			jdbc.RS = jdbc.stmt.executeQuery(sql);

			// 가이드 출력
			System.out.printf("============%s============\n", menuStr);
			System.out.printf("| %-4s | %-15s | %-6s |\n", "코드", "이름", "가격");

			// 처리
			while (jdbc.RS.next()) {
				code = jdbc.RS.getInt(jdbc.mysqlCode[0]); // code
				name = jdbc.RS.getString(jdbc.mysqlCode[1]); // name
				price = jdbc.RS.getInt(jdbc.mysqlCode[2]); // price

				product = new Product(code, name, price);

				System.out.println(product.toString());
			} // while

		} catch (

		ClassNotFoundException e) { // getConnection(url, user, password);
			e.printStackTrace(); // 프로그램이 완료된 후에 반드시 제거 또는 주석
		} catch (SQLException e) {
			e.printStackTrace(); // 프로그램이 완료된 후에 반드시 제거 또는 주석
		} catch (Exception e) {
			e.printStackTrace(); // 프로그램이 완료된 후에 반드시 제거 또는 주석
		} finally { // 무조건 실행되는 코드
					// 연결이 되어 있으면 연결 끊기

			// 사용순서 반대로 닫기
			if (jdbc.RS != null) {
				try {
					jdbc.RS.close();
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

			if (jdbc.CN != null) {
				try {
					jdbc.CN.close();
				} catch (SQLException e) {
					e.printStackTrace(); // 프로그램이 완료된 후에 반드시 제거 또는 주석
				}
			}

		} // try

	}// SelectCoffeeAll()

	// 1.2 음료 메뉴 프린트
	public void SelectBeverageAll() {
		// Jdbc 객체 생성
		DB jdbc = new DB();

		// 변수 선언
		String sql = ""; // sql문
		String menuStr = ""; // 선택 메뉴 이름
		Product product = null; // toString
		int code = 0;
		String name = "";
		int price = 0;

		// BufferedReader
		jdbc.br = new BufferedReader(new InputStreamReader(System.in));

		try {

			// 음료
			sql = "SELECT * FROM beverage";
			jdbc.mysqlCode = new String[] { "bcode", "bname", "bprice" };
			menuStr = "음료";

			// jdbc 드라이버 등록
			Class.forName(jdbc.driver);

			// db 연결
			jdbc.CN = DriverManager.getConnection(jdbc.url, jdbc.user, jdbc.password);

//			System.out.println("연결 성공"); // 확인용, 추후 삭제

			// statement 객체 생성
			jdbc.stmt = jdbc.CN.createStatement();

			// sql 문장을 실행하고 결과를 리턴
			jdbc.RS = jdbc.stmt.executeQuery(sql);

			// 가이드 출력
			System.out.printf("============%s============\n", menuStr);
			System.out.printf("| %-4s | %-15s | %-6s |\n", "코드", "이름", "가격");

			// 처리
			while (jdbc.RS.next()) {
				code = jdbc.RS.getInt(jdbc.mysqlCode[0]); // code
				name = jdbc.RS.getString(jdbc.mysqlCode[1]); // name
				price = jdbc.RS.getInt(jdbc.mysqlCode[2]); // price

				product = new Product(code, name, price);

				System.out.println(product.toString());
			} // while

		} catch (

		ClassNotFoundException e) { // getConnection(url, user, password);
			e.printStackTrace(); // 프로그램이 완료된 후에 반드시 제거 또는 주석
		} catch (SQLException e) {
			e.printStackTrace(); // 프로그램이 완료된 후에 반드시 제거 또는 주석
		} catch (Exception e) {
			e.printStackTrace(); // 프로그램이 완료된 후에 반드시 제거 또는 주석
		} finally { // 무조건 실행되는 코드
					// 연결이 되어 있으면 연결 끊기

			// 사용순서 반대로 닫기
			if (jdbc.RS != null) {
				try {
					jdbc.RS.close();
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

			if (jdbc.CN != null) {
				try {
					jdbc.CN.close();
				} catch (SQLException e) {
					e.printStackTrace(); // 프로그램이 완료된 후에 반드시 제거 또는 주석
				}
			}

		} // try

	}// SelectBeverageAll()

	// 1.3 디저트 메뉴 프린트
	public void SelectDessertAll() {
		// Jdbc 객체 생성
		DB jdbc = new DB();

		// 변수 선언
		String sql = ""; // sql문
		String menuStr = ""; // 선택 메뉴 이름
		Product product = null; // toString
		int code = 0;
		String name = "";
		int price = 0;

		// BufferedReader
		jdbc.br = new BufferedReader(new InputStreamReader(System.in));

		try {

			// 음료
			sql = "SELECT * FROM dessert";
			jdbc.mysqlCode = new String[] { "dcode", "dname", "dprice" };
			menuStr = "디저트";

			// jdbc 드라이버 등록
			Class.forName(jdbc.driver);

			// db 연결
			jdbc.CN = DriverManager.getConnection(jdbc.url, jdbc.user, jdbc.password);

//			System.out.println("연결 성공"); // 확인용, 추후 삭제

			// statement 객체 생성
			jdbc.stmt = jdbc.CN.createStatement();

			// sql 문장을 실행하고 결과를 리턴
			jdbc.RS = jdbc.stmt.executeQuery(sql);

			// 가이드 출력
			System.out.printf("============%s============\n", menuStr);
			System.out.printf("| %-4s | %-15s | %-6s |\n", "코드", "이름", "가격");

			// 처리
			while (jdbc.RS.next()) {
				code = jdbc.RS.getInt(jdbc.mysqlCode[0]); // code
				name = jdbc.RS.getString(jdbc.mysqlCode[1]); // name
				price = jdbc.RS.getInt(jdbc.mysqlCode[2]); // price

				product = new Product(code, name, price);

				System.out.println(product.toString());
			} // while

		} catch (

		ClassNotFoundException e) { // getConnection(url, user, password);
			e.printStackTrace(); // 프로그램이 완료된 후에 반드시 제거 또는 주석
		} catch (SQLException e) {
			e.printStackTrace(); // 프로그램이 완료된 후에 반드시 제거 또는 주석
		} catch (Exception e) {
			e.printStackTrace(); // 프로그램이 완료된 후에 반드시 제거 또는 주석
		} finally { // 무조건 실행되는 코드
					// 연결이 되어 있으면 연결 끊기

			// 사용순서 반대로 닫기
			if (jdbc.RS != null) {
				try {
					jdbc.RS.close();
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

			if (jdbc.CN != null) {
				try {
					jdbc.CN.close();
				} catch (SQLException e) {
					e.printStackTrace(); // 프로그램이 완료된 후에 반드시 제거 또는 주석
				}
			}

		} // try

	}// SelectDessertAll()

	// 2.1 장바구니에 담는 메서드
	public void orderCartAdd() throws NumberFormatException, IOException {
		// Jdbc 객체 생성
		DB jdbc = new DB();

		// bufferedReader
		jdbc.br = new BufferedReader(new InputStreamReader(System.in));

		// 변수선언
		int code = 0; // 코드선택
		int amount1 = 0; // 수량

		// 코드 선택 및 수량 선택
		System.out.println("메뉴를 선택하세요>>> \n(코드를 입력하세요 - 숫자)");
		code = Integer.parseInt(jdbc.br.readLine()); // 코드 입력

		System.out.println("수량을 선택하세요>>> \n(숫자를 입력하세요)"); // 다른거 치는 경우도 고려할 시, 추후 변경
		amount1 = Integer.parseInt(jdbc.br.readLine()); // 수량 입력

		// 장바구니에 넣기
		products.add(selectProduct(code));
		amount.add(amount1);

	}// orderCartAdd()

	// 2.1 코드를 입력받으면, Product객체를 반환 하는 메서드
	public static Product selectProduct(int code) {
		// Jdbc 객체 생성
		DB jdbc = new DB();

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
			sql = "SELECT * FROM coffee where ccode = ?";
			jdbc.mysqlCode = new String[] { "ccode", "cname", "cprice" };
			break;
		case 2:
			// 음료
			sql = "SELECT * FROM beverage where bcode = ?";
			jdbc.mysqlCode = new String[] { "bcode", "bname", "bprice" };
			break;
		case 3:
			// 디저트
			sql = "SELECT * FROM dessert where dcode = ?";
			jdbc.mysqlCode = new String[] { "dcode", "dname", "dprice" };
			break;
		default:
			System.out.println("잘못된 코드입니다.");
			break;
		}
		try {
			// jdbc 드라이버 등록
			Class.forName(jdbc.driver);

			// db 연결
			jdbc.CN = DriverManager.getConnection(jdbc.url, jdbc.user, jdbc.password);

//			System.out.println("연결 성공"); // 확인용, 추후 삭제

			// prepareStatement 객체 생성
			jdbc.PS = jdbc.CN.prepareStatement(sql);

			// 입력받은 코드 입력
			jdbc.PS.setInt(1, code);

			// sql 문장을 실행하고 결과를 리턴
			jdbc.RS = jdbc.PS.executeQuery();

			// Product 객체를 만들어서 담기

			if (jdbc.RS.next()) {
				code1 = jdbc.RS.getInt(jdbc.mysqlCode[0]); // code
				name1 = jdbc.RS.getString(jdbc.mysqlCode[1]); // name
				price1 = jdbc.RS.getInt(jdbc.mysqlCode[2]); // price

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
			if (jdbc.RS != null) {
				try {
					jdbc.RS.close();
				} catch (SQLException e) {
					e.printStackTrace(); // 프로그램이 완료된 후에 반드시 제거 또는 주석
				}
			} // if

			if (jdbc.PS != null) {
				try {
					jdbc.PS.close();
				} catch (SQLException e) {
					e.printStackTrace(); // 프로그램이 완료된 후에 반드시 제거 또는 주석
				}
			} // if

			if (jdbc.CN != null) {
				try {
					jdbc.CN.close();
				} catch (SQLException e) {
					e.printStackTrace(); // 프로그램이 완료된 후에 반드시 제거 또는 주석
				}
			}

		} // try

		// 값을 리턴하고 끝내기
		return selectProduct;

	}// selectProduct(int code)

	// 2.3 수량을 변경 혹은 삭제하는 메서드
	public void orderCartChange(int code) throws NumberFormatException, IOException {
		// Jdbc 객체 생성
		DB jdbc = new DB();

		// bufferedReader
		jdbc.br = new BufferedReader(new InputStreamReader(System.in));

		// 변수선언
		int tmp = 0; // 임시 값
		int amount1 = 0; // 변경 수량

		// 변경 혹은 삭제 선택
		System.out.println("1. 수량 변경, 2. 삭제");
		System.out.println("코드(숫자)를 입력하세요>>> ");
		tmp = Integer.parseInt(jdbc.br.readLine()); // 값 입력

		// 처리
		if (tmp == 1) { // 수량 변경 시
			// 수량 입력
			System.out.println("변경할 수량을 입력하세요>>> ");
			amount1 = Integer.parseInt(jdbc.br.readLine());

			// 같은 코드시 처리
			for (int i = 0; i < products.size(); i++) {
				tmp = products.get(i).code; // i번째 코드를 가져옴

				if (tmp == code) {
					amount.add(i, amount1); // 수량 변경
					break;
				} // if.for.if

			} // if.for
		} else if (tmp == 2) {// 삭제시

			for (int i = 0; i < products.size(); i++) {
				tmp = products.get(i).code; // i번째 코드를 가져옴

				if (tmp == code) {
					products.remove(i); // 메뉴 삭제
					amount.remove(i); // 수량 삭제
					break;
				} // if.for.if

			} // if.for
		} else {
			System.out.println("잘못된 코드입니다.");
		}

	}

	// 4. 장바구니를 비우는 메서드
	public void orderCartClear() { // N을 입력했을때
		products.clear();
		amount.clear();
		System.out.println("장바구니가 비워졌습니다.");
		/* public static ArrayList<Product> products; // 여기에 담아서 추후에 결제
		public static ArrayList<Integer> amount; // 수량
		을 1. 비우고 2. 출력을 장바구니비우는 / 에리리스트로 delete */
	}

	// 5. 장바구니를 보여주는 메서드
	public void orderCartShow() { // 2. 장바구니 입력했을 때
		System.out.println("============장바구니 목록============");
		System.out.printf("| %-5d | %-15s | %-6d | %-6d |", "번호", "메뉴", "수량", "가격");
		
		// String coffeestring="";
		// String beveragetring="";
		// String dessertstring="";
		String temp="";			
		
		int no=0;
		String mene="";
		int amount1 =0;
		int price=0;
		

		for(int i=0; i<amount.size(); i++) {
			no=products.get(i).code;
			mene=products.get(i).name;
			amount1=amount.get(i);
			price=amount1*products.get(i).price;
			
			System.out.printf("| %-5d | %-15s | %-6d | %-6d |", no, mene, amount1, price); 
			System.out.println("==================================");
			
		}
	}
	
	// 3. 마이페이지
	public void myPage() {
		try {
			int choice = 0;
			boolean run = true;
			
			while(run) {
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("=======마이페이지=======");
		System.out.println("1. 내 정보 보기"); // checkMyInfo();
		System.out.println("2. 내 정보 변경"); // changeMyInfo();
		System.out.println("3. 주문내역"); // orderHistory
		System.out.println("4. 회원탈퇴"); // membershipWithdrawal();
		System.out.println("5. 종료하기"); // membershipWithdrawal();
		
		choice = Integer.parseInt(br.readLine());
		
		if(choice==1) {
			checkMyInfo();
		}else if(choice==2) {
			changeMyInfo();
		}else if(choice==3) {
		    orderHistory();
		}else if(choice==4) {
			membershipDelete();			
		}else if(choice==5) {
			System.out.println("종료합니다.");
			Home.main(null);
			run = false;
		}else {
			System.out.println("잘못된 코드입니다.");
			continue;
		}//while.if
		
			}//while
		} catch (Exception e) {
	
		}
		
	} //myPage
	




	// 3.1 내 정보 확인 
	private void checkMyInfo() {  // 왜 이소라가 아니라 아무개씨가 나오지 ㅠㅠㅠㅠㅠㅠㅠㅠㅠ
		// Jdbc 객체 생성
				DB jdbc = new DB();

				// 변수 선언
				String sql = "SELECT * FROM customers"; 
				String id = ""; // 고객 아이디
				String name = ""; // 고객 이름
				String phone = ""; // 고객 휴대폰
				int coupon = 0; // 고객 쿠폰
				String koFormat = "| %-7s | %-15s | %-15s | %6s |\n"; // 출력
				String enFormat = "| %-10s | %-15s | %-15s | %6s |\n"; // 출력

				
				
				try {
					// jdbc 드라이버 등록
					Class.forName(jdbc.driver);

					// db 연결
					jdbc.CN = DriverManager.getConnection(jdbc.url, jdbc.user, jdbc.password);

					// statement 객체 생성
					jdbc.stmt = jdbc.CN.createStatement();
					
			
					// sql 문장을 실행하고 결과를 리턴
					jdbc.RS = jdbc.stmt.executeQuery(sql);
			//		((PreparedStatement) jdbc.RS).setString(1, id);
					
					
					// 출력
					System.out.format(
							"+------------+-----------------+-----------------+--------+\n");
					System.out.printf(enFormat, "Name", "ID", "Phone Number", "Coupon");
					System.out.format(
							"+------------+-----------------+-----------------+--------+\n");
		
					
					if (jdbc.RS.next()) { // 여기서 내 정보만 출력되게
						//Customer customer = new Customer(this.name, this.id, this.pwd, this.phone);

						
			
						id = jdbc.RS.getString("customer_id"); // 아이디    this.customer_id
						id = jdbc.RS.getString("customer_id"); // 아이디    this.customer_id
						name = jdbc.RS.getString("customer_name"); // 이름
						phone = jdbc.RS.getString("customer_phone"); // 폰
						coupon = jdbc.RS.getInt("customer_coupon"); // 쿠폰
						

						System.out.printf(koFormat, name, id, phone, coupon);
					}
					System.out.println();

				} catch (ClassNotFoundException e) { // getConnection(url, user, password);
					e.printStackTrace(); // 프로그램이 완료된 후에 반드시 제거 또는 주석
				} catch (SQLException e) {
					e.printStackTrace(); // 프로그램이 완료된 후에 반드시 제거 또는 주석
				} catch (Exception e) {
					e.printStackTrace(); // 프로그램이 완료된 후에 반드시 제거 또는 주석
				} finally { // 무조건 실행되는 코드
					// 연결이 되어 있으면 연결 끊기

					// 사용순서 반대로 닫기
					if (jdbc.RS != null) {
						try {
							jdbc.RS.close();
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

					if (jdbc.CN != null) {
						try {
							jdbc.CN.close();
						} catch (SQLException e) {
							e.printStackTrace(); // 프로그램이 완료된 후에 반드시 제거 또는 주석
						}
					}

				} // try

			}// checkMyInfo()

	
	// 3.2 내 정보 변경
	public static void changeMyInfo() {
		
	}
	
	// 3.3 내 주문내역 확인
	private void orderHistory() {
		//=====================================
		
		// Jdbc 객체 생성
				DB jdbc = new DB();

				// 변수 선언
				String sql = "SELECT * FROM orderCart WHERE customer_id = ?"; // sql문
				String name = ""; // 상품 이름 받기
				String id = ""; // 상품 이름 받기				
				int price = 0; // 가격받기
				int ocode = 0; // 주문 코드 받기
				int ccode = 0; // 커피 코드 받기
				int bcode = 0; // 음료 코드 받기
				int dcode = 0; // 디저트 코드 받기
				int amount = 0; // 수량 받기
				int allPrice = 0; // 판매금액 합계
				Date date = null; // date 받기

				try {

					// jdbc 드라이버 등록
					Class.forName(jdbc.driver);

					// db 연결
					jdbc.CN = DriverManager.getConnection(jdbc.url, jdbc.user, jdbc.password);

					// prepareStatement 객체 생성
					jdbc.PS = jdbc.CN.prepareStatement(sql);

					// 실행
					//jdbc.PS.setString(1, id);

					// sql 문장을 실행하고 결과를 리턴
					jdbc.RS = jdbc.PS.executeQuery();

					// 결과 출력
					String koFormat = "| %-7d | %-7d | %-7d | %-7d | %-15s | %-6d | %-6d | %-3B - %-3tA |\n";
					// 주문, 커피, 음료, 디저트, 이름, 수량, 가격, 날짜(달,요일)
					String enFormat = "| %-7s | %-7s | %-7s | %-7s | %-15s | %-6s | %-6s | %-8s |\n"; // 추후수정*********************
					System.out.format(
							"+---------+---------+---------+---------+-----------------+--------+--------+----------+\n");
					System.out.printf(enFormat, "주문", "커피", "음료", "디저트", "이름", "수량", "가격", "날짜(달,요일)");
					while (jdbc.RS.next()) {
						// 데이터 불러오기
						ocode = jdbc.RS.getInt("ocode");
						id = jdbc.RS.getString("customer_id");
						ccode = jdbc.RS.getInt("ccode");
						bcode = jdbc.RS.getInt("bcode");
						dcode = jdbc.RS.getInt("dcode");
						name = jdbc.RS.getString("oname");
						amount = jdbc.RS.getInt("oamount");
						price = jdbc.RS.getInt("oprice");
						date = jdbc.RS.getDate("odate");

						// 매출 합계
						allPrice += price;

						// 출력
						System.out.printf(koFormat, ocode, ccode, bcode, name, amount, price, date);
					}
					System.out.println();
					System.out.println("총 결제 금액>>> " + allPrice);

				} catch (ClassNotFoundException e) { // getConnection(url, user, password);
					e.printStackTrace(); // 프로그램이 완료된 후에 반드시 제거 또는 주석
				} catch (SQLException e) {
					e.printStackTrace(); // 프로그램이 완료된 후에 반드시 제거 또는 주석
				} catch (Exception e) {
					e.printStackTrace(); // 프로그램이 완료된 후에 반드시 제거 또는 주석
				} finally { // 무조건 실행되는 코드
					// 연결이 되어 있으면 연결 끊기

					// 사용순서 반대로 닫기
					if (jdbc.RS != null) {
						try {
							jdbc.RS.close();
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

					if (jdbc.CN != null) {
						try {
							jdbc.CN.close();
						} catch (SQLException e) {
							e.printStackTrace(); // 프로그램이 완료된 후에 반드시 제거 또는 주석
						}
					}

				} // try
			} // orderHistory(String id)

		
		
		
	//============================	
	
	// 4 회원탈퇴
		public static void membershipDelete() {
			// Jdbc 객체 생성
			DB jdbc = new DB();

			// 변수 선언
			String sql = ""; // sql문
			int rows = 0; // 삭제된 행의 수
			String id="";
			

			try {
				// 메뉴로 나누기
				sql = "DELETE FROM customers WHERE customer_id = ?";

				// jdbc 드라이버 등록
				Class.forName(jdbc.driver);

				// db 연결
				jdbc.CN = DriverManager.getConnection(jdbc.url, jdbc.user, jdbc.password);

				// prepareStatement 객체 생성
				jdbc.PS = jdbc.CN.prepareStatement(sql);

				// 실행
				jdbc.PS.setString(1, id);

				// sql 문장을 실행하고 결과를 리턴
				rows = jdbc.PS.executeUpdate();

				if (rows == 0) {
					System.out.println("삭제할 값이 없습니다");
				} else {
					System.out.println("삭제가 완료되었습니다.");
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
				if (jdbc.RS != null) {
					try {
						jdbc.RS.close();
					} catch (SQLException e) {
						e.printStackTrace(); // 프로그램이 완료된 후에 반드시 제거 또는 주석
					}
				} // if

				if (jdbc.PS != null) {
					try {
						jdbc.PS.close();
					} catch (SQLException e) {
						e.printStackTrace(); // 프로그램이 완료된 후에 반드시 제거 또는 주석
					}
				} // if

				if (jdbc.CN != null) {
					try {
						jdbc.CN.close();
					} catch (SQLException e) {
						e.printStackTrace(); // 프로그램이 완료된 후에 반드시 제거 또는 주석
					}
				}

			} // try

		}// membershipDelete

		
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
