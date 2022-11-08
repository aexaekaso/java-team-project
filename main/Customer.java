package cafe;

import java.util.regex.Pattern;

public class Customer {
	//필드
	private String id;  //기본키
	private String name;
	private String pwd;
	private String phone;
	private int coupon;
	
	//생성자
	public Customer() {};
	public Customer(String id, String name, String pwd, String phone, int coupon) {
		this.id = id;
		this.name = name;
		this.pwd = pwd;
		this.phone = phone;
		this.coupon = coupon;
	}
	
	//getter & setter
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getCoupon() {
		return coupon;
	}
	public void setCoupon(int coupon) {
		this.coupon = coupon;
	}
	
	
	//이것도 잠시 보류...
	//출력 형태 지정
	@Override
	public String toString() {		
		String koFormat = "| %-7s | %-15s | %-15s | %-15s | %6s |\n";
		String enFormat = "| %-10s | %-15s | %-15s | %-15s | %6s |\n";
		
		if (Pattern.matches("^[가-힣]*$", name)) {
			return String.format(koFormat, name, id, pwd, phone, coupon);
		} else {
			return String.format(enFormat, name, id, pwd, phone, coupon);
		}
		
	}
}