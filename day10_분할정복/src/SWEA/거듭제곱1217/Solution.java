package SWEA.거듭제곱1217;

import java.util.Scanner;

public class Solution {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		for(int tc = 1; tc <= 10; tc++) {
			int T = sc.nextInt();
			int N = sc.nextInt();
			int M = sc.nextInt();
			
			System.out.println("#"+T+" "+power(N,M));
		}

	}
	public static int power(int N, int M) {
		//종료조건
		if(M == 0) return 1;
		
		//재귀 조건 분할정복
		if(M % 2 == 1) {
			return power(N, (M-1)/2)*power(N,(M-1)/2)*N;
		}else {
			return power(N,M/2)*power(N,M/2);
		}
	}

}
