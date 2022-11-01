package cafeOrder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Home {
	static Connection CN = null;
	static PreparedStatement PS = null;
	static ResultSet RS = null;
	static Scanner scan = new Scanner(System.in);
	static String[] userData = new String[4]; //이름, 아이디, 비밀번호, 전화번호를 담을 배열
	
	//데이터베이스 연결
	public static void connectDB() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/cafeorder?characterEndofing=UTF-8&serverTimezone=UTC";
			CN = DriverManager.getConnection(url, "test", "1234");
			System.out.println("*** 데이터베이스 연결 성공 ***");  //나중에 제거
		} catch(ClassNotFoundException e) {
			e.printStackTrace();  //JDBC Driver 로드 실패
		} catch(SQLException e) {
			e.printStackTrace();  //데이터베이스 연결 실패
		} catch (Exception e) {
			e.printStackTrace();  //그 외 모든 예외
		}
	}
	
	//앱 실행
	public static void main(String[] args) {
		boolean sw = true;
		while (sw) {
			System.out.println("[1. 로그인]\t[2. 회원가입]");
			System.out.print(">>> ");
			switch (scan.nextInt()) {
				case 1: login(); sw = false; break;
				case 2: signUp(); sw = false; break;
				default: System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
			}
		}
	}
	
	//로그인
	public static void login() {
		System.out.println("========== 로그인 ==========");
		
	}
	
	//회원가입
	public static void signUp() {
		scan.nextLine();
		connectDB();
		System.out.println("========== 회원가입 ==========");
		setName();
		setID();
		setPWD();
		setPhone();
		addAccount();
	}
	
	public static void setName() {
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
	
	public static void setID() {
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
				PS = CN.prepareStatement(sql);
				RS = PS.executeQuery();
				while (RS.next() == true) {
					if (id.equals(RS.getString("customer_id"))) {
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
	
	public static void setPWD() {
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
	
	public static void setPhone() {
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
	
	public static void addAccount() {
		try {
//			connectDB();
			String sql = "INSERT INTO customers(customer_id, customer_name, customer_pwd, customer_phone)"
							+ "VALUES(?, ?, ?, ?)";
			PS = CN.prepareStatement(sql);
			for (int i=0; i<userData.length; i++) {
				PS.setObject(i+1, userData[i]);
			}
			PS.executeUpdate();
	
			System.out.println("회원가입이 완료되었습니다.");
			System.out.println(Arrays.toString(userData));
		} catch (Exception e) {
			e.printStackTrace(); 
		}
	}
	
}
