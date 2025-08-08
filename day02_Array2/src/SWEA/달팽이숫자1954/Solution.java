package SWEA.달팽이숫자1954;

import java.io.File;
import java.util.Scanner;

public class Solution {
	public static int[] dr = { 0, 1, 0, -1 }; // 우하좌상
	public static int[] dc = { 1, 0, -1, 0 }; // 우하좌상

	public static void main(String[] args) throws Exception {
		File file = new File("./src/SWEA/달팽이숫자1954/input.txt");
		Scanner sc = new Scanner(file);

		int T = sc.nextInt();
		for (int tc = 1; tc <= T; tc++) {
			int N = sc.nextInt(); // 달팽이 크기
			int[][] snail = new int[N][N];
			int M = N; // 이동거리 처음엔 N만큼 이동해서 수평 -> 수직일때! -1씩 줄어들거임!
			int D = 0; // 방향
			int row = 0;
			int col = 0;
			int num = 1;// 달팽이 흔적
			// 달팽이 배열채우기 시작~!
			for (int n = 1; n <= N * N; n++) {
				// D가 만약 수평-> 수직 => 즉 하, 상방향일 때 이동거리가 줄음
				if (D % 2 == 1)
					M--;
				snail[row][col] = num;
				// D방향으로 M만큼 이동할거양~!
				for (int i = 0; i < M; i++) {
					// D방향으로 이동할건데 오류검사하고 이동 ㄱㄱㄱ
					int nr = row + dr[D];
					int nc = col + dc[D];
					if (nr >= 0 && nc >= 0 && nr < N && nc < N) {
						row = nr;
						col = nc;
						snail[row][col] = ++num;
					}
				}
				// 이동 다 했으면 방향 바꾸자!
				D = (D + 1) % 4;

			}
			// 다 채웠으면 출력해보자.
			System.out.println("#" + tc);
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					System.out.print(snail[i][j] + " ");
				}
				System.out.println();
			}

		}

	}

}
