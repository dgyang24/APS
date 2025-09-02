package BAEK.톱니바퀴14891;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {
	static List<LinkedList<Character>> arr;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		// 톱니바퀴 4개 초기화 및 생성
		arr = new ArrayList<LinkedList<Character>>();
		for (int i = 0; i < 4; i++) {
			arr.add(new LinkedList<Character>());
			String str = sc.next();
			for (int j = 0; j < str.length(); j++) {
				arr.get(i).add(str.charAt(j));
			}
		}
		// 돌리는 횟수 K
		int K = sc.nextInt();
		for (int i = 0; i < K; i++) {
			int N = sc.nextInt() - 1;
			int D = sc.nextInt();
			changeQ(N, D);
		} // 톱니 돌리기
			// 점수 세기
		int cnt = 0;
		for (int i = 0; i < 4; i++) {
			if (arr.get(i).get(0) == '1') {
				cnt += Math.pow(2, i);
			}
		}
		System.out.println(cnt);

	}

	// 톱니바퀴를 돌릴거야
	static void changeQ(int n, int D) {
		// 돌려야되는 톱니바퀴 방
		int[] dir = new int[4];
		dir[n] = D;
		// n 보존
		int tmpN = n;
		// 오른쪽 먼저 검사
		// n+1이 4보다 작을 때
		while (tmpN + 1 < 4) {
			// n의 2인덱스와 n+1의 6인덱스가 다르면
			if (arr.get(tmpN).get(2) != arr.get(tmpN + 1).get(6)) {
				dir[tmpN + 1] = -dir[tmpN];
			}
			tmpN++;
		}

		int tmpN2 = n;
		// 왼쪽검사
		// n-1이 0 이상이면
		while (tmpN2 - 1 >= 0) {
			// n의 인덱스 6과 n-1인덱스 2가 다르면
			if (arr.get(tmpN2).get(6) != arr.get(tmpN2 - 1).get(2)) {
				dir[tmpN2 - 1] = -dir[tmpN2];
			}
			tmpN2--;
		}
		// 돌리기
		for (int i = 0; i < dir.length; i++) {
			if (dir[i] != 0)
				changeD(i, dir[i]);

		}

	}

	// 톱니바퀴 어디로 돌릴게 ~
	static void changeD(int n, int D) {
		if (D == 1) {
			arr.get(n).addFirst(arr.get(n).pollLast());
		} else if (D == -1) {
			arr.get(n).addLast(arr.get(n).pollFirst());
		}
	}
}
