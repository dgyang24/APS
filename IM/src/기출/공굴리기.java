package 기출;

import java.util.Arrays;
import java.util.Scanner;

public class 공굴리기 {
	// 델타탐색 상하좌우
	static int[] dr = { -1, 1, 0, 0 };
	static int[] dc = { 0, 0, -1, 1 };

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		// tc개수 T받기
		int T = sc.nextInt();
		// 테스트케이스 실행
		for (int tc = 1; tc <= T; tc++) {
			// 크기 N 입력
			int N = sc.nextInt();
			// 크기가 N*N인 배열 생성
			int[][] arr = new int[N][N];
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					// 입력값받기
					arr[i][j] = sc.nextInt();
				}
//				System.out.println(Arrays.toString(arr[i]));
			}
			// 최대값 비교
			int max = Integer.MIN_VALUE;
			// N*N만큼 배열을 순회하면서 델타탐색 시작
			for (int r = 0; r < N; r++) {
				for (int c = 0; c < N; c++) {
					// 현재 위치
					int row = r;
					int col = c;
					// 카운트 초기화 본인부터 1로 세기
					int cnt = 1;
					// while(공굴릴 곳이 없을 때 까지)
					while (true) {
						// 적은 값 비교
						int min = Integer.MAX_VALUE;
						int nr = 0;
						int nc = 0;
						// 현재위치로부터 델타 4방향 진행하고
						for (int i = 0; i < 4; i++) {
							nr = row + dr[i];
							nc = col + dc[i];
							// 인덱스 에러 방지
							if (nr >= 0 && nc >= 0 && nr < N && nc < N) {
								// 4방향중 제일 값이 적은값 갱신
								if (arr[nr][nc] < min)
									min = arr[nr][nc];
							}
						}
						// 적은 값을 현재 위치와 비교해서 작으면 이동 + count++
						if (min < arr[row][col]) {
							// 델타탐색으로 min의 좌표를 찾고 거기로 다시 이동
							for (int i = 0; i < 4; i++) {
								nr = row + dr[i];
								nc = col + dc[i];
								// 인덱스 에러 방지
								if (nr >= 0 && nc >= 0 && nr < N && nc < N) {
									// 4방향중 제일 값이 적은값 갱신
									if (arr[nr][nc] == min)
										break;
								}
							}
							// 이동
							row = nr;
							col = nc;
//							System.out.println("nr: " + nr + "nc: " + nc);
							cnt++;
						} else {
							// 최솟값이 없으면 공굴리기 끝
							break;
						}
					} // 공굴리기 끝
						// 최대값비교해서 갱신
					if (cnt > max)
						max = cnt;
				} // 열
			} // 배열 순회 끝

			System.out.println("#" + tc + " " + max);

		} // 테스트 케이스 실행

	}

}
