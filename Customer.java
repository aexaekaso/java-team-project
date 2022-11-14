package cafe;

import java.io.UnsupportedEncodingException;
import java.util.regex.Pattern;

public class Customer {
	//필드
	PrintFormat pf = new PrintFormat();
	private String id;  //기본키
	private String name;
	private String pwd;
	private String phone;
	private int coupon;
	private int couponCheck;
	
	//생성자
	public Customer() {};
	public Customer(String id, String name, String pwd, String phone, int coupon, int couponCheck) {
		this.id = id;
		this.name = name;
		this.pwd = pwd;
		this.phone = phone;
		this.coupon = coupon;
		this.couponCheck = couponCheck;
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
	public int getCouponCheck() {
		return couponCheck;
	}
	public void setCouponCheck(int couponCheck) {
		this.couponCheck = couponCheck;
	}
	
	//이것도 잠시 보류...
	//출력 형태 지정
	@Override
	public String toString() {		
		String format = "| %-" + pf.convertByte(10, name) + "s | %-15s | %-15s | %-15s | %-4s | %-8s |";
		return String.format(format, name, id, phone, coupon, couponCheck);
	}
}
