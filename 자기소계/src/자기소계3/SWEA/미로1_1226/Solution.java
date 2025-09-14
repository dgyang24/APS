package 자기소계3.SWEA.미로1_1226;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Solution {
	static int[] dr = {-1,1,0,0};
	static int[] dc = {0,0,-1,1};
	
	static char[][] maze;
	static boolean[][] visited;
	static int[] start;
	static int isPossible;
	static Queue<int[]> q;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		for(int k = 1; k <= 10; k++) {
			int tc = sc.nextInt();
			maze = new char[16][16];
			visited = new boolean[16][16];
			start = new int[2];
			isPossible = 0;
			for(int i = 0; i < 16; i++) {
				String str = sc.next();
				for(int j = 0; j < 16; j++) {
					char c = str.charAt(j);
					maze[i][j] = c;
					if(c == '2') {
						start[0] = i;
						start[1] = j;
					}
				}
			}
			//초기화 
			
			bfs(1,1);
			System.out.println("#"+tc+" "+isPossible);
			
			
		}//tc

	}//main
	
	//bfs
	static void bfs(int r,int c) {
		q = new LinkedList<>();
		q.add(new int[]{r,c});
		visited[r][c] = true;
		
		while(!q.isEmpty()) {
			int[] curr = q.poll();
			
			//도착지 찾으면 끝
			if(maze[curr[0]][curr[1]] == '3') {
				isPossible = 1;
				return;
			}
//			System.out.println(Arrays.toString(curr));
			//4방 탐색할건데 방문 안했고, 1이 아니면 가(추가)
			for(int i = 0; i < 4; i++) {
				int nr = curr[0] + dr[i];
				int nc = curr[1] + dc[i];
				
				//범위탐색
				if(nr < 0 || nc < 0 || nr >= 16 || nc >= 16) continue;
//				System.out.println("nr: " + nr + " nc: " + nc);
//				System.out.println(maze[nr][nc]);
				//방문 안했고 1이 아니면 q추가
				if(!visited[nr][nc] && maze[nr][nc] != '1') {
					q.add(new int[] {nr,nc});
					visited[nr][nc] = true;
				}
					
			}
			
		}//while
		
	}

}
