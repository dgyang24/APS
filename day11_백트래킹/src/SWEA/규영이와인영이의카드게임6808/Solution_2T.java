package SWEA.규영이와인영이의카드게임6808;

import java.util.Scanner;

public class Solution_2T {
	static int[] A; // 규영
	static int[] B; // 인영
	static int[] tmp;
	static boolean[] visited;
	static int winCnt;
	static final int TOTAL = fact(9);

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int T = sc.nextInt();
		for (int tc = 1; tc <= T; tc++) {
			A = new int[9];
			B = new int[9];
			tmp = new int[9];
			visited = new boolean[9];
			winCnt = 0;
			for (int i = 0; i < 9; i++)
				A[i] = sc.nextInt();
			int idx = 0;
			for (int i = 1; i <= 18; i++) {
				boolean isIncluded = false;
				for (int j = 0; j < 9; j++) {
					if (A[j] == i) {
						isIncluded = true;
						break;
					}

				}
				if (!isIncluded)
					B[idx++] = i;
			}

			playGame(0);
			System.out.println("#" + tc + " " + winCnt + " " + (TOTAL - winCnt));

		} // tc

	}// main

	static void playGame(int idx) {
		// 종료조건
		if (idx == 9) {
			int scoreA = 0;
			int scoreB = 0;
			for (int i = 0; i < 9; i++) {
				if (A[i] > tmp[i]) scoreA += A[i] + tmp[i];
				else scoreB += A[i] + tmp[i];
			}
			if (scoreA > scoreB) winCnt++;
		}
		// 재귀파트
		for (int i = 0; i < 9; i++) {
			if (visited[i]) continue;
			visited[i] = true;
			tmp[idx] = B[i];
			playGame(idx + 1);
			visited[i] = false;
		}

	}

	static int fact(int n) {
		if (n == 1) return 1;
		return n * fact(n - 1);
	}

}
