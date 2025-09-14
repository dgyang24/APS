package 자기소계3.SWEA.NQueen2806;

import java.util.Scanner;

public class Solution_2T {
	//상하좌우 좌상 우상 좌하 우하
	static int[] dr = {-1,1,0,0,-1,-1,1,1};
	static int[] dc = {0,0,-1,1,-1,1,-1,1};
	
	static int[][] visited;
	static int N;
	static int cntQ;
	static int ans;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int T = sc.nextInt();
		
		for(int tc = 1; tc <= T; tc++) {
			N = sc.nextInt();
			visited = new int[N][N];
			ans = 0;
			cntQ = 0;
			findQ(0);
			
			System.out.println("#"+tc+" "+ans);
			
		}

	}
	
	static void findQ(int r) {
		if(cntQ == N) {
			ans++;
			return;
		}
		
		for(int c = 0; c < N; c++) {
			if(visited[r][c] != 0) continue;
			checkPoint(r,c,1);
			cntQ++;
			findQ(r+1);
			checkPoint(r,c,-1);
			cntQ--;
		}
		
	}
	
	static void checkPoint(int r, int c, int bool) {
		visited[r][c] += bool;
		
		for(int i = 0; i < 8; i++) {
			int nr = r + dr[i];
			int nc = c + dc[i];
			
			while(!(nr < 0 || nc < 0 || nr >= N || nc >= N)) {
				visited[nr][nc] += bool;
				
				nr = nr + dr[i];
				nc = nc + dc[i];
				
			}

			
		}
		
	}

}
