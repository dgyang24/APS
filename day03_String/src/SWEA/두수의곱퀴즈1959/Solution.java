package SWEA.두수의곱퀴즈1959;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Solution {
	static int max_sum = 0;
	
	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("./src/SWEA/두수의곱퀴즈1959/input.txt");
		Scanner sc = new Scanner(file);
//		Scanner sc = new Scanner(System.in);
		// T
		int T = sc.nextInt();
		// T 만큼 반복
		for(int tc=1; tc<=T; tc++) {
			// N, M 값을 받고
			
			int N = sc.nextInt();
			int M = sc.nextInt();
			
			// N의 길이 만큼 A 배열
			int[] A = new int[N];
			for (int n=0; n<N; n++) {
				A[n] = sc.nextInt();
			}
			// M의 길이 만큼 B 배열
			int[] B = new int[M];
			for (int m=0; m<M; m++) {
				B[m] = sc.nextInt();
			}
			
			if(N<M) { max_sum = work(A, B); }
			else if(N>M) { max_sum = work(B, A); }
			
			
			System.out.printf("#%d %d%n", tc, max_sum);
			
		}
		

		// Scanner sc를 닫아주는건 모든 작업이 끝난 후 
		sc.close();
	}
	
	// 반환값 max값 체크해서 반환하는 메소드를 만들것이고 
	// 매개변수로 1번째 인자는 길이가 더 짧은 배열, 2번째 인자는 길이가 긴 배열 
	public static int work(int[] S, int[] L) {
		max_sum = 0;
		// 이제부터 탐색을 할게~~
		// 0 ~ (5-3) = 2
		// < 0,1 || <= 0,1,2
		
		for (int i=0; i<=L.length-S.length; i++) {
			// i 번째 턴에서 합을 구해줄 용도
			int cur_sum = 0;
			// 이제 곱해서 합을 넣어준 다음에 최대인지 아닌지 체크
			// S[0] S[1] S[2]
			// L[0] L[1] L[2]

			for (int j=0; j<S.length; j++) {
				cur_sum += S[j]*L[i+j];
			}
			// 최대값이 바꼈는지 확인해줘야해요
			if (max_sum < cur_sum) {
				max_sum = cur_sum;
			}
		}
		return max_sum;
	}
}
