package cafe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Coffee extends Product{
	static Scanner sc = new Scanner(System.in);
	static ResultSet rs;
	static Connection con = null;
	
	static String ccode="";
	static String cName="";
	static String cPrice="";
	
	public Coffee() {} //초기화
	
	public static void Coffee() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost/cafedb";
			con = DriverManager.getConnection(url, "3jo", "1234"); //db에 연결, user-사용자, password-암호 //일단 동건님이 넣으신거 넣어둠
			String sql = "SELECT ccode, cName, cPrice FROM coffee where ccode=?"; //어떤 자료를 가져올건가
			PreparedStatement pstmt = con.prepareStatement(sql);  //객체 구현 고정. 그냥 외워
			pstmt.setString(1,"asd"); //무엇을 선택할 것인가. 값을 무얼 넣는거지?
			rs = pstmt.executeQuery(); // sql문 실행
			
			// rs 객체에 담겨있는 값을 가져다 Board 객체를 생성해서 담기
			// rs.next() : 데이터가 있으면 true, 없으면 false
			while(rs.next()) {	
				Product Product = new Product(); //rs 객체에 담겨있는 값을 가져다 Product 객체를 생성해서 담기
				
				System.out.println("======커피======");
				System.out.println("orderCart 내역");
				System.out.println("       총 금액");
				System.out.println("----------------");
				System.out.println("현재 가지고 있는 쿠폰 수는 "+"개");
//				System.out.println("현재 금액에서 사용할 수 있는 쿠폰 수는 "+c+"개");
				System.out.println("1. 일반 결제");
				System.out.println("2. 혼합 결제");
			}
			int sel = sc.nextInt();
			if(sel==1) {
				paymentNomal();
			}
			else if(sel==2) {
				paymentCoupon();
			}
			rs.close();
			pstmt.close();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
        }catch (SQLException e) {
        	e.printStackTrace();
        }
	}
	
	
	
	
	
	
	//===============
	
	
	
	
	
	
	
	
	
	
		//+code : int
		//+name : String
		//+price : int
		//+Coffee()
		//+Coffee(int, String, int)
		//+toString()
		
	//	Product coffee1 = new Product(1, "아메리카노", 2000);
	//	Product coffee2 = new Product(2, "카페라떼", 2500);
	//	Product coffee3 = new Product(3, "바닐라라떼", 2500);
		
		
		
	
	
	// select spl..
	
	

	@Override
	public String toString() {
		return "Coffee [code=" + ccode + ", name=" + cName + ", price=" + cPrice + ", toString()=" + super.toString()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}

	
}
