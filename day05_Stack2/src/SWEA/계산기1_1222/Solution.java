package SWEA.계산기1_1222;

import java.util.Scanner;
import java.util.Stack;

class Solution
{
	public static void main(String args[]) throws Exception
	{
		Scanner sc = new Scanner(System.in);
		

		for(int test_case = 1; test_case <= 10; test_case++)
		{
			int sum = 0;
			int N = sc.nextInt();
			String str = sc.next();
			//연산자 담을 스택
			Stack<Character> stack = new Stack<>();
			//후위표기식 담을 스트링빌더
			StringBuilder result = new StringBuilder();
			
			//str을 순회하면서 
			//숫자 만나면 result에 넣기
			for(int i = 0; i < N; i++) {
				char tmp = str.charAt(i);
				if(tmp >= '0' && tmp <= '9') {
					result.append(tmp);
					int a = Integer.parseInt(Character.toString(tmp));
					sum += a;
				}
				else {
					//연산자를 만나면 stack에다가 넣기
					if(!stack.isEmpty()) {
						result.append(stack.pop());
					}
					
					stack.push(tmp);
					
				}
			}//후위표기식생성
			//생성끝났으면 다 털기
			while(!stack.isEmpty())
				result.append(stack.pop());
			System.out.println("#" + test_case + " " + sum);
			
		}
	}
}
