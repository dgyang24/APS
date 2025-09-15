package 주간5회차;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Algo1_서울_13반_양동근 {
	//델타 : 상 하 좌 우
	static int[] dr = {-1,1,0,0};
	static int[] dc = {0,0,-1,1};
	//미로
	static int[][] maze;
	//크기
	static int N;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int T = sc.nextInt();
		for(int tc = 1; tc<=T; tc++) {
			N = sc.nextInt();
			int ans = 0; //정답
			maze = new int[N][N];
			for(int i = 0; i < N; i++) {
				for(int j = 0; j < N; j++) {
					maze[i][j] = sc.nextInt();
				}
//				System.out.println(Arrays.toString(maze[i]));
			}
			//초기화
			
			//bfs 실행 -> 최소거리 출력
			bfs(0,0);
			
			//만약 못탈출하면 ans = -1
			if(maze[N-1][N-1] == 0) ans = -1; 
			else ans = maze[N-1][N-1];
			//출력
			System.out.println("#"+tc+" "+ans);
//			for(int i = 0; i < N; i++) {
//				System.out.println(Arrays.toString(maze[i]));
//			}
			
		}//tc

	}//main
	
	static void bfs(int r, int c) {
		//미로 갈 곳 저장할 큐
		Queue<int[]> q = new LinkedList<>();
		//첫번째 넣고 방문쳌
		q.add(new int[] {r,c});
		//갈 길이 있을 동안~
		while(!q.isEmpty()) {
			//현재위치
			int[] curr = q.poll();
			int cr = curr[0];
			int cc = curr[1];
			
			//4방탐색
			for(int i = 0; i < 4; i++) {
				int nr = cr + dr[i];
				int nc = cc + dc[i];
						
				//범위 쳌
				if(nr < 0 || nc < 0 || nr >= N || nc >= N) continue;
				//길확인 + 방문 햇니~
				if(maze[nr][nc] == 0) {
					//둘 다 아니면 갈거야~ + 거리기록
					maze[nr][nc] = maze[cr][cc] + 1;
					//그리고 넣어
					q.add(new int[] {nr,nc});
				}
				
			}
			
			
			
		}//while
		
		
		
	}

}
