package 자기소계4.BAEK.적록색약10026;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {
	static int[] dr = { -1, 1, 0, 0 };
	static int[] dc = { 0, 0, -1, 1 };
	static boolean[][] visited;
	static int N;
	static char[][] board;
	static char[][] board2;
	static int ans1, ans2;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		board = new char[N][N];
		board2 = new char[N][N];
		visited = new boolean[N][N];
		ans1 = ans2 = 0;
		for (int i = 0; i < N; i++) {
			String str = sc.next();
			for (int j = 0; j < N; j++) {
				char c = str.charAt(j);
				board[i][j] = c;
				if(c == 'R' || c == 'G') {
					board2[i][j] = 'R';
				}else {
					board2[i][j] = c;
				}
			}
		}
		// 초기화
		
		//2차원 배열을 순회하면서, 방문하지 않았으면 bfs ->ans+1
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				//방문쳌
				if(visited[i][j]) continue;
				bfs(i,j,board);
				ans1++;
				
			}
		}
		//방문기록 초기화
		visited = new boolean[N][N];
		//적록색약 version
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				//방문쳌
				if(visited[i][j]) continue;
				bfs(i,j,board2);
				ans2++;
				
			}
		}
		
		System.out.println(ans1 + " " + ans2);
		
	}

	static void bfs(int r, int c, char[][] colors) {
		Queue<int[]>  q = new LinkedList<>();
		q.add(new int[] {r,c});
		char target = colors[r][c];
		visited[r][c] = true;
		while(!q.isEmpty()) {
			int[] curr = q.poll();
			int cr = curr[0];
			int cc = curr[1];
			
			//4방탐색하면서 타겟이랑 같은 문자만 쳌
			for(int i = 0; i < 4; i++) {
				int nr = cr + dr[i];
				int nc = cc + dc[i];
				
				//범위쳌
				if(nr<0||nc<0||nr>=N||nc>=N) continue;
				//방문쳌
				if(visited[nr][nc]) continue;
				//타겟과 문자가 같으면 방문체크하고 q에 넣기
				if(target == colors[nr][nc]) {
					visited[nr][nc] = true;
					q.add(new int[] {nr,nc});
				}
				
			}
			
			
		}
	
	}

}
