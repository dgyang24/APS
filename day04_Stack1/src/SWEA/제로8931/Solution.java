package SWEA.제로8931;

import java.io.File;
import java.util.Scanner;
import java.util.Stack;

class Solution
{
	public static void main(String args[]) throws Exception
	{
		File file = new File("./src/SWEA/제로8931/sample_input.txt");
		Scanner sc = new Scanner(file);
		int T;
		T=sc.nextInt();
		for(int test_case = 1; test_case <= T; test_case++)
		{
			int sum = 0;
			int K = sc.nextInt();
			Stack<Integer> stack = new Stack<>();
			for(int i = 0; i < K; i++) {
				int r = sc.nextInt();
				if(r != 0) {
					stack.push(r);
					
				}else {//0인경우
					if(!stack.empty())
						stack.pop();
				}
			}
			while(!stack.empty())
				sum+=stack.pop();
			
			System.out.println("#" + test_case + " " + sum);
		}
	}
}
