package BAEK.수이어가기2635;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		// N입력받기
		int N = sc.nextInt();
		// 수 이어가는 리스트 생성
		List<Integer> result = new ArrayList<>();
		result.add(N);
		// 길이 최대값 변수생성
		int max = 0;
		// M의 길이만큼 순회
		for (int M = 1; M <= N; M++) {
			// 임시리스트 생성
			List<Integer> tmp = new ArrayList<>();
			// 첫번째 숫자 입력
			tmp.add(N);
			// 두번째 숫자 입력
			tmp.add(M);
			// 멈추라고 하기 전까지 반복
			while (true) {
				int diff = tmp.get(tmp.size() - 2) - tmp.get(tmp.size() - 1);
				if (diff >= 0) {
					tmp.add(diff);
//						System.out.println("N: " + N);
				} else {
					// 반대의 경우는 음수이므로 그만!
					break;
				}
			} // 수이어가기
			// tmp와 result를 비교해서 길이가 tmp가 더 크면 result를 tmp로 대치
			if (tmp.size() >= result.size()) {
				result = tmp;
			}
		} // 순회 끝
		System.out.println(result.size());
		for (int i = 0; i < result.size(); i++)
			System.out.print(result.get(i) + " ");

	}

}
