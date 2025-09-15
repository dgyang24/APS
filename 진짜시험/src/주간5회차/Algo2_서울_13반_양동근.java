package 주간5회차;

import java.util.Scanner;

public class Algo2_서울_13반_양동근 {
	//델타 : 상하좌우 좌상 우상 좌하 우하
	static int[] dr = {-1,-1,0,0,-1,-1,1,1};
	static int[] dc = {0,0,-1,1,-1,1,-1,1};
	
	//체스판
	static int[][] board;
	//크기
	static int N;
	//킹을 안겹치게 놓는 경우의 수
	static int cnt;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int T = sc.nextInt();
		
		for(int tc = 1; tc <= T; tc++) {
			N =sc.nextInt();
			cnt = 1; //아무것도 안놓는 경우도 있으므로 1로 초기화
			board = new int[N][N];
			
			dfs(0);
			
			System.out.println("#"+tc+" "+cnt);
			
			
			
		}//tc
		

	}//main

	static void dfs(int r) {
		//종료조건: r == N
		if(r == N) return;
		
		//행별로 체크
		for(int c = 0; c < N; c++) {
			//방문쳌
			if(board[r][c] != 0) continue;
			//킹의 사거리 쳌
			checkAll(r,c,1);
			//경우의 수 추가!
			cnt++;
			//현재행 
			dfs(r);
			//다음 행 재귀
			dfs(r+1);
			//사거리 쳌했던거 풀기
			checkAll(r,c,-1);
		}
		
	}
	
	//킹의 사거리 모두 체크
	static void checkAll(int r, int c, int bool) {
		//현재위치 쳌
		board[r][c] += bool;
		
		//8방탐색하면서 킹의 모든 사거리 체크
		for(int i = 0; i < 8; i++) {
			int nr = r + dr[i];
			int nc = c + dc[i];
		
			//범위쳌
			if(nr < 0 || nc < 0 || nr >= N || nc >= N) continue;
			//범위 안에 있으면 체크할거야~
			board[nr][nc] += bool;
		}
		
	}
	//같은 행에 더 놓을 곳이 있니~
	static boolean checkZero(int r) {
		for(int i = 0; i < N; i++) {
			if(board[r][i] == 0) return true;
		}
		return false;
	}
}