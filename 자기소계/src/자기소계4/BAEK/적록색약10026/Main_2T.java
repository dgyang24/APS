package 자기소계4.BAEK.적록색약10026;

import java.util.Scanner;

public class Main_2T {
	static int[] dr = {-1,1,0,0};
	static int[] dc = {0,0,-1,1};
	
	static int N;
	static boolean[][] visited;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		char[][] board = new char[N][N];
		for(int i = 0; i < N; i++) {
			String str = sc.next();
			for(int j = 0; j < N; j++) {
				char c = str.charAt(j);
				board[i][j] = c;
			}
		}
		visited = new boolean[N][N];
		int cnt1 = 0;
		int cnt2 = 0;
		//초기화 끝
		
		//순회화면서 bfs돌리기
		for(int i = 0; i < N; i++) {
			for(int j  = 0; j < N; j++) {
				if(visited[i][j]) continue;
				char c = board[i][j];
				
			}
		}
		
	}

}
