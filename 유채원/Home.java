package cafeOrder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

//수정사항
//1. 회원가입도 Account 클래스를 거쳐가도록
//2. 뒤로가기 및 잘못 입력해도 다시 재입력할 수 있도록
//3. 비밀번호 보여줄 때 *** 이런 식으로
//4. 비밀번호 찾기에서 바로 비밀번호 보여줘도 ㄱㅊ은지?

public class Home {	
	static DB db = new DB();
	static Customer customer = new Customer();
	static Account account = new Account();
	static Scanner scan = new Scanner(System.in);
	static String[] userData = new String[4]; //이름, 아이디, 비밀번호, 전화번호를 담을 배열
	
	
	//1. 앱 실행
	public static void main(String[] args) {
		boolean sw = true;
		while (sw) {
			System.out.println("[1. 로그인]\t[2. 회원가입]");
			System.out.print(">>> ");
			switch (scan.nextLine()) {
				case "1": login(); sw = false; break;
				case "2": signUp(); sw = false; break;
				default: System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
			}
		}
	}
	
	
	//2-1. 로그인
	public static void login() {
		System.out.println("========== 로그인 ==========");
		boolean sw = true;
		while (sw) {
			System.out.println("[1. 아이디/비밀번호 입력]\t[2. 아이디/비밀번호 찾기]");
			System.out.print(">>> ");
			switch (scan.nextLine()) {
				case "1": inputIDPWD(); sw = false; break;
				case "2": searchIDPWD(); sw = false; break;
				default: System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
			}
		}
	}
	
	public static void inputIDPWD() {
		System.out.println("========== 구현 예정 ==========");
	}
	
	public static void searchIDPWD() {
		System.out.println("========== 회원정보 찾기 ==========");
		boolean sw = true;
		while (sw) {
			System.out.println("[1. 아이디 찾기]\t[2. 비밀번호 찾기]");
			System.out.print(">>> ");
			switch (scan.nextLine()) {
				case "1": searchID(); sw = false; break;
				case "2": searchPWD(); sw = false; break;
				default: System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
			}
		}
	}
	
	public static void searchID() {
		db.connectDB();
		
		System.out.println("========== 아이디 찾기 ==========");
		System.out.println("이름을 입력해주세요.");
		System.out.print(">>> ");
		String name = scan.nextLine();
		System.out.println("전화번호를 입력해주세요. 구분 단위는 '-' 입니다.");
		System.out.print(">>> ");
		String phone = scan.nextLine();
		
		customer.setName(name);
		customer.setPhone(phone);
		account.findAccountID(customer.getName(), customer.getPhone());
	}
	
	public static void searchPWD() {
		db.connectDB();
		
		System.out.println("========== 비밀번호 찾기 ==========");
		System.out.println("이름을 입력해주세요.");
		System.out.print(">>> ");
		String name = scan.nextLine();
		System.out.println("아이디를 입력해주세요.");
		System.out.print(">>> ");
		String id = scan.nextLine();
		
		customer.setName(name);
		customer.setPhone(id);
		account.findAccountPWD(customer.getName(), customer.getPhone());
	}
	
	
	//2-2. 회원가입
	public static void signUp() {
		db.connectDB();
		
		System.out.println("========== 회원가입 ==========");
		saveName();
		saveID();
		savePWD();
		savePhone();
		
		Account account1 = new Account(userData[1], userData[0], userData[2], userData[3]);
		account1.addAccount();
	}
	
	public static void saveName() {
		while (true) {
			//이름 입력 받기
			System.out.println("이름을 입력해주세요.");
			System.out.print(">>> ");
			String name = scan.nextLine();
			
			//null 및 공백 체크
			boolean check = (name == null || name.isEmpty()); 
			if (check) { 
				System.out.println("잘못된 이름입니다. 다시 입력해주세요.\n");
				continue;
			} 
			
			//userData 배열에 추가
			userData[0] = name;
			break;
		}
	}
	
	public static void saveID() {
		save : while (true) {
			//아이디 입력 받기
			System.out.println("아이디를 입력해주세요.");
			System.out.print(">>> ");
			String id = scan.nextLine();
			
			//null 및 공백 체크
			boolean check = (id == null || id.isEmpty()); 
			if (check) { 
				System.out.println("잘못된 아이디입니다. 다시 입력해주세요.\n");
				continue;
			}
			
			//아이디 중복 체크
			try {
				String sql = "select customer_id from customers";
				db.PS = db.CN.prepareStatement(sql);
				db.RS = db.PS.executeQuery();
				while (db.RS.next() == true) {
					if (id.equals(db.RS.getString("customer_id"))) {
						System.out.println("이미 존재하는 아이디입니다. 다시 입력해주세요.\n");
						continue save;
					}
				}
			} catch (Exception e) {
				e.printStackTrace(); 
			}
			
			//userData 배열에 추가
			userData[1] = id;
			break;
		}
	}
	
	public static void savePWD() {
		while (true) {
			//이름 입력 받기
			System.out.println("비밀번호를 입력해주세요.");
			System.out.print(">>> ");
			String pwd = scan.nextLine();
			
			//null 및 공백 체크
			boolean check = (pwd == null || pwd.isEmpty()); 
			if (check) { 
				System.out.println("잘못된 이름입니다. 다시 입력해주세요.\n");
				continue;
			}
			
			//비밀번호 재확인 체크
			System.out.println("재확인을 위해 비밀번호를 다시 입력해주세요.");
			System.out.print(">>> ");
			String temp = scan.nextLine();
			if (!temp.equals(pwd)) {
				System.out.println("비밀번호가 일치하지 않습니다. 다시 입력해주세요.\n");
				continue;
			}
			
			//비밀번호 암호화
			final int KEY = 2;
			String encryPwd = "";
			for (int i = 0; i < pwd.length(); i++) {
				encryPwd += (char)(pwd.charAt(i) * KEY);
			}
			
			//userData 배열에 추가
			userData[2] = encryPwd;
			break;
		}
	}
	
	public static void savePhone() {
		while (true) {
			//전화번호 입력 받기
			System.out.println("전화번호를 입력해주세요. 구분 단위는 '-' 입니다.");
			System.out.print(">>> ");
			String phone = scan.nextLine();
			
			//전화번호 형식 체크
			boolean check = Pattern.matches("\\d{3}-\\d{4}-\\d{4}", phone);
			if (!check) {
				System.out.println("잘못된 전화번호입니다. 다시 입력해주세요.\n");
				continue;
			}
			
			//userData 배열에 추가
			userData[3] = phone;
			break;
		}
	}
	
}
