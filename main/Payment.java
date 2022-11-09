package cafe;

import java.sql.SQLException;
import java.util.Scanner;

public class Payment {
	static DB db = new DB();
	static Scanner sc = new Scanner(System.in);
	static int coupon = 0;
	static int discount = 0;
	static int coupon_check = 0;
	static Customer customer = Home.customer; //아이디 확인

	public static void paymentHome() {
		try {
			db.connectDB();
			
			String sql = "SELECT customer_coupon FROM customers where customer_id=?";
			db.PS = db.CN.prepareStatement(sql);
			db.PS.setString(1, customer.getId()); //아이디 선택
			db.RS = db.PS.executeQuery();
			
			while(db.RS.next()) {
				coupon = db.RS.getInt("customer_coupon");
			}
			System.out.println(customer.getId()+"님이 보유한 쿠폰 수는 "+coupon+"개 입니다.");
			System.out.println("1. 일반 결제");
			System.out.println("2. 쿠폰사용 결제");
			int select = sc.nextInt();
			if(select==1) {
				System.out.println("주문 하신 총 금액 : "+OrderCart.allPrice);
				paymentNomal();
			}
			else if(   select==2) {
				paymentCoupon();
			}
		}catch (Exception e) {
//			System.out.println("에러 발생");
		}
	}
	
	public static void paymentNomal() {
		System.out.println("결제 금액을 입력하세요");
		while(true) {
			int pay = sc.nextInt();
			if(OrderCart.allPrice<pay) {
				System.out.println("주문 금액보다 결제 금액이 큽니다\n다시입력하세요");
				continue;
			}
			if (pay <= 0) {
				System.out.println("결제 금액은 0원일 수 없습니다.\n다시입력하세요.\n");
				continue;
			}
			if(OrderCart.allPrice>pay) {
				OrderCart.allPrice -= pay;
				System.out.println(pay+"원 결제 되었습니다.\n결제하실 남은 금액은"+OrderCart.allPrice+"원 입니다.");
				continue;
			}
			System.out.println("결제 완료 되었습니다.\n");
			paymentAddCoupon();
			new OrderCart().orderInsertDB();
			OrderCart.bill();
			break;
		}
	}
	
	public static void paymentCoupon() throws SQLException {
		String sql = "SELECT customer_coupon FROM customers where customer_id=?";
		String sql2 = "SELECT customer_coupon FROM customers where customer_id='admin'";
		String sql3 = "UPDATE customers set customer_coupon = customer_coupon-? where customer_id=?";
		db.PS = db.CN.prepareStatement(sql);
		db.PS.setString(1, customer.getId());
		db.RS = db.PS.executeQuery();
		while (db.RS.next()) {
			coupon = db.RS.getInt("customer_coupon");
		}
		if (coupon == 0) {
			System.out.println("보유하신 쿠폰이 없습니다.\n일반결제를 이용해주세요.");
			paymentHome();
		}
		db.PS = db.CN.prepareStatement(sql2);
		db.RS = db.PS.executeQuery();
		while (db.RS.next()) {
			discount = db.RS.getInt("customer_coupon");
		}
		System.out.println("주문 하신 총 금액 : " + OrderCart.allPrice);
		System.out.println("보유한 쿠폰의 개수는 " + coupon + "개 입니다.\n쿠폰 1개당 차감금액은 " + discount + "원입니다.\n사용할 쿠폰 개수를 입력하세요.");
		int coupon_sel = sc.nextInt();
		if (coupon_sel <= 0) {
			System.out.println("사용할 쿠폰 개수는 0개일 수 없습니다.");
			paymentHome();
		}
		if (coupon < coupon_sel) {
			System.out.println("보유하신 쿠폰보다 더 입력하셨습니다.\n다시입력하세요");
			paymentCoupon();
		}
		if (coupon >= coupon_sel) {
			if (OrderCart.allPrice < (coupon_sel * discount) || OrderCart.allPrice == (coupon_sel * discount)) {
				db.PS = db.CN.prepareStatement(sql3);
				db.PS.setLong(1, coupon_sel);
				db.PS.setString(2, customer.getId());
				db.PS.executeUpdate();
				System.out.println("결제 완료 되었습니다.\n");
				paymentAddCoupon();
				new OrderCart().orderInsertDB();
				OrderCart.bill();
				new OrderCart().customerPage();
			}
			System.out.println((coupon_sel * discount) + "원 차감되었습니다.");
			OrderCart.allPrice -= (coupon_sel * discount);
			while (true) {
				System.out.println("결제하실 남은 금액은 " + OrderCart.allPrice + "원 입니다.");
				System.out.println("결제 금액을 입력하세요");
				int pay = sc.nextInt();
				if (OrderCart.allPrice < pay) {
					System.out.println("주문 금액보다 결제 금액이 큽니다.\n다시입력하세요.\n");
					continue;
				} 
				if (pay <= 0) {
					System.out.println("결제 금액은 0원일 수 없습니다.\n다시입력하세요.\n");
					continue;
				}
				if (OrderCart.allPrice > pay) {
					System.out.println(pay + "원 결제 되었습니다.");
					OrderCart.allPrice -= pay;
					continue;
				}
				db.PS = db.CN.prepareStatement(sql3);
				db.PS.setLong(1, coupon_sel);
				db.PS.setString(2, customer.getId());
				db.PS.executeUpdate();
				System.out.println("결제 완료 되었습니다.\n");
				paymentAddCoupon();
				new OrderCart().orderInsertDB();
				OrderCart.bill();
				break;
			}
		}
		db.RS.close();
		db.PS.close();
		db.CN.close();
	}
	public static void paymentAddCoupon() {
		try {
			String sql = "SELECT customer_couponcheck FROM customers where customer_id=?";
			String sql2 = "UPDATE customers set customer_couponcheck = customer_couponcheck+1 where customer_id=?"; //스탬프 1개 추가
			String sql3 = "UPDATE customers set customer_couponcheck = customer_couponcheck-10, customer_coupon = customer_coupon+1 where customer_id=?"; //스탬프 10개 제거, 쿠폰 1개추가
//			db.PS = db.CN.prepareStatement(sql);
//			db.PS.setString(1, customer.getId());
//			db.RS = db.PS.executeQuery();
//			
//			while(db.RS.next()) { //쿠폰 적립 수 확인
//				coupon_check = db.RS.getInt("customer_couponcheck");
//			}
//			System.out.println("이전 스탬프 개수 : "+coupon_check+"\n");
			db.PS = db.CN.prepareStatement(sql2);
			db.PS.setString(1, customer.getId());
			System.out.println("스탬프 1개가 추가 되었습니다.");
			db.PS.executeUpdate();
			db.PS = db.CN.prepareStatement(sql);
			db.PS.setString(1, customer.getId());
			db.RS = db.PS.executeQuery();
			while(db.RS.next()) { //쿠폰 적립 수 확인
				coupon_check = db.RS.getInt("customer_couponcheck");
			}
			System.out.println("현재 스탬프 개수 : "+coupon_check+"\n");
			if(coupon_check == 10){
				db.PS = db.CN.prepareStatement(sql3);
				db.PS.setString(1, customer.getId());
				db.PS.executeUpdate();
				System.out.println("스탬프 10개가 적립되어 쿠폰이 적립되었습니다.");
			}
			db.RS.close();
			db.PS.close();
			db.CN.close();
		}catch (SQLException e) {
			//e.printStackTrace();
		}
	}
}