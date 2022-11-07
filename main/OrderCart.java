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
			// Jdbc 객체 생성
			Jdbc jdbc = new Jdbc();

			// bufferedReader
			jdbc.br = new BufferedReader(new InputStreamReader(System.in));

			// 변수선언
			int code = 0; // 코드선택
			int amount1 = 0; // 수량

			// 코드 선택 및 수량 선택
			System.out.println("메뉴를 선택하세요>>> \n(코드를 입력하세요 - 숫자)");
			inputNum = Integer.parseInt(br.readLine());


			code = Integer.parseInt(jdbc.br.readLine()); // 코드 입력

			System.out.println("수량을 선택하세요>>> \n(숫자를 입력하세요)"); // 다른거 치는 경우도 고려할 시, 추후 변경
			amount1 = Integer.parseInt(jdbc.br.readLine()); // 수량 입력

			jdbc.br.close();
			// 장바구니에 넣기
			products.add(jdbc.selectProduct(code));
			amount.add(amount1);

		}// orderCartAdd()
		

		// 2. 수량을 변경 혹은 삭제하는 메서드
		public void orderCartChange(int code) throws NumberFormatException, IOException {
			// Jdbc 객체 생성
			Jdbc jdbc = new Jdbc();

			// bufferedReader
			jdbc.br = new BufferedReader(new InputStreamReader(System.in));

			// 변수선언
			int tmp = 0; // 임시 값
			int amount1 = 0; // 변경 수량

			// 변경 혹은 삭제 선택
			System.out.println("1. 수량 변경, 2. 삭제");
			System.out.println("코드(숫자)를 입력하세요>>> ");
			tmp = Integer.parseInt(jdbc.br.readLine()); // 값 입력

			// 처리
			if (tmp == 1) { // 수량 변경 시
				// 수량 입력
				System.out.println("변경할 수량을 입력하세요>>> ");
				amount1 = Integer.parseInt(jdbc.br.readLine());
				jdbc.br.close();

				//같은 코드시 처리
				for (int i = 0; i < products.size(); i++) {
					tmp = products.get(i).code; // i번째 코드를 가져옴

					if (tmp == code) {
						amount.add(i, amount1); // 수량 변경
						break;
					} // if.for.if

				} // if.for
			} else if (tmp == 2) {// 삭제시

				for (int i = 0; i < products.size(); i++) {
					tmp = products.get(i).code; // i번째 코드를 가져옴

					if (tmp == code) {
						products.remove(i); // 메뉴 삭제
						amount.remove(i); // 수량 삭제
						break;
					} // if.for.if

				}//if.for
			}else {
				System.out.println("잘못된 코드입니다.");
			}

		}
	
	
	
	
	// 3. 장바구니를 비우는 메서드 // 
	public void orderCartClear() { // N을 입력했을때
		products.clear();
		amount.clear();
		System.out.println("장바구니가 비워졌습니다.");
		/* public static ArrayList<Product> products; // 여기에 담아서 추후에 결제
		public static ArrayList<Integer> amount; // 수량
		을 1. 비우고 2. 출력을 장바구니비우는 / 에리리스트로 delete */
	}
	
	
	// 4. 장바구니를 보여주는 메서드
	public void orderCartShow() { // 2. 장바구니 입력했을 때
		System.out.println("============장바구니 목록============");
		System.out.printf("| %-5d | %-15s | %-6d | %-6d |", "번호", "메뉴", "수량", "가격");
		
		// String coffeestring="";
		// String beveragetring="";
		// String dessertstring="";
		String temp="";			
		
		int no=0;
		String mene="";
		int amount1 =0;
		int price=0;
		

		for(int i=0; i<amount.size(); i++) {
			no=products.get(i).code;
			mene=products.get(i).name;
			amount1=amount.get(i);
			price=amount1*products.get(i).price;
			
			System.out.printf("| %-5d | %-15s | %-6d | %-6d |", no, mene, amount1, price); 
			System.out.println("==================================");
		}
		//변수에 "번호", "메뉴", "수량", "가격" 불러온다. > 출력
		
		
		//	ArrayList<Product> products = products
		//for(Product product : products) {
		//	System.out.println(Product);
		}
		
		// 종류가 구분이 안되니까, 커피,음료,디저트 구분되게 스트링에 담아둠.
		
		/* public static ArrayList<Product> products; // 여기에 담아서 추후에 결제
		public static ArrayList<Integer> amount; // 
				
		프로덕트스를 to 스트링으로 와일문으로 불러와서 어마운트도 같이 출력되게
		
		3개 커피, 디젙, 음료 .amount  필드, 생성자. 기본 생성자, 초기화생성자. 
		자식들을 클래스를 생성하게 끔
		프로덕트 어레이리스트로 클래스가 다르더라도 객체를 만들어서 담아둘수 있게.
		넣을때, 
		출력할때는  프로덕트 어레이리스트 클래스는 프로덕트, 자식껄로 넣는다. 클래스 확인해서 > 커피클래스면 커피, 음료면 음료, 디저트면 디저트
		
		넣는순서에따라 따라 코드가 달라질 수있다. 문제없다. */ 
		 
		
	
}
