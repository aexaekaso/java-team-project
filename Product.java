package cafe;

public class Product {
	//필드 선언
	PrintFormat pf = new PrintFormat();
	int code;
	String name;
	int price;
	
	//기본 생성자
	public Product() {
		
	}
	
	//초기화 생성자
	public Product(int code, String name, int price) {
		this.code = code;
		this.name = name;
		this.price = price;
	}
	
	//toString 오버라이딩
	@Override
	public String toString() {
		return String.format("| %-6d | %-" + pf.convertByte(25, name) + "s | %-6d |", code, name, price);
//		return String.format("| %-6d | %-30s | %-6d |", code, name, price);
	}		
}
