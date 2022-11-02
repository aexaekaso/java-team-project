package cafe;

import java.util.ArrayList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class OrderCart { //extends Product 이 클래스도 상속을 받아야하나? // product하고는 같은패키지니까 딱히 뭘 안해도 되는건가.....

	//+products : static Arraylist<Product> 
	//+amount : static Arraylist<Integer> 
	//+OrderCart()
	//+orderCartAdd(Product products, int amount)
	//+orderCartChange(Product products, int amount)
	//+orderCartClear()
	//+toString()
	
	static Scanner sc = new Scanner(System.in);
	private static ResultSet rs;
	static Connection con = null;
	static int c = 0;  //쿠폰관련
	
	public OrderCart() {}
	
	public static void OrderCartList() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost/cafedb";
			con = DriverManager.getConnection(url, "3jo", "1234");
			String sql = "SELECT orderCart_couponcheck, customer_coupon FROM customers where customer_id=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "asd"); //아이디 선택
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				c = rs.getInt("customer_coupon");
				System.out.println("======결제======");
				System.out.println("orderCart 내역");
				System.out.println("       총 금액");
				System.out.println("----------------");
				System.out.println("현재 가지고 있는 쿠폰 수는 "+c+"개");
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
	}

	
	
	
	
	
	
	
	//==========================================
	
	//+products : static Arraylist<Product> 
	//+amount : static Arraylist<Integer> 
	//p674 지네릭 클래스
	static ArrayList<Product> product = new ArrayList<Product>(); //제품
	static ArrayList<Integer> amount = new ArrayList<Integer>();
	
	
	// 장바구니
	//+OrderCart()   
	
	product.add(new Product());  // 왜 안되지...ㄴ
	
	
	//+orderCartAdd(Product products, int amount)
	//상품추가
	
	
	//+orderCartChange(Product products, int amount)
	//상품수정
	//.remove(); // 한개삭제
	
	//+orderCartClear()
	//장바구니비우기
	
	//.clear(); 리스트 전체 삭제
	
	
	
	
	//+toString() 무엇을 해줘야하지  ?
	@Override 
	public String toString() {
		return "OrderCart [code=" + code + ", name=" + name + ", price=" + price + "]";
	}
	
	
	
	
	
	
	/*
	///이런걸로 담아아ㅗ야하나?
	public int getCode() {  // 그 필드의 첫글자만 대문자
		return Code;  // 아직 값이 없음  - set에서 담긴것을 리턴시킨다. 
	}
	
	public void setSsn(int Code) {
		this.Code = Code;  // mian에서 설정된 값을 담음
	}
	*/

	
	

}
