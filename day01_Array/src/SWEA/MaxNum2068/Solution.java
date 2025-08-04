package SWEA.MaxNum2068;

import java.util.Scanner;

public class Solution {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int T;
		T=sc.nextInt();
		for(int test_case = 1; test_case <= T; test_case++)
		{
			int max = 0; //각 수는 0~10000 이하이므로, 첫 비교대상은 제일 적은 0!
			//각 테스트 케이스의 개수는 10개로 고정
			for(int i = 0; i < 10; i++) {
				int num = sc.nextInt();
				if(num > max) {
					max = num;
				}
			}
			System.out.println("#"+test_case+ " " + max);
		}

	}

}
