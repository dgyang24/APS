package SWEA.쉬운거스름돈1970;

import java.util.Scanner;

public class Solution {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int T =sc.nextInt();
		for(int tc = 1; tc <= T; tc++) {
			int money = sc.nextInt();
			StringBuilder sb = new StringBuilder();
			int[] coin= {50000,10000,5000,1000,500,100,50,10};
			
			//10부터
			for(int i = 0; i <8; i++) {
				int N = money/coin[i];
				money %= coin[i];
				sb.append(N).append(" ");
				
			}
			
			
			System.out.println("#"+tc+"\n"+sb);
			
			
		}
		
	}

}
