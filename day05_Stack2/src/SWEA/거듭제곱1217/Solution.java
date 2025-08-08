package SWEA.거듭제곱1217;

import java.util.Scanner;

class Solution
{
	public static void main(String args[]) throws Exception
	{
		Scanner sc = new Scanner(System.in);
		
		/*
		   여러 개의 테스트 케이스가 주어지므로, 각각을 처리합니다.
		*/

		for(int test_case = 1; test_case <= 10; test_case++)
		{
			int T =sc.nextInt();
			int n = sc.nextInt();
			int m = sc.nextInt();
			
			System.out.println("#"+T+ " " + power(n,m));
			
		}
	}
	public static int power(int n, int m) {
		//기본
		if(m == 0) return 1;
		//재귀
		return n*power(n, m-1);
	}
}
