package SWEA.퍼펙트셔플3499;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Solution {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int T = sc.nextInt();
		for (int tc = 1; tc <= T; tc++) {
			int N = sc.nextInt();
			Queue<String> q = new LinkedList<>();
			Queue<String> q2 = new LinkedList<>();
			for (int i = 0; i < N; i++) {
				if (N % 2 == 0) {
					if (i < N / 2)
						q.add(sc.next());
					else
						q2.add(sc.next());
				} else {
					if (i < N / 2 + 1)
						q.add(sc.next());
					else
						q2.add(sc.next());
				}

			}
			// 셔플 후 카드 스텍
			StringBuilder sb = new StringBuilder();

			while (!q.isEmpty() && !q2.isEmpty()) {
				if (!q.isEmpty())
					sb.append(q.remove()).append(" ");
				if (!q2.isEmpty())
					sb.append(q2.remove()).append(" ");

			}
			// 홀수일경우, q에 1개 남아있으므로 마저 털어준다.
			if (!q.isEmpty())
				sb.append(q.remove()).append(" ");

			System.out.println("#" + tc + " " + sb);

		}

	}

}
