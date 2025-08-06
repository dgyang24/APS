package SWEA.초심자회문검사1989;

import java.util.Scanner;

class Solution
{
	public static void main(String args[]) throws Exception
	{
		Scanner sc = new Scanner(System.in);
		int T;
		T=sc.nextInt();
		

		for(int test_case = 1; test_case <= T; test_case++)
		{
			String str = sc.next();
			int N = str.length();
			int result = 1;
			for(int i = 0; i<str.length()/2; i++) {
				if(str.charAt(i) != str.charAt(N-1-i)) {
					result = 0;
					break;
				}
			}
			System.out.println("#" + test_case + " " + result);
			
		}
	}
}
