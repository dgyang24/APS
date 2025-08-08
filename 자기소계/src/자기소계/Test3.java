package 자기소계;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Test3 {
	//Test3은 한 칸 더 볼 수 있어야함! 
	// 상 하 좌 우 좌상 우상 좌하 우하
//	dr = {-1,1,0,0,-1,-1,1,1};
//	dc = {0,0,-1,1,-1,1,-1,1};
	
	
	public static void main(String[] args) throws FileNotFoundException{
		File file = new File("./src/자기소계/input.txt");
		Scanner sc = new Scanner(file);
		//T
		int T = sc.nextInt();
		//테스트 케이스 s
		for(int tc = 1; tc <= T; tc++) {
			//N
			int N = sc.nextInt();
			//N*N
			//입력을 다 받고
			int[][] arr = new int[N][N];
			//행기준 s
//			for(int r = 0; r < N; r++) {
//				for(int c = 0; c<N; c++) {
//					arr[r][c] = sc.nextInt();
//				}
//				System.out.println(Arrays.toString(arr[r]));
//			}// 행기준 e
			
//			//열기준 s
//			for(int c = 0; c < N; c++) {
//				for(int r = 0; r<N; r++) {
//					arr[r][c] = sc.nextInt();
//				}
//				
//			}// 열기준 e
//			for(int i = 0; i < N; i++) {
//				System.out.println(Arrays.toString(arr[i]));
//			}
			
			//그다음에 탐색
			
			
			
		}// 테스트 케이스 e

		
		
	}

}
