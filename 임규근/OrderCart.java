package cafeProject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class OrderCart {
	// 필드 선언
	public static ArrayList<Product> products; // 여기에 담아서 추후에 결제
	public static ArrayList<Integer> amount; // 수량
	public static Queue<Integer> choice = new LinkedList<Integer>();

	// 기본 생성자
	public OrderCart() {

	}

	// 초기화 생성자
	// static이라서 필요가 없음.

	// 메서드
	// 1.장바구니에 담는 메서드
	public void orderCartAdd() throws NumberFormatException, IOException {
		// Jdbc 객체 생성
		Jdbc jdbc = new Jdbc();

		// bufferedReader
		jdbc.br = new BufferedReader(new InputStreamReader(System.in));

		// 변수선언
		int code = 0; // 코드선택
		int amount1 = 0; // 수량

		// 코드 선택 및 수량 선택
		System.out.println("메뉴를 선택하세요>>> \n(코드를 입력하세요 - 숫자)");
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

	// 3. 장바구니를 비우는 메서드
	public void orderCartClear() {
		
	}

	// 4. 장바구니를 보여주는 메서드
	public void orderCartShow() {

	}
}
