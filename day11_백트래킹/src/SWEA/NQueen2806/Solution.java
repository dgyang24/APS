package SWEA.NQueen2806;

import java.util.Arrays;
import java.util.Scanner;

public class Solution {
	//상 하 좌 우 좌상 우상 좌하 우하
	static int[] dr = {-1,1,0,0,-1,-1,1,1};
	static int[] dc = {0,0,-1,1,-1,1,-1,1};
	//크기
	static int N;
	//visitedCheck
	static boolean[][] visited;
	//Queen 개수
	static int cntQ;
	//경우의수 카운트
	static int cnt;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int T = sc.nextInt();
		for(int tc = 1; tc <= T; tc++) {
			N = sc.nextInt();
			visited = new boolean[N][N];
			cntQ = 0;
			cnt = 0;
			
			findNQueen(0);
			System.out.println("#"+tc+" "+cnt);
			
			
		}//tc
		

	}//main
	
	static void findNQueen(int r) {
		//종료조건 cr과 cc가 끝까지 다 돌았으면 카운트한 값이 N과 같으면 찐카운트
		if(r == N) {
			if(cntQ == N)cnt++;
			return;
		}
		//재귀파트 : 백트래킹을 통해 퀸을 마킹
		for(int c = 0; c < N; c++) {
			//방문한 곳이면 패스
			if(visited[r][c]) continue;
			//현재위치 방문했음 + 못가는 곳 표시
			checkVisited(r,c,true);
			cntQ++;
			findNQueen(r+1);
			//다시 초기화
			checkVisited(r,c,false);
			cntQ= 0;
			
		}
		
		
	}//findQ
	
	static void checkVisited(int cr, int cc, boolean bool) {
		visited[cr][cc] = bool;
		//8방향으로 배열 끝까지 쳌
		for(int i = 0; i < 8; i++) {
			int nr = cr+dr[i];
			int nc = cc+dc[i];
			//범위 안에 있는동안 방문쳌
			while(nr >= 0 && nc >= 0 && nr < N && nc < N) {
				visited[nr][nc] = bool;
				nr = nr+dr[i];
				nc = nc+dc[i];
			}
		}
//		System.out.println("cr: "+cr+"cc: "+cc+"bool: "+bool+" "+"----------------------------------");
//		for(int k = 0; k < N;k++) {
//			System.out.println(Arrays.toString(visited[k]));
//		}
		
	}

}
