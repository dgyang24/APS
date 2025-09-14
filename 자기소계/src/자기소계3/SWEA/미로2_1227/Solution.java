package 자기소계3.SWEA.미로2_1227;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Solution {
	static int[] dr = {-1,1,0,0};
	static int[] dc = {0,0,-1,1};
	
	static char[][] maze;
	static boolean[][] visited;
	static int isP;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		for(int tc = 1; tc<=10; tc++) {
			int K = sc.nextInt();
			maze = new char[100][100];
			visited = new boolean[100][100];
			isP = 0;
			int[] start = new int[2];
			for(int i = 0; i < 100; i++) {
				String str = sc.next();
				for(int j = 0; j < 100; j++) {
					char c = str.charAt(j);
					maze[i][j] = c;
					if(c == '2') {
						start[0] = i;
						start[1] = j;
					}
				}
//				System.out.println(Arrays.toString(maze[i]));
			}
			//초기화
			bfs(start[0], start[1]);
			System.out.println("#"+K+" "+isP);
		}//tc
		sc.close();
		

	}//main
	
	static void bfs(int r, int c) {
		Queue<int[]> q = new LinkedList<>();
		q.add(new int[]{r,c});
		boolean isFind = false;
		
		while(!isFind && !q.isEmpty()) {
			int[] curr = q.poll();
			int cr = curr[0];
			int cc = curr[1];
			visited[cr][cc]=true;
			
			//4방탐색하면서 찾으면 q에 집어 넣어
			for(int i = 0; i < 4; i++) {
				int nr = cr + dr[i];
				int nc = cc + dc[i];
				
				//범위탐색
				if(nr < 0 || nc < 0 || nr >= 100 || nc >= 100) continue;
				//방문탐색
				if(visited[nr][nc] || maze[nr][nc] == '1') continue;
				//만약 다음이동위치가 3이면 처리해
				if(maze[nr][nc] == '3') {
					isP++;
					isFind = true;
					break;
				}
				//다 아니면 넣어!
				q.add(new int[] {nr, nc});
			}
		
		}
		
	}

}
