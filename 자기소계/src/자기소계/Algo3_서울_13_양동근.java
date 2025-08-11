package 자기소계;

import java.util.Scanner;

public class Algo3_서울_13_양동근 {

	// 팔방 탐색을 위한 델타
	static int[] dr = { -1, 1, 0, 0, -1, -1, 1, 1 };
	static int[] dc = { 0, 0, -1, 1, -1, 1, -1, 1 };

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int T = sc.nextInt();

		// T만큼 반복
		for (int tc = 1; tc <= T; tc++) {
			// 배열 크기 N 받기
			int N = sc.nextInt();
			// K줄 크기
			int K = sc.nextInt();
			// 던지는 위치
			int r = sc.nextInt();
			int c = sc.nextInt();
			// 초기 돌의 무게(값)
			int a = sc.nextInt();
			// 퍼져나가는 힘(줄어드는 값)
			int b = sc.nextInt();
			// N+2크기의 배열 생성
			int[][] arr = new int[N + 2][N + 2];

			for (int i = 0; i < arr.length; i++) {
				for (int j = 0; j < arr.length; j++) {
					// -1 벽 세우기
					if (i == 0 || j == 0 || i > N || j > N)
						arr[i][j] = -1;
				}
			}
			// 시작점
			arr[r][c] = a;

			// 합
			int sum = 0;
			for (int k = 0; k < 8; k++) {
				int nr = r + dr[k];
				int nc = c + dc[k];

				System.out.println("#" + tc + " " + sum);

			}
		}

	}

}
