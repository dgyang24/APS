package 과목평가.T02_알고리즘_기본;

import java.util.Arrays;
import java.util.Scanner;

public class Algo3_서울_13_양동근_2T {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int T = sc.nextInt();//tc개수
		for(int tc = 1; tc <= T; tc++) {
			//배열사이즈 N 입력받기
			int N = sc.nextInt();
			//크기가 N*N인 배열 생성
			int[][] arr = new int[N][N];
			//돌 몇 번 던질래~ K
			int K = sc.nextInt();
			//K번 던지기 전에 합 초기화
			int sum = 0;
			//K만큼 던지자~
			for(int i = 0; i < K; i++){
				//던지는 위치 row,col
				int row = sc.nextInt()-1;//인덱스값
				int col = sc.nextInt()-1;//인덱스값
//				System.out.println("row: "+row+"col "+ col);
				//돌의 무게 W
				int W = sc.nextInt();
				//퍼지면서 커지거나 줄어드는 힘 P
				int P = sc.nextInt();
				//배열을 순회하면서 row, col과 거리(d)만큼 힘(P)을 더할거임
				for(int cr = 0; cr < N; cr++) {
					for(int cc = 0; cc < N; cc++) {
						//거리(d)는 row, col과 cr, cc간의 차 중 큰 값이라고 정의
						int rDiff = Math.abs(row-cr);
						int cDiff = Math.abs(col-cc);
						int d = Math.max(rDiff, cDiff);
						
						//각 거리(d)에 맞는 힘(P)을 초기값(W)에 더하여 할당
						int element = W+(d*P);
						//단 해당 값이 음수인 경우는 0을 할당
						if(element < 0) 
							arr[cr][cc] = 0;
						else
							arr[cr][cc] = element;
						
					}
					System.out.println(Arrays.toString(arr[cr]));
				}//배열 생성 완료
				//배열 합 
				for(int r = 0; r < N; r++) {
					for(int c = 0; c < N; c++) {
						sum += arr[r][c];
					}
				}
			}//돌던지기 끝
			
			
			System.out.println("#"+tc+" "+sum);
			
			
			
			
		}//테스트실행~
	}

}
