package 자기소계3.SWEA.NQueen2806;

import java.util.Scanner;

public class Solution {
	//델타 : 상, 하, 좌, 우, 좌상, 우상, 좌하, 우하
	static int[] dr = {-1,1,0,0,-1,-1,1,1};
	static int[] dc = {0,0,-1,1,-1,1,-1,1};
	
	static int[][] visited; //방문 쳌
	static int N;//  보드판 크기
	static int cntQ;//퀸 배치 횟수
	static int ans; //N개의 퀸이 배치되는 경우의 수  
	

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int T = sc.nextInt();
		for(int tc = 1; tc <= T; tc++) {
			N = sc.nextInt();
			visited = new int[N][N];
			cntQ = 0;
			ans = 0;
			//초기화
			
		findQueen(0);
		
		System.out.println("#"+tc+" "+ans);
			
			
		}// tc
		
	}//main
	
	//퀸찾자
	static void findQueen(int r) {
		//종료 조건 : r == N 일 때, cntQ == N 이면 정답처리
		if(r == N) {
			if(cntQ == N) ans++;
			return;
		}
		
		
		
		// 재귀 파트: 백트래킹 ㄱㄱ 
		// 열만 움직일거야
		for(int c = 0; c < N; c++) {
			// 방문하지 않은 곳에만 놓고
			if(visited[r][c] != 0) continue;
			// 현재 위치 및 갈 수 있는 방향 방문 체크
			checkAll(r,c, 1);
			// cntQ++
			cntQ++;
			//다음행 (재귀)
			findQueen(r+1);
			//현재위치로 갈 수 있는 곳 되돌려 놓기 + cntQ--
			checkAll(r,c, -1);
			cntQ--;
		}
	}//findQueen
	
	//방문쳌
	static void checkAll(int cr, int cc, int bool) {
		//현재 위치 쳌
		visited[cr][cc] += bool;
		//현재위치로부터 8방탐색하면서 퀸 사거리 모두 체크 표시
		for(int i = 0; i < 8; i++) {
			int nr = cr + dr[i];
			int nc = cc + dc[i];
			
			//범위안에 있을 동안 갈 수 있는 8방향을 모두 체크 할거야
			while(nr >= 0 && nc >= 0 && nr < N && nc < N) {
				visited[nr][nc] += bool;
				
				nr = nr + dr[i];
				nc = nc + dc[i];
			}
			
			
			
		}
	}
	
}
