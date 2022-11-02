package cafe;

public class Product {
//Board 참고	
	// +Product()
	// +toString()

	// 상품들 코드별로 다 변수 선언?
	private int ccode = 0, bcode = 0, dcode = 0;
	private String cName = "", bName = "", dName = "";
	private int cPrice = 0, bPrice = 0, dPrice = 0;

	public Product() {} // 초기화

	// 이것은 필요한가?
	public static void Product1() {} // 커피
	public static void Product2() {} // 음료
	public static void Product3() {} // 디저트

	public Product(int ccode, int bcode, int dcode, String cName, String bName, String dName, int cPrice, int bPrice,
			int dPrice) {
		super();
		this.ccode = ccode;
		this.bcode = bcode;
		this.dcode = dcode;
		this.cName = cName;
		this.bName = bName;
		this.dName = dName;
		this.cPrice = cPrice;
		this.bPrice = bPrice;
		this.dPrice = dPrice;
	}

	
	public final int getCcode() {
		return ccode;
	}

	public final void setCcode(int ccode) {
		this.ccode = ccode;
	}

	public final int getBcode() {
		return bcode;
	}

	public final void setBcode(int bcode) {
		this.bcode = bcode;
	}

	public final int getDcode() {
		return dcode;
	}

	public final void setDcode(int dcode) {
		this.dcode = dcode;
	}

	public final String getcName() {
		return cName;
	}

	public final void setcName(String cName) {
		this.cName = cName;
	}

	public final String getbName() {
		return bName;
	}

	public final void setbName(String bName) {
		this.bName = bName;
	}

	public final String getdName() {
		return dName;
	}

	public final void setdName(String dName) {
		this.dName = dName;
	}

	public final int getcPrice() {
		return cPrice;
	}

	public final void setcPrice(int cPrice) {
		this.cPrice = cPrice;
	}

	public final int getbPrice() {
		return bPrice;
	}

	public final void setbPrice(int bPrice) {
		this.bPrice = bPrice;
	}

	public final int getdPrice() {
		return dPrice;
	}

	public final void setdPrice(int dPrice) {
		this.dPrice = dPrice;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bName == null) ? 0 : bName.hashCode());
		result = prime * result + bPrice;
		result = prime * result + bcode;
		result = prime * result + ((cName == null) ? 0 : cName.hashCode());
		result = prime * result + cPrice;
		result = prime * result + ccode;
		result = prime * result + ((dName == null) ? 0 : dName.hashCode());
		result = prime * result + dPrice;
		result = prime * result + dcode;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (bName == null) {
			if (other.bName != null)
				return false;
		} else if (!bName.equals(other.bName))
			return false;
		if (bPrice != other.bPrice)
			return false;
		if (bcode != other.bcode)
			return false;
		if (cName == null) {
			if (other.cName != null)
				return false;
		} else if (!cName.equals(other.cName))
			return false;
		if (cPrice != other.cPrice)
			return false;
		if (ccode != other.ccode)
			return false;
		if (dName == null) {
			if (other.dName != null)
				return false;
		} else if (!dName.equals(other.dName))
			return false;
		if (dPrice != other.dPrice)
			return false;
		if (dcode != other.dcode)
			return false;
		return true;
	}

	
	
	//어떻게 정리하지. 상품들이 여러가지인데
	@Override
	public String toString() {
		return "Product [ccode=" + ccode + ", bcode=" + bcode + ", dcode=" + dcode + ", cName=" + cName + ", bName="
				+ bName + ", dName=" + dName + ", cPrice=" + cPrice + ", bPrice=" + bPrice + ", dPrice=" + dPrice + "]";
	}
	
	
	/*
	 * @Override public String toString() { String result= code +", " + name + ", "
	 * + price; return result; }
	 */

}
