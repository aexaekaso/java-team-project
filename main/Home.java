package cafe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Home {	
	static DB db = new DB();
	static Scanner scan = new Scanner(System.in);
	static String[] customerData = new String[4]; //아이디, 비밀번호, 이름, 전화번호를 담을 배열
	
	//1. 앱 실행
	public static void main(String[] args) {		
		boolean sw = true;
		while (sw) {
			System.out.print("[1. 로그인]\t[2. 회원가입]\n>>> ");
			switch (scan.nextLine()) {
				case "1": login(); sw = false; break;
				case "2": signUp(); sw = false; break;
				default: System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
			}
		}
	}
	
	
	//2-1. 로그인
	public static void login() {
		System.out.println("\n========== 로그인 ==========");
		boolean sw = true;
		while (sw) {
			System.out.print("[1. 아이디/비밀번호 입력]\t[2. 아이디 찾기]\t[3. 비밀번호 찾기]\n>>> ");
			switch (scan.nextLine()) {
				case "1": inputIDPWD(); sw = false; break;
				case "2": findID(); sw = false; break;
				case "3": findPWD(); sw = false; break;
				default: System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
			}
		}
	}
	
	public static void inputIDPWD() {
		System.out.println("\n========== 아이디/비밀번호 입력 ==========");
		//작성 예정...
	}
	
	public static void findID() {
		System.out.println("\n========== 아이디 찾기 ==========");
		System.out.print("이름을 입력해주세요.\n>>> ");
		String name = scan.nextLine();
		System.out.print("전화번호를 입력해주세요. 구분 단위는 '-' 입니다.\n>>> ");
		String phone = scan.nextLine();
		
		Account account = new Account();
		String foundID = account.findAccountID(name, phone);
		switch (foundID) {
			case "error": System.out.println("에러 발생"); break;
			case "empty": System.out.println("일치하는 회원정보가 없습니다."); break;
			default: System.out.printf("[%s]님의 아이디는 [%s]입니다.", name, foundID);
		}
	}
	
	public static void findPWD() {
		System.out.println("\n========== 비밀번호 찾기 ==========");
		System.out.print("이름을 입력해주세요.\n>>> ");
		String name = scan.nextLine();
		System.out.print("아이디를 입력해주세요.\n>>> ");
		String id = scan.nextLine();
		
		Account account = new Account();
		String foundPWD = account.findAccountPWD(name, id);
		switch (foundPWD) {
			case "error": System.out.println("에러 발생"); break;
			case "empty": System.out.println("일치하는 회원정보가 없습니다."); break;
			default: System.out.printf("[%s]님의 비밀번호는 [%s]입니다.", name, foundPWD);
		}
	}
	
	
	//2-2. 회원가입
	public static void signUp() {
		System.out.println("\n========== 회원가입 ==========");
		saveID();
		savePWD();
		saveName();
		savePhone();
		
		Account account = new Account();
		account.addAccount(customerData);
	}
	
	public static void saveID() {
		save : while (true) {
			//아이디 입력 받기
			System.out.print("아이디를 입력해주세요.\n>>> ");
			String id = scan.nextLine();
			
			//null 및 공백 체크
			boolean check = (id == null || id.isEmpty()); 
			if (check) { 
				System.out.println("잘못된 아이디입니다. 다시 입력해주세요.\n");
				continue;
			}
			
			//아이디 중복 체크
			try {
				db.connectDB();
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
			
			//customerData 배열에 추가
			customerData[0] = id;
			break;
		}
	}
	
	public static void savePWD() {
		while (true) {
			//이름 입력 받기
			System.out.print("비밀번호를 입력해주세요.\n>>> ");
			String pwd = scan.nextLine();
			
			//null 및 공백 체크
			boolean check = (pwd == null || pwd.isEmpty()); 
			if (check) { 
				System.out.println("잘못된 이름입니다. 다시 입력해주세요.\n");
				continue;
			}
			
			//비밀번호 재확인 체크
			System.out.print("재확인을 위해 비밀번호를 다시 입력해주세요.\n>>> ");
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
			
			//customerData 배열에 추가
			customerData[1] = encryPwd;
			break;
		}
	}
	
	public static void saveName() {
		while (true) {
			//이름 입력 받기
			System.out.print("이름을 입력해주세요.\n>>> ");
			String name = scan.nextLine();
			
			//null 및 공백 체크
			boolean check = (name == null || name.isEmpty()); 
			if (check) { 
				System.out.println("잘못된 이름입니다. 다시 입력해주세요.\n");
				continue;
			} 
			
			//customerData 배열에 추가
			customerData[2] = name;
			break;
		}
	}
	
	public static void savePhone() {
		while (true) {
			//전화번호 입력 받기
			System.out.print("전화번호를 입력해주세요. 구분 단위는 '-' 입니다.\n>>> ");
			String phone = scan.nextLine();
			
			//전화번호 형식 체크
			boolean check = Pattern.matches("\\d{3}-\\d{4}-\\d{4}", phone);
			if (!check) {
				System.out.println("잘못된 전화번호입니다. 다시 입력해주세요.\n");
				continue;
			}
			
			//customerData 배열에 추가
			customerData[3] = phone;
			break;
		}
	}
	
}
