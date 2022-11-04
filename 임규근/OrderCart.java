package cafeProject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class OrderCart {
	//필드 선언
	public static ArrayList<Product> products; // 여기에 담아서 추후에 결제
	public static ArrayList<Integer> amount; // 수량
	public static Queue<Integer> choice = new LinkedList<Integer>();
	
	//기본 생성자
	public OrderCart() {

	}
	
	//초기화 생성자
	// static이라서 필요가 없음.
	
	//메서드
	// 1.장바구니에 담는 메서드
	public void orderCartAdd() throws NumberFormatException, IOException {
		//bufferedReader
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		//변수선언
		int inputNum =0; // 코드선택
		
		//코드 선택
		System.out.println("메뉴를 선택하세요>>> \n(코드를 입력하세요 - 숫자)");
		inputNum = Integer.parseInt(br.readLine());
		
		
	}// orderCartAdd()
	
	// 2. 수량을 변경 혹은 삭제하는 메서드
	public void orderCartChange() {
		
	}
	
	// 3. 장바구니를 비우는 메서드
	public void orderCartClear() {
		
	}
	
	// 4. 장바구니를 보여주는 메서드
	public void orderCartShow() {
		
	}
}
