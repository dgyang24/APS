package SWEA.길이가M인회문찾기21936;

import java.util.Scanner;
import java.io.FileInputStream;

class Solution {
	public static void main(String args[]) throws Exception {
		Scanner sc = new Scanner(System.in);
		int T;
		T = sc.nextInt();

		for (int test_case = 1; test_case <= T; test_case++) {
			int N = sc.nextInt();
			int M = sc.nextInt();
			String str = sc.next();
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < N-M+1; i++) {
				boolean isTrue = true;
				for(int j = 0; j < M/2; j++) {
					if(str.charAt(i+j) != str.charAt(i+M-1-j)) {
						isTrue = false;
						break;
					}
				}
				if(isTrue) {
					for(int k = i; k < i+M; k++)
						sb.append(str.charAt(k));
				}
			}
			if(sb.length() == 0)
				sb.append("NONE");
			
			System.out.println("#"+test_case+" "+sb);
		}
	}
}