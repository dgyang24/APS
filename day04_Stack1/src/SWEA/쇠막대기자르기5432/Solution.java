package SWEA.쇠막대기자르기5432;

import java.util.Scanner;
import java.util.Stack;

class Solution
{
	public static void main(String args[]) throws Exception
	{
		Scanner sc = new Scanner(System.in);
		int T;
		T=sc.nextInt();

		for(int test_case = 1; test_case <= T; test_case++)
		{
			//str 받기
			String str = sc.next();
			//str길이
			int N = str.length();
			//stack 막대
			Stack<Character> stack = new Stack<>();
			//막대개수
			int sum = 0;
			//길이만큼 순회하면서 막대기 자르기
			for(int i = 0; i < N; i++) {
				char tmp = str.charAt(i);
				//1. '('은 push
				if(tmp == '(')
					stack.push(tmp);
				else {
					//2. ')'일때
					char preT = str.charAt(i-1);
					//2-1. str상의 앞 문자가 '('이면 pop하고 +size
					if(preT == '(') {
						stack.pop();
						sum+= stack.size();
					}else {
						//2-2. str상의 앞 문자가 ')'이면 pop하고 +1
						stack.pop();
						sum+=1;
					}
					
				}
			}
			System.out.println("#"+test_case+" " + sum);
			
			
			
		}
	}
}
