package 기출.공굴러가유;

import java.util.Scanner;

public class 공굴러가유_1T {
	// 델타 상 하 좌
	static int[] dr = { -1, 1, 0, 0 };
	static int[] dc = { 0, 0, -1, 1 };

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		// tc개수 T
		int T = sc.nextInt();
		for (int tc = 1; tc <= T; tc++) {
			int N = sc.nextInt();
			int[][] arr = new int[N][N];
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					arr[i][j] = sc.nextInt();
				}
			}

			// cnt 최대값
			int max = 1;

			// 배열 하나씩 순회
			for (int r = 0; r < N; r++) {
				for (int c = 0; c < N; c++) {
					// 초기 위치
					int row = r;
					int col = c;
					int cnt = 1;
					// 범위에서 벗어나기 전까지 이동
					while (true) {
						int min = arr[row][col];
						int mRow = row;
						int mCol = col;
						// 4방탐색하면서 가장 작은 값 및 위치 저장
						for (int i = 0; i < 4; i++) {
							int nr = row + dr[i];
							int nc = col + dc[i];
							if (nr >= 0 && nc >= 0 && nr < N && nc < N) {
								if (arr[nr][nc] < min) {
									min = arr[nr][nc];
									mRow = nr;
									mCol = nc;
								}
							}

						}//4방 끝
						//더이상 최소 위치가 갱신되지 않으면 break;
						if(row == mRow && col == mCol)
							break;
						//이동
						row = mRow;
						col = mCol;
						cnt++;
						
					} // 돌굴리기 끝
					// max보다 크면갱신
					max = Math.max(max, cnt);

				}
			}
			// 출력
			System.out.println("#" + tc + " " + max);

		} // tc

	}

}
