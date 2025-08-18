package SWEA.달팽이숫자1954;

import java.util.Scanner;

public class Solution {
	//델타 우 하 좌 상
	static int[] dr = {0,1,0,-1};
	static int[] dc = {1,0,-1,0};
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		//tc개수 T
		int T = sc.nextInt();
		for(int tc = 1; tc<=T; tc++) {
			//달팽이크기 N
			int N = sc.nextInt();
			//N*N크기의 배열 생성
			int[][] snail = new int[N][N];
			//방향 변수
			int D = 0;
			//출발지 지정
			int row = 0;
			int col = 0;
			//넣을 값
			int s = 1;
			//출발지 값 부여
			snail[row][col] = s++;
			//값 넣기 시작
			for(int n = 1; n <= N*N; n++) {
				//배열크기 범위 안에 있을동안 일정한 방향으로 직진
				while(true) {
					//이동 경로
					int nr = row + dr[D];
					int nc = col + dc[D];
					if(nr < 0 || nc < 0|| nr >= N || nc >= N || snail[nr][nc] != 0) break;
					row = nr;
					col = nc;
					snail[row][col] = s++;
				}
				//범위를 벗어났으면 방향전환
				D = (D+1) %4; 
			}
			//출력
			
			System.out.println("#"+tc);
			for(int i = 0; i <N; i++) {
				for(int j = 0; j < N; j++) {
					System.out.print(snail[i][j] + " ");
				}
				System.out.println();
			}
			
		}//테스트 케이스 실행
	}

}
