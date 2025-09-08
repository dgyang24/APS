package SWEA.파핑파핑지뢰찾기1868;

import java.util.Arrays;
import java.util.Scanner;

public class Solution {
	static char[][] table;// 지뢰밭
	static boolean[][] visited; // 지뢰체크
	static int[][] nums; // 각 칸의 주변 지뢰 개수
	static int N; // 지뢰판 크기

	// 상,하,좌,우,좌상,우상,좌하,우하
	static int[] dr = { -1, 1, 0, 0, -1, -1, 1, 1 };
	static int[] dc = { 0, 0, -1, 1, -1, 1, -1, 1 };

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int T = sc.nextInt();
		for (int tc = 1; tc <= T; tc++) {
			N = sc.nextInt();
			table = new char[N][N];
			nums = new int[N][N];
			visited = new boolean[N][N];
			for (int i = 0; i < N; i++) {
				String s = sc.next();
				for (int j = 0; j < N; j++) {
					table[i][j] = s.charAt(j);
				}
//				System.out.println(Arrays.toString(table[i]));
			} // 초기화

			// 1. 각 칸마다 지뢰개수 계산
			findBomb();

			int cnt = 0;
			
			//2. 방문하지 않았고, 지뢰 아니고, 주변 지뢰가 0인경우 dfs 탐색 -> 연결된 폭탄 다 터트리기
			for(int r = 0; r < N; r++) {
				for(int c = 0; c < N; c++) {
					if(!visited[r][c] && table[r][c] == '.' && nums[r][c] == 0) {
						cnt++;
						dfs(r,c);
					}
					
				}
			}
			
			//3. 나머지 경우는 방문하지 않았고, 폭탄이 아니면++ 			
			for(int r = 0; r < N; r++) {
				for(int c = 0; c < N; c++) {
					if(!visited[r][c] && table[r][c] == '.')
						cnt++;
				}
			}
			System.out.println("#" + tc + " " + cnt);

		} // tc

	}// main

	// 지뢰 개수 계산
	static void findBomb() {
		for (int r = 0; r < N; r++) {
			for (int c = 0; c < N; c++) {
				// 지뢰
				if (table[r][c] == '*')
					nums[r][c] = -1;
				else {
					int bomb = 0;
					for (int i = 0; i < 8; i++) {
						int nr = r + dr[i];
						int nc = c + dc[i];

						// 범위 안에 있고, 지뢰찾으면 bomb++
						if (nr >= 0 && nc >= 0 && nr < N && nc < N && table[nr][nc] == '*')
							bomb++;
					}
					// 개수 할당
					nums[r][c] = bomb;
//					System.out.println(" r: " + r + " c: " + c + " nums: " + nums[r][c]);

				}
			}
		}

	}// 지뢰 찾기

	// dfs
	static void dfs(int r, int c) {
		//일단 방문 기록
		visited[r][c] = true;
		//주변 개수가 0이 아닐 때는 넘어가.
		if(nums[r][c] != 0) return;
		// 8방 탐색하면서 주변 값이 = 범위 안에 있고, 방문하지 않았고, 폭탄아니면 재귀
		for(int i = 0; i < 8; i++) {
			int nr = r + dr[i];
			int nc = c + dc[i];
			
			if(nr >= 0 && nc >= 0 && nr < N && nc < N && !visited[nr][nc] && table[nr][nc] == '.')
				dfs(nr, nc);
			
		}
	}

}
