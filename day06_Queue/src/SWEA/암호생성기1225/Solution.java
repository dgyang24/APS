package SWEA.암호생성기1225;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class Solution {
	public static void main(String args[]) throws Exception {
		Scanner sc = new Scanner(System.in);

		for (int test_case = 1; test_case <= 10; test_case++) {
			int T = sc.nextInt();
			Queue<Integer> q = new LinkedList<>();
			for (int i = 0; i < 8; i++) {
				q.add(sc.nextInt());
			}
			
			int n = 1;
			while (true) {
				int tmp = q.remove() - n++;
				if(n > 5)
					n=1;
				if (tmp <= 0) {
					q.add(0);
					break;
				}
				q.add(tmp);
			}
			StringBuilder sb = new StringBuilder();
			while (!q.isEmpty()) {
				sb.append(q.remove()).append(" ");
			}
			System.out.println("#" + T + " " + sb);

		}
	}
}
