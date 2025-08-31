package 자기소계2.SWEA.베이비진게임5203;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Solution {
	static Map<Integer, Integer> p1;
	static Map<Integer, Integer> p2;
	static int winner;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int T = sc.nextInt();
		for (int tc = 1; tc <= T; tc++) {
			// p1, p2 초기화
			p1 = new HashMap<>();
			p2 = new HashMap<>();
			for (int i = 0; i <= 9; i++) {
				p1.put(i, 0);
				p2.put(i, 0);
			}
			// 승자여부 초기화
			winner = 0;
			// 12번동안게임 진행
			for (int i = 0; i < 6; i++) {
				int n = sc.nextInt();
				int n2 = sc.nextInt();
				// 카드뽑기
				p1.put(n, p1.get(n) + 1);
				p2.put(n2, p2.get(n2) + 1);
				// p1부터 검사
				// run or triplet 조건 충족시 winner 1 및 브렠
				if (isRun(p1) || isTriplet(p1)) {
					winner = 1;
					break;
				}
				// p2도 똑같이
				if (isRun(p2) || isTriplet(p2)) {
					winner = 2;
					break;
				}
			} // 게임 끝
				// 출력
			System.out.println("#" + tc + " " + winner);

		}
	}

	// run 연속숫자 3개 인가?
	static public boolean isRun(Map<Integer, Integer> p) {
		// 0~9-2까지 조회하면서 연속숫자가 3개 있으면 return true
		for (int i = 0; i <= 7; i++) {
			boolean isRun = true;
			for (int j = i; j < i + 3; j++) {
				if (p.get(j) == 0) {
					isRun = false;
					break;
				} 
			}
			if (isRun) {
				return true;
			}
		}
		return false;
	}

	// triplet 같은숫자 3개인가?
	static public boolean isTriplet(Map<Integer, Integer> p) {
		// 0~9까지 순회하면서 각 밸류 값이 3이면 트루반환
		for (int i = 0; i <= 9; i++) {
			if (p.get(i) >= 3)
				return true;
		}
		return false;
	}
}
