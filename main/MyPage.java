package cafe;

import java.util.Scanner;

public class MyPage {
	Scanner scan = new Scanner(System.in);
	DB db = new DB();
	Customer customer = Home.customer;

	public void myPageHome() {
		System.out.println("\n========== 마이 페이지 ==========");
		while (true) {
			System.out.print("[1. 내 정보 보기]\n[2. 내 정보 변경]\n[3. 주문내역]\n[4. 회원탈퇴]\n[5. 돌아가기]\n>>> ");
			switch (scan.nextLine()) {
				case "1": 
					viewMyInfo(); 
					break;
				case "2": 
					changeMyInfo(); 
					break;
				case "3":
					orderList();
					break;
				case "4":
					signOut();
					break;
				case "5":
					new OrderCart().customerPage();
					break;
				default: 
					System.out.println("잘못된 입력입니다. 다시 입력해주세요.\n"); 
					continue;
			}
		}
	}
	
	public void viewMyInfo() {
		System.out.println("\n========== 내 정보 보기 ==========");
		try {
			db.connectDB();
			String sql = "select * from customers where customer_id=?";
			db.PS = db.CN.prepareStatement(sql);
			db.PS.setString(1, customer.getId());
			db.RS = db.PS.executeQuery();
			
			if (db.RS.next() == true) {
				String printFormat = "| %-10s | %-15s | %-15s | %6s | %12s |\n";
				System.out.format("+------------+-----------------+-----------------+--------+--------------+\n");
				System.out.printf(printFormat, "Name", "ID", "Phone Number", "Coupon", "Stamp");
				System.out.format("+------------+-----------------+-----------------+--------+--------------+\n");
				System.out.printf(printFormat,
						db.RS.getString("customer_name"),
						db.RS.getString("customer_id"),
						db.RS.getString("customer_phone"),
						db.RS.getInt("customer_coupon"),
						db.RS.getInt("customer_couponCheck")
				);
				System.out.format("+------------+-----------------+-----------------+--------+--------------+\n");
				System.out.println(); 
			}
			
			myPageHome();
		} catch (Exception e) {
			System.out.println("viewMyInfo() error");
		}
	}
	
	public void changeMyInfo() {
		System.out.println("\n========== 내 정보 변경 ==========");
		boolean run = true;
		while (run) {
			System.out.print("[1. 비밀번호 변경]\n[2. 전화번호 변경]\n>>> ");
			switch (scan.nextLine()) {
				case "1": 
					changePwd(); 
					run = false; 
					break;
				case "2": 
					changePhone(); 
					run = false; 
					break;
				default: 
					System.out.println("잘못된 입력입니다. 다시 입력해주세요.\n"); 
					continue;
			}
		}
	}
	
	public void changePwd() {
		System.out.println("\n========== 비밀번호 변경 ==========");
		if (checkPwd()) {
			System.out.println("비밀번호가 일치하지 않습니다.");
			changeMyInfo();
		} else {
			try {
				String pwd = null;
				while (true) {
					System.out.printf("변경할 비밀번호를 입력해주세요.\n>>> ");
					String temp = scan.nextLine();
					
					if (comparePwd(temp)) {
						System.out.println("현재 사용 중인 비밀번호는 사용할 수 없습니다.");
						continue;
					} else if (Home.checkBlank(temp)) {
						System.out.println("잘못된 비밀번호입니다. 다시 입력해주세요.\n");
						continue;
					} else if (Home.checkReconfirmPWD(temp)) {
						System.out.println("비밀번호가 일치하지 않습니다. 다시 입력해주세요.\n");
						continue;
					} else {
						pwd = Home.encrypt(temp);
						break;
					}
				}
				
				db.connectDB();
				String sql = "update customers set customer_pwd=? where customer_id=?";
				db.PS = db.CN.prepareStatement(sql);
				db.PS.setString(1, pwd);
				db.PS.setString(2, customer.getId());
				db.PS.executeUpdate();
				
				System.out.println("비밀번호 변경이 완료되었습니다.\n");
			} catch (Exception e) {
				System.out.println("changPwd() error");
			}
		}
	}
	
	public void changePhone() {
		System.out.println("\n========== 전화번호 변경 ==========");
		if (checkPwd()) {
			System.out.println("비밀번호가 일치하지 않습니다.");
			changeMyInfo();
		} else {
			try {
				String phone = null;
				while (true) {
					System.out.printf("변경할 전화번호를 입력해주세요. 구분 단위는 '-' 입니다.\n>>> ");
					String temp = scan.nextLine();
					
					if (comparePhone(temp)) {
						System.out.println("현재 사용 중인 비밀번호는 사용할 수 없습니다.");
						continue;
					} else if (Home.checkPhoneFormat(temp)) {
						System.out.println("잘못된 전화번호입니다. 다시 입력해주세요.\n");
						continue;
					} else {
						phone = temp;
						break;
					}
				}
				
				db.connectDB();
				String sql = "update customers set customer_phone=? where customer_id=?";
				db.PS = db.CN.prepareStatement(sql);
				db.PS.setString(1, phone);
				db.PS.setString(2, customer.getId());
				db.PS.executeUpdate();
				System.out.println("전화번호 변경이 완료되었습니다.\n");
				
			} catch (Exception e) {
				System.out.println("changPwd() error");
			}
		}
	}
	
	// 주문내역 보기
	public void orderList() {
		try {
			db.connectDB();
			int sum = 0;
			String sql = "SELECT oname, oamount, oprice, odate FROM orderCart WHERE customer_id=?";
			db.PS = db.CN.prepareStatement(sql);
			db.PS.setString(1, customer.getId()); //아이디 선택
			db.RS = db.PS.executeQuery();
			if(db.RS.next()==false) {
				System.out.println("주문 내역이 없습니다.");
				myPageHome();
			}
			db.RS = db.PS.executeQuery(); //재선언
			System.out.println("    메뉴   / 수량/ 가격 / 결제시간");
			while(db.RS.next()) {
				System.out.println(db.RS.getString("oname")+" /  "+db.RS.getInt("oamount")+"  / "+db.RS.getInt("oprice")+" / "+db.RS.getTimestamp(4));
				sum += db.RS.getInt("oprice");
			}
			System.out.println("총 주문한 금액 : "+sum+"원");
		} catch (Exception e) {
//			System.out.println("에러");
		}
	}
	
	public void signOut() {
		System.out.println("\n========== 회원탈퇴 ==========");
		if (checkPwd()) {
			System.out.println("비밀번호가 일치하지 않습니다.");
			changeMyInfo();
		} else {
			try {
				db.connectDB();
				String sql = "delete from customers where customer_id=?";
				db.PS = db.CN.prepareStatement(sql);
				db.PS.setString(1, customer.getId());
				db.PS.executeUpdate();
				System.out.println("회원탈퇴가 완료되었습니다.\n");
				Home.main(null);
			} catch (Exception e) {
				//e.printStackTrace();
//				System.out.println("signOut() error");
			}
			
		}
	}
	
	//내 정보 변경 및 회원탈퇴 전에 비밀번호 입력하고 확인하는 절차
	public boolean checkPwd() {
		try {
			System.out.print("현재 비밀번호를 입력해주세요.\n>>> ");
			String pwd = scan.nextLine();
			
			db.connectDB();
			String sql = "select customer_pwd from customers where customer_id=?";
			db.PS = db.CN.prepareStatement(sql);
			db.PS.setString(1, customer.getId());
			db.RS = db.PS.executeQuery();
			
			boolean pass = false;
			if (db.RS.next() == true) {
				pass = !(Home.encrypt(pwd).equals(db.RS.getString("customer_pwd"))) ? true : false;
			}
			return pass;  //입력된 비밀번호가 DB의 비밀번호와 다를 경우, true
		} catch (Exception e) {
			return false;
		}
	}
	
	//비밀번호 변경 시, 현재 비밀번호와 같은지 확인
	public boolean comparePwd(String pwd) {
		try {
			db.connectDB();
			String sql = "select customer_pwd from customers where customer_id=?";
			db.PS = db.CN.prepareStatement(sql);
			db.PS.setString(1, customer.getId());
			db.RS = db.PS.executeQuery();
			
			boolean pass = false;
			if (db.RS.next() == true) {
				pass = (Home.encrypt(pwd).equals(db.RS.getString("customer_pwd"))) ? true : false;
			}
			return pass;  //입력된 비밀번호가 현재 비밀번호와 같은 경우, true
		} catch (Exception e) {
			return false;
		}
	}
	
	//전화번호 변경 시, 현재 전화번호와 같은지 확인
	public boolean comparePhone(String phone) {
		try {
			db.connectDB();
			String sql = "select customer_phone from customers where customer_id=?";
			db.PS = db.CN.prepareStatement(sql);
			db.PS.setString(1, customer.getId());
			db.RS = db.PS.executeQuery();
			
			boolean pass = false;
			if (db.RS.next() == true) {
				pass = (phone.equals(db.RS.getString("customer_phone"))) ? true : false;
			}
			return pass;  //입력된 전화번호가 현재 전화번호와 같은 경우, true
		} catch (Exception e) {
			return false;
		}
	}
}