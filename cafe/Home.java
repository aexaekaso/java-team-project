package cafe;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Home {	
	static Scanner scan = new Scanner(System.in);
	static DB db = new DB();
	static Customer customer = new Customer();
	static String[] customerData = new String[4]; //아이디, 비밀번호, 이름, 전화번호를 담을 배열
	
	////////// 앱 실행 //////////
	//메인 화면
	public static void main(String[] args) {	
		while (true) {
			System.out.println("==================================================");
			System.out.println("* 환영합니다!");
			System.out.println("==================================================");
			System.out.print("[1. 로그인]\n[2. 회원가입]\n[3. 종료하기]\n>>> ");
			switch (scan.nextLine()) {
				case "1": login(); break;
				case "2": signUp(); continue;
				case "3": System.out.println("프로그램이 종료되었습니다."); System.exit(0); break;
				default: System.out.println("잘못된 입력입니다. 다시 입력해주세요.\n"); continue;
			}
		}
	}
	
	
	////////// 로그인 //////////
	//로그인 화면
	public static void login() {
		System.out.println("\n==================================================");
		System.out.println("* 로그인");
		System.out.println("==================================================");
		while (true) {
			System.out.print("[1. 아이디/비밀번호 입력]\n[2. 아이디 찾기]\n[3. 비밀번호 찾기]\n>>> ");
			switch (scan.nextLine()) {
				case "1": inputIDPWD(); break;
				case "2": findID(); break;
				case "3": findPWD(); break;
				default: System.out.println("잘못된 입력입니다. 다시 입력해주세요.\n"); continue;
			}
		}
	}
	
	
	//아이디/비밀번호 입력 및 관리자 or 회원 로그인 처리
	public static void inputIDPWD() {
		System.out.println("\n==================================================");
		System.out.println("* 아이디/비밀번호 입력");
		System.out.println("==================================================");
		int count = 1;
		while (true) {
			System.out.print("아이디를 입력해주세요.\n>>> ");
			String id = scan.nextLine();
			System.out.print("비밀번호를 입력해주세요.\n>>> ");
			String pwd = scan.nextLine();
			
			switch (handleLogin(id, pwd)) {
				case "empty":
					System.out.println("아이디 또는 비밀번호를 다시 확인해주세요.");
					System.out.printf("현재 %d회 로그인에 실패하였습니다. 3번 이상 실패하면 이전 화면으로 돌아갑니다.\n\n", count);
					count++;
					if (count > 3) {
						System.out.println("3번 이상 로그인에 실패하여 이전 화면으로 돌아갑니다.\n");
						login();
					}
					continue;
				case "admin":
					System.out.println("관리자로 로그인하였습니다.\n");
					adminInfo().adminPage();
					break;
				case "customer":
					System.out.println("로그인이 완료되었습니다.\n");
					new OrderCart().customerPage();
					break;
			}
		}
	}
	
	//아이디 찾기
	public static void findID() {
		System.out.println("\n==================================================");
		System.out.println("* 아이디 찾기");
		System.out.println("==================================================");
		System.out.print("이름을 입력해주세요.\n>>> ");
		String name = scan.nextLine();
		System.out.print("전화번호를 입력해주세요. 구분 단위는 '-' 입니다.\n>>> ");
		String phone = scan.nextLine();
		
		String foundID = new Account().findAccountID(name, phone);
		switch (foundID) {
			case "error": 
				System.out.println("에러 발생"); 
				login(); 
				break;
			case "empty": 
				System.out.println("일치하는 회원정보가 없습니다."); 
				login();
				break;
			default: 
				System.out.printf("[%s]님의 아이디는 [%s]입니다.\n", name, foundID); 
				login();
				
		}
	}
	
	//비밀번호 찾기
	public static void findPWD() {
		System.out.println("\n==================================================");
		System.out.println("* 비밀번호 찾기");
		System.out.println("==================================================");
		System.out.print("이름을 입력해주세요.\n>>> ");
		String name = scan.nextLine();
		System.out.print("아이디를 입력해주세요.\n>>> ");
		String id = scan.nextLine();
	
		String foundPWD = new Account().findAccountPWD(name, id);
		switch (foundPWD) {
			case "error": 
				System.out.println("에러 발생"); 
				login(); 
				break;
			case "empty": 
				System.out.println("일치하는 회원정보가 없습니다."); 
				login(); 
				break;
			default: 
				System.out.printf("[%s]님의 비밀번호는 [%s]입니다.\n", name, decrypt(foundPWD)); 
				login();
		}
	}
	
	
	////////// 회원가입 //////////
	//회원가입 화면
	public static void signUp() {
		System.out.println("\n==================================================");
		System.out.println("* 회원가입");
		System.out.println("==================================================");
		saveID();
		savePWD();
		saveName();
		savePhone();
		new Account().addAccount(customerData);
	}
	
	//아이디 입력 및 저장
	public static void saveID() {
		while (true) {
			//아이디 입력 받기
			System.out.print("아이디를 입력해주세요.\n>>> ");
			String id = scan.nextLine();
			
			//입력값 유효성 체크 및 배열에 데이터 저장
			if (checkBlank(id)) {
				System.out.println("잘못된 아이디입니다. 다시 입력해주세요.\n");
				continue;
			} else if (checkAdminID(id)) {
				System.out.println("사용할 수 없는 아이디입니다. 다시 입력해주세요.\n");
				continue;
			} else if (checkDuplicateID(id)) {
				System.out.println("이미 존재하는 아이디입니다. 다시 입력해주세요.\n");
				continue;
			} else {
				customerData[0] = id;
				break;
			}
		}
	}
	
	//비밀번호 입력 및 저장
	public static void savePWD() {
		while (true) {
			//이름 입력 받기
			System.out.print("비밀번호를 입력해주세요.\n>>> ");
			String pwd = scan.nextLine();
			
			//입력값 유효성 체크 및 암호화 후 배열에 데이터 저장
			if (checkBlank(pwd)) {
				System.out.println("잘못된 비밀번호입니다. 다시 입력해주세요.\n");
				continue;
			} else if (checkReconfirmPWD(pwd)) {
				System.out.println("비밀번호가 일치하지 않습니다. 다시 입력해주세요.\n");
			} else {
				customerData[1] = encrypt(pwd);
				break;
			}
		}
	}
	
	//이름 입력 및 저장
	public static void saveName() {
		while (true) {
			//이름 입력 받기
			System.out.print("이름을 입력해주세요.\n>>> ");
			String name = scan.nextLine();
			
			//입력값 유효성 체크 및 배열에 데이터 저장
			if (checkBlank(name)) {
				System.out.println("잘못된 이름입니다. 다시 입력해주세요.\n");
				continue;
			} else {
				customerData[2] = name;
				break;
			}
		}
	}
	
	//전화번호 입력 및 저장
	public static void savePhone() {
		while (true) {
			//전화번호 입력 받기
			System.out.print("전화번호를 입력해주세요. 구분 단위는 '-' 입니다.\n>>> ");
			String phone = scan.nextLine();
			
			//입력값 유효성 체크 및 배열에 데이터 저장
			if (checkPhoneFormat(phone)) {
				System.out.println("잘못된 전화번호입니다. 다시 입력해주세요.\n");
				continue;
			} else {
				customerData[3] = phone;
				break;
			}
		}
	}
	
	
	////////// 로그인 관련 메서드 //////////
	//로그인을 시도하면 관리자인지 일반회원인지 판별
	public static String handleLogin(String id, String pwd) {
		try {
			db.connectDB();
			String sql = "select * from customers where customer_id=? and customer_pwd=?";
			db.PS = db.CN.prepareStatement(sql);
			db.PS.setString(1, id);
			db.PS.setString(2, encrypt(pwd));
			db.RS = db.PS.executeQuery();
			
			if (db.RS.next() == false) {
				return "empty"; 
			} else if (db.RS.getString("customer_id").equals("admin")) {
				return "admin";
			} else {
				createCustomerObj(id, pwd);
				return "customer";
			}
		} catch (Exception e) {
			return "handleLogin() error";
		}
	}
	
	//일반회원으로 로그인할 경우, 로그인한 회원의 정보를 생성
	public static void createCustomerObj(String id, String pwd) {
		try {
			db.connectDB();
			String sql = "select * from customers where customer_id=? and customer_pwd=?";
			db.PS = db.CN.prepareStatement(sql);
			db.PS.setString(1, id);
			db.PS.setString(2, encrypt(pwd));
			db.RS = db.PS.executeQuery();
			
			if (db.RS.next() == true) {
				customer.setId(db.RS.getString("customer_id"));
				customer.setPwd(db.RS.getString("customer_pwd"));
				customer.setName(db.RS.getString("customer_name"));
				customer.setPhone(db.RS.getString("customer_phone"));
				customer.setCoupon(db.RS.getInt("customer_coupon"));
				customer.setCouponCheck(db.RS.getInt("customer_couponCheck"));
			}
			
		} catch (Exception e) {
//			System.out.println("createCustomerObj() error");
		}
	}
	
	////////// 데이터 유효성 검사 //////////
	//null값이나 공백인지 체크
	public static boolean checkBlank(String data) {
		return (data == null || data.isEmpty()) ? true : false;  //null값이거나 공백일 경우, true
	}
	
	//일반회원이 관리자 아이디를 사용하려는지 체크
	public static boolean checkAdminID(String data) {
		return (data.equals("admin")) ? true : false;  //관리자 아이디일 경우, true
	}
	
	//아이디가 중복인지 체크
	public static boolean checkDuplicateID(String id) {
		try {
			db.connectDB();
			String sql = "select customer_id from customers";
			db.PS = db.CN.prepareStatement(sql);
			db.RS = db.PS.executeQuery();
			
			boolean duplicate = false;
			while (db.RS.next() == true) {
				if (id.equals(db.RS.getString("customer_id"))) {
					duplicate = true;
					break;
				} 
			}
			return duplicate; //중복된 아이디일 경우, true
		} catch (Exception e) {
			return false;
		}
	}
	
	//비밀번호 재확인
	public static boolean checkReconfirmPWD(String pwd) {
		System.out.print("재확인을 위해 비밀번호를 다시 입력해주세요.\n>>> ");
		String temp = scan.nextLine();
		return !(temp.equals(pwd)) ? true : false;  //비밀번호와 재입력된 비밀번호가 일치하지 않을 경우, true
	}
	
	//전화번호 양식에 맞는지 체크
	public static boolean checkPhoneFormat(String phone) {
		return !(Pattern.matches("\\d{3}-\\d{4}-\\d{4}", phone)) ? true : false;  //전화번호 양식에 맞지 않을 경우, true
	}
	
	
	////////// 데이터 암호화 및 복호화 //////////
	//암호화
	public static String encrypt(String data) {
		int KEY = 2;
		String encryptedData = "";
		for (int i = 0; i < data.length(); i++) {
			encryptedData += (char)(data.charAt(i) * KEY);
		}
		return encryptedData;
	}
	
	//복호화
	public static String decrypt(String data) {
		int KEY = 2;
		String decryptedData = "";
		for (int i=0; i<data.length(); i++) {
			decryptedData += (char)(data.charAt(i) / KEY);
		}
		return decryptedData;
	}
	
	//////// 관리자 정보 받기 /////////////
	//아이디가 중복인지 체크
		public static Admin adminInfo() {
			int coupon = 0;
			Admin admin = null;
			try {
				db.connectDB();
				String sql = "SELECT customer_coupon FROM customers WHERE customer_id = 'admin'";
				db.PS = db.CN.prepareStatement(sql);
				db.RS = db.PS.executeQuery();
				
				if (db.RS.next() == true) {
					coupon = db.RS.getInt("customer_coupon");
					admin = new Admin(coupon);
					admin.couponAdmin = coupon;
				}//
			} catch (Exception e) {

			}//try
			return admin;
		}//adminInfo()
}