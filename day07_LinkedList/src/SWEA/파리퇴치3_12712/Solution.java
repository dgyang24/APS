package SWEA.파리퇴치3_12712;

import java.util.Scanner;

public class Solution {
	static int[] dr = { -1, 1, 0, 0, -1, -1, 1, 1 };
	static int[] dc = { 0, 0, -1, 1, -1, 1, -1, 1 };

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		// 테스트 케이스
		int T = sc.nextInt();
		for (int tc = 1; tc <= T; tc++) {
			// 배열의 크기 N 입력
			int N = sc.nextInt();
			// 분사 범위 M입력
			int M = sc.nextInt();
			// 벽(0)을 포함한 N+(M-1)*2 실제 크기를 설정 size
			int size = N + (M - 1) * 2;
			// 위 크기로 된 배열 생성
			int[][] arr = new int[size][size];
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					// 벽세우기
					if (i < M - 1 || j < M - 1 || i > size - M || j > size - M) {
						arr[i][j] = 0;
					}
					// 나머지는 숫자입력
					else {
						arr[i][j] = sc.nextInt();
					}
				}
//				System.out.println(Arrays.toString(arr[i]));
			}

			// 최대값
			int max = 0;
			// 배열을 실제 숫자있는 범위(M-1<=i<=size-M)동안 순회
			for (int cr = M - 1; cr <= size - M; cr++) {
				for (int cc = M - 1; cc <= size - M; cc++) {
					// sum초기화
					int sum = arr[cr][cc];
					// +모양일때 탐색 ㄱ
					for (int i = 0; i < 4; i++) {
						// 현재 위치 초기화
						int row = cr;
						int col = cc;
						// M-1번 반복
						for (int j = 1; j < M; j++) {
							int nr = row + dr[i];
							int nc = col + dc[i];
							sum += arr[nr][nc];
							row = nr;
							col = nc;
						}
					}
					// sum>max이면 max =sum
					if (sum > max) {
						max = sum;
					}
					// sum초기화
					sum = arr[cr][cc];
					// x모양일때 탐색 ㄱ
					for (int i = 4; i < 8; i++) {
						// 현재 위치 초기화
						int row = cr;
						int col = cc;
						// M-1번 반복
						for (int j = 1; j < M; j++) {
							int nr = row + dr[i];
							int nc = col + dc[i];
							sum += arr[nr][nc];
							row = nr;
							col = nc;
						}
					}
					// sum>max이면 max =sum
					if (sum > max) {
						max = sum;
					}
				}
			}
			// 출력
			System.out.println("#"+tc+" "+max);
		} // 테스트케이스 실행

	}

}
