package SWEA.규영이와인영이의카드게임6808;

import java.util.Arrays;
import java.util.Scanner;

public class Solution {

	static int[] A; // 규영
	static int[] B; // 인영이 원본
	static int[] tmpB; // 인영이 카드 섞을 배열
	static boolean[] visited; // 인영이 뭐냈는지 방문기록
	static int win; // 규영이가 이긴 횟수
	static final int TOTAL = factorial(9);// 모든 게임 수

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int T = sc.nextInt();
		for (int tc = 1; tc <= T; tc++) {
			A = new int[9];
			B = new int[9];
			tmpB = new int[9];
			visited = new boolean[9];
			win = 0;

			// 각 카드 초기화
			for (int i = 0; i < 9; i++) {
				A[i] = sc.nextInt();
			}
			// 인영이는 규영이 가지고 있는 건 못가져잉
			int idx = 0;
			for (int i = 1; i <= 18; i++) {
				boolean isIncluded = false;
				for (int j = 0; j < 9; j++) {
					if (i == A[j]) {
						isIncluded = true;
						break;
					}
				}
				if (!isIncluded)
					B[idx++] = i;
			}
			// 초기화 끝
//			System.out.println(Arrays.toString(B));
			// 카드게임 시작하고
			playGame(0);
			// 규영이가 이긴횟수 진 횟수 출력
			System.out.println("#" + tc + " " + win + " " + (TOTAL-win));

		} // tc

	}// main

	// 카드게임 시작
	static void playGame(int idx) {
		// 종료조건 : 인영이의 카드 묶음을 섞을 때마다 게임 실행
		if (idx == 9) {
			// 게임시작
			// 규영 인영 각 총점
			int scoreA = 0;
			int scoreB = 0;
			// 9라운드동안 진행
			// 규영 vs 인영 0-> 9
			for (int i = 0; i < 9; i++) {
				// 숫자 큰 놈은 점수 먹기
				if (A[i] > tmpB[i])
					scoreA += A[i] + tmpB[i];
				else
					scoreB += A[i] + tmpB[i];
			}
			// 경기 종료 후 규영이가 이겼으면 winCnt++
			if (scoreA > scoreB) win++;
		}

		// 재귀부분: 인영이 카드 섞어야겠지? 백트래킹
		for (int i = 0; i < 9; i++) {
			// 이미 바뀐 부분은 패스!
			if (visited[i])
				continue;
			//방문안한 인덱스 체크하고
			visited[i] = true;
			// 인영이의 값을 하나씩 돌리기
			tmpB[idx] = B[i];
			
			playGame(idx + 1);
			// 다시 원상복구
			visited[i] = false;

		}

	}

	// 특정 숫자의 팩토리얼 구하기
	static int factorial(int n) {
		// 종료조건
		if (n == 1)
			return 1;
		// 재귀
		return n * factorial(n - 1);
	}
}
