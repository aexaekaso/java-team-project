package cafeOrder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Account extends Customer {
	static DB db = new DB();
	
	private String id;  //기본키
	private String name;
	private String pwd;
	private String phone;
	
	Account() {};
	Account(String id, String name, String pwd, String phone) {
		this.id = id;
		this.name = name;
		this.pwd = pwd;
		this.phone = phone;
	}
	
	public void addAccount() {
		try {
			String sql = "INSERT INTO customers(customer_name, customer_id, customer_pwd, customer_phone) VALUES(?, ?, ?, ?)";
			db.PS = db.CN.prepareStatement(sql);
			
//			for (int i=0; i<userData.length; i++) {
//				db.PS.setObject(i+1, userData[i]);
//			}
			db.PS.setString(1, this.name);
			db.PS.setString(2, this.id);
			db.PS.setString(3, this.pwd);
			db.PS.setString(4, this.phone);
			
			db.PS.executeUpdate();
			
			String printFormat = "| %-5s | %-10s | %-15s | %-15s | %-6s |\n";
			Customer customer = new Customer(this.name, this.id, this.pwd, this.phone);
			System.out.format("+-------+------------+-----------------+-----------------+--------+\n");
			System.out.printf(printFormat, "Name", "ID", "Password", "Phone Number", "Coupon");
			System.out.format("+-------+------------+-----------------+-----------------+--------+\n");
			System.out.println(customer);
			System.out.format("+-------+------------+-----------------+-----------------+--------+\n");
			System.out.println("회원가입이 완료되었습니다.");
		} catch (Exception e) {
			e.printStackTrace(); 
		}
	}	
	
	public void findAccountID(String name, String phone) {
		try {
			String sql = "select customer_id, customer_name from customers where customer_name=? and customer_phone=?";
			db.PS = db.CN.prepareStatement(sql);
			
			db.PS.setString(1, name);
			db.PS.setString(2, phone);
			
			db.RS = db.PS.executeQuery();
			
			while(db.RS.next() == true) { 
				System.out.println(db.RS.getString("customer_name") + "님의 아이디는 " + db.RS.getString("customer_id") + " 입니다.");
			}
			
		} catch (Exception e) {
			e.printStackTrace(); 
		}
	}
	
	public void findAccountPWD(String name, String id) {
		try {
			String sql = "select customer_pwd, customer_name from customers where customer_name=? and customer_id=?";
			db.PS = db.CN.prepareStatement(sql);
			
			db.PS.setString(1, name);
			db.PS.setString(2, id);
			
			db.RS = db.PS.executeQuery();
			
			while(db.RS.next() == true) { 
				System.out.println(db.RS.getString("customer_name") + "님의 비밀번호는 " + db.RS.getString("customer_pwd") + " 입니다.");
			}
			
		} catch (Exception e) {
			e.printStackTrace(); 
		}
	}
	
}
