package SWEA.String1213;

import java.util.Scanner;

class Solution
{
	public static void main(String args[]) throws Exception
	{
		Scanner sc = new Scanner(System.in);
		
		for(int test_case = 1; test_case <= 10; test_case++)
		{
			int T;
			T=sc.nextInt();
			
			String find = sc.next();
			String str = sc.next();
			int N = find.length();
			int M = str.length();
			int sum = 0;
			
			for(int i = 0; i < M-(N-1); i++) {
				String tmp = "";
				for(int j = 0; j < N; j++) {
					if(find.charAt(j) == str.charAt(i+j)) {
						tmp += find.charAt(j);
					}
				}
				if(find.equals(tmp))
					sum++;
			}
			System.out.println("#" + T + " " + sum);
		}
	}
}